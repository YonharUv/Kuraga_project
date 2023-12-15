package bks2101.kuraga.firstProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingList implements Serializable {
    private long id;
    private String name;
    private LocalDateTime date;
    private String topic;
    private String location;
    private List<StudentAttendanceDto> list;
}
