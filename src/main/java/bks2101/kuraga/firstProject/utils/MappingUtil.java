package bks2101.kuraga.firstProject.utils;

import bks2101.kuraga.firstProject.dto.*;
import bks2101.kuraga.firstProject.entitys.*;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.crypto.keygen.KeyGenerators.string;

@UtilityClass
public class MappingUtil {
    public static CuratorDto mapToCuratorDto(Curator curator) {
        List<Group> groups = curator.getGroups();
        List<GroupDto> groupsDto = groups.stream().map(
                MappingUtil::mapToGroupDto).collect(Collectors.toList());
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
                collect(Collectors.toList());

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
                .attendance(new HashMap<>())
                .build();
    }

    public static Meeting mapToMeeting(Curator curator, MeetingDto meetingDto, Group group) {
        return Meeting.builder()
                .number(meetingDto.getId())
                .date(meetingDto.getDate())
                .group(group)
                .topic(meetingDto.getTopic())
                .curator(curator)
                .location(meetingDto.getLocation())
                .build();
    }
    public static List<MeetingDto> mapToMeetingDto(GroupDto groupDto) {
        return groupDto.getMeetings();
    }
    public static Set<MeetingDto> mapToMeetingDto(CuratorDto curatorDto) {
        List<GroupDto> groupsDto = curatorDto.getGroups();
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
                .id(meeting.getNumber())
                .date(meeting.getDate())
                .topic(meeting.getTopic())
                .location(meeting.getLocation())
                .build();
    }

    public static GroupDto mapToGroupDto(Group group) {
        List<StudentDto> studentsDto = null;
        List<MeetingDto> meetingsDto = null;
        if (group.getStudents() != null) {
            studentsDto = group.getStudents().stream()
                    .map(MappingUtil::mapToStudentDto)
                    .collect(Collectors.toList());
        }
        if (group.getMeetings() != null) {
            meetingsDto = group.getMeetings().stream()
                    .map(MappingUtil::mapToMeetingDto)
                    .collect(Collectors.toList());
        }
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
    public static StudentAttendanceDto mapToStudentAttendanceDto(Student student) {
        return StudentAttendanceDto.builder()
                .name(student.getLast_name() + " " + student.getFirst_name())
                .isAttended(false)
                .build();
    }

    public static MeetingList CreateMeetingList(List<Student> students, String name, long id, Meeting meeting) {
        List<StudentAttendanceDto> studentList = students.stream().map(MappingUtil::mapToStudentAttendanceDto).toList();
        return MeetingList.builder()
                .id(id)
                .date(meeting.getDate())
                .name(name)
                .topic(meeting.getTopic())
                .location(meeting.getLocation())
                .list(studentList)
                .build();
    }
}
