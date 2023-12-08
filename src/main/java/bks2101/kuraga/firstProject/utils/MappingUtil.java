package bks2101.kuraga.firstProject.utils;

import bks2101.kuraga.firstProject.dto.*;
import bks2101.kuraga.firstProject.entitys.*;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MappingUtil {
    public static CuratorDto mapToCuratorDto(Curator curator) {
        Set<Group> groups = curator.getGroups();
        Set<GroupDto> groupsDto = groups.stream().map(
                MappingUtil::mapToGroupDto).collect(Collectors.toSet());
        return CuratorDto.builder()
                .email(curator.getEmail())
                .groups(groupsDto)
                .first_name(curator.getFirst_name())
                .last_name(curator.getLast_name())
                .personal_data(curator.getPersonal_data())
                .supervisor_email(curator.getSupervisor().getEmail())
                .build();
    }


    public static SupervisorDto mapToSupervisorDto(Supervisor supervisor) {
        var curators = supervisor.getCurators()
                .stream()
                .map(MappingUtil::mapToCuratorResponse).
                collect(Collectors.toSet());

        return  SupervisorDto.builder()
                .email(supervisor.getEmail())
                .first_name(supervisor.getFirst_name())
                .last_name(supervisor.getLast_name())
                .personal_data(supervisor.getPersonal_data())
                .curators(curators)
                .build();
    }

    public static CuratorResponse mapToCuratorResponse(Curator curator) {
        return CuratorResponse.builder()
                .curator_first_name(curator.getFirst_name())
                .curator_last_name(curator.getLast_name())
                .curator_email(curator.getEmail())
                .curator_personal_data(curator.getPersonal_data())
                .build();
    }

    public static Student mapToStudent(StudentDto student, Group group) {
        return Student.builder()
                .group(group)
                .email(student.getEmail())
                .first_name(student.getFirst_name())
                .last_name(student.getLast_name())
                .personal_data(student.getPersonal_data())
                .vk_id(student.getVk_id())
                .build();
    }

    public static Meeting mapToMeeting(Curator curator, MeetingDto meetingDto, Group group) {
        return Meeting.builder()
                .date(meetingDto.getDate())
                .group(group)
                .topic(meetingDto.getTopic())
                .curator(curator)
                .location(meetingDto.getLocation())
                .build();
    }
    public static Set<MeetingDto> mapToMeetingDto(GroupDto groupDto) {
        return groupDto.getMeetings();
    }
    public static Set<MeetingDto> mapToMeetingDto(CuratorDto curatorDto) {
        Set<GroupDto> groupsDto = curatorDto.getGroups();
        Set<MeetingDto> curatorMeetings = groupsDto.stream()
                .flatMap(groupDto -> mapToMeetingDto(groupDto).stream())
                .collect(Collectors.toSet());
        return curatorMeetings;
    }
    public static StudentDto mapToStudentDto(Student student) {
        return StudentDto.builder()
                .vk_id(student.getVk_id())
                .email(student.getEmail())
                .first_name(student.getFirst_name())
                .last_name(student.getLast_name())
                .personal_data(student.getPersonal_data())
                .build();
    }

    public static MeetingDto mapToMeetingDto(Meeting meeting) {
        return MeetingDto.builder()
                .date(meeting.getDate())
                .topic(meeting.getTopic())
                .location(meeting.getLocation())
                .build();
    }

    public static GroupDto mapToGroupDto(Group group) {
        Set<StudentDto> studentsDto = group.getStudents().stream()
                .map(MappingUtil::mapToStudentDto)
                .collect(Collectors.toSet());
        Set<MeetingDto> meetingsDto = group.getMeetings().stream()
                .map(MappingUtil::mapToMeetingDto)
                .collect(Collectors.toSet());
        String curator_email = "";
        if (group.getCurator() != null) {
            curator_email = group.getCurator().getEmail();
        }
        return GroupDto.builder()
                .students(studentsDto)
                .name(group.getName())
                .faculty_name(group.getFaculty_name())
                .curator_email(curator_email)
                .meetings(meetingsDto)
                .build();
    }
}
