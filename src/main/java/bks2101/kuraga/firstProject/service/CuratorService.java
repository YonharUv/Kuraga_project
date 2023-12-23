package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.*;
import bks2101.kuraga.firstProject.exceptions.GroupNotFoundByCurator;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.entitys.*;
import bks2101.kuraga.firstProject.repository.*;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import bks2101.kuraga.firstProject.utils.MappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor
@Slf4j
public class CuratorService {
    private final CuratorRepository curatorRepository;
    private final MeetingRepository meetingRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final SupervisorRepository supervisorRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public ResponseEntity<List<CuratorDto>> getAllCurators() {
        var listCurators = curatorRepository.findAll();
        List<CuratorDto> curatorDtoList = listCurators.stream()
                .map(MappingUtil::mapToCuratorDto)
                .toList();
        return ResponseEntity.ok(curatorDtoList);
    }

    public ResponseEntity<List<CuratorDto>> getAllCuratorsByEmail(String email) {
        var listCurators = curatorRepository.findAllByEmail(email);
        List<CuratorDto> curatorDtoList = listCurators.stream()
                .map(MappingUtil::mapToCuratorDto)
                .toList();
        return ResponseEntity.ok(curatorDtoList);
    }

    public ResponseEntity<String> createCuratorAdmin(CuratorDto curatorDto) throws UserAlreadyExistsException {
        if (curatorRepository.existsByEmail(curatorDto.getEmail())) {
            throw new UserAlreadyExistsException("Куратор", curatorDto.getEmail());
        }
        log.info("Curator created successfully: {}", curatorDto.getEmail());
        Curator curator = new Curator();
        curator.setFirst_name(curatorDto.getFirst_name());
        curator.setLast_name(curatorDto.getLast_name());
        curator.setPersonal_data(curatorDto.getPersonal_data());
        curator.setEmail(curatorDto.getEmail());
        ApplicationUser curatorUser = userRepository.findByEmail(curatorDto.getEmail());
        curator.setUsername(curatorUser.getUsername());
        if (curatorDto.getSupervisor_email() == null || !(supervisorRepository.existsByEmail(curatorDto.getSupervisor_email()))) {
            curatorDto.setSupervisor_email("vis0@example.com");
        }
        Supervisor supervisor = supervisorRepository.findByEmail(curatorDto.getSupervisor_email());
        supervisor.addCurator(curator);
        curator.setSupervisor(supervisor);
        supervisorRepository.save(supervisor);
        curatorRepository.save(curator);
        return ResponseEntity.ok("Куратор успешно создан");
    }

    public ResponseEntity<CuratorDto> getCuratorByEmail(String curatorEmail) throws UserNotFoundByUsernameException {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        return ResponseEntity.ok(MappingUtil.mapToCuratorDto(curator));
    }

    public ResponseEntity<List<GroupDto>> getCuratorGroups(String curatorEmail) throws UserNotFoundByUsernameException {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        CuratorDto curatorDto = MappingUtil.mapToCuratorDto(curator);
        return ResponseEntity.ok(curatorDto.getGroups());
    }

    public ResponseEntity<Set<MeetingDto>> getCuratorMeetings(String curatorEmail) throws UserNotFoundByUsernameException {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        CuratorDto curatorDto = MappingUtil.mapToCuratorDto(curator);
        return ResponseEntity.ok(MappingUtil.mapToMeetingDto(curatorDto));
    }

    public ResponseEntity<GroupDto> getCuratorGroupByName(String curatorEmail, String groupName) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        CuratorDto curatorDto = MappingUtil.mapToCuratorDto(curator);
        if (!curatorDto.getGroups().contains(groupName)) {
            throw new GroupNotFoundByCurator(curatorEmail, groupName);
        }
        return ResponseEntity.ok(MappingUtil.mapToGroupDto(groupRepository.findByName(groupName)));
    }

    public ResponseEntity<MeetingDto> createGroupMeetings(String curatorEmail, String groupName, MeetingDto meetingDto) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        if (!groupRepository.existsByName(groupName)) {
            throw new UserNotFoundByUsernameException("Группа", groupName);
        }
        Group group = groupRepository.findByName(groupName);
        if (!Objects.equals(group.getCurator().getEmail(), curatorEmail)) {
            throw new GroupNotFoundByCurator(curatorEmail, groupName);
        }
        Journal loadedJournal = Journal.loadFromFile("journal.ser");
        Meeting meeting = MappingUtil.mapToMeeting(curator, meetingDto, group);
        curator.AddMeetings(meeting);
        group.AddMeeting(meeting);
        MeetingList list = MappingUtil.CreateMeetingList(group.getStudents(), group.getName(), meetingDto.getId(), meeting);
        loadedJournal.addList(curatorEmail, groupName + " " + meetingDto.getId(), list);
        loadedJournal.saveToFile("journal.ser");
        meeting.setGroup(group);
        curatorRepository.save(curator);
        meetingRepository.save(meeting);
        groupRepository.save(group);
        return ResponseEntity.ok(meetingDto);
    }

    public ResponseEntity<String> deleteCurator(String email) throws UserNotFoundByUsernameException {
        if (!curatorRepository.existsByEmail(email)) {
            throw new UserNotFoundByUsernameException("Куратор", email);
        }
        Curator curator = curatorRepository.findByEmail(email);
        Supervisor supervisor = curator.getSupervisor();
        supervisor.deleteCurator(curator);
        supervisorRepository.save(supervisor);
        var groups = curator.getGroups();
        for (Group group : groups) {
            group.setCurator(null);
            List<Meeting> meetingsGroup = group.getMeetings();
            for (Meeting meeting : meetingsGroup) {
                meeting.setCurator(null);
                meetingRepository.save(meeting);
            }
            groupRepository.save(group);
        }
        curatorRepository.delete(curator);
        log.info("Curator deleted successfully: {}", email);
        return ResponseEntity.ok(format("Куратор %s успешно удален", email));
    }
    public ResponseEntity<?> updateList(String curatorEmail, String groupName, long id, MeetingList studentsList) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        if (!groupRepository.existsByName(groupName)) {
            throw new UserNotFoundByUsernameException("Группа", groupName);
        }
        Group group = groupRepository.findByName(groupName);
        if (!Objects.equals(group.getCurator().getEmail(), curatorEmail)) {
            throw new GroupNotFoundByCurator(curatorEmail, groupName);
        }

        Journal loadedJournal = Journal.loadFromFile("journal.ser");
        if (loadedJournal.check(curatorEmail, groupName + " " + id)) {
            var journal = loadedJournal.getJournal();
            var lists = journal.get(curatorEmail);
            var list = lists.get(groupName + " " + id);
            list.setList(studentsList.getList());
            loadedJournal.saveToFile("journal.ser");
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok("Не удалось обновить " + id + " у группы " + groupName);
    }

    public ResponseEntity<?> getList(String curatorEmail, String groupName, long id) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        if (!groupRepository.existsByName(groupName)) {
            throw new UserNotFoundByUsernameException("Группа", groupName);
        }
        Group group = groupRepository.findByName(groupName);
        if (!Objects.equals(group.getCurator().getEmail(), curatorEmail)) {
            throw new GroupNotFoundByCurator(curatorEmail, groupName);
        }
        Journal loadedJournal = Journal.loadFromFile("journal.ser");
        if (loadedJournal.check(curatorEmail, groupName + " " + id)) {
            var journal = loadedJournal.getJournal();
            var lists = journal.get(curatorEmail);
            return ResponseEntity.ok(lists.get(groupName + " " + id));
        }
        return ResponseEntity.ok("Не удалось найти " + id + " у группы " + groupName);
    }
}
