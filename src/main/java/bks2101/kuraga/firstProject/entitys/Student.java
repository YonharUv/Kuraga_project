package bks2101.kuraga.firstProject.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Builder
@Table(name="Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String personal_data;
    private String email;
    private String vk_id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @Transient
    private Map<Long, Boolean> attendance = new HashMap<>();
    public Map<Long, Boolean> addAttendance(long id) {
        this.attendance.put(id, false);
        return this.attendance;
    }
    public void ChangeAttendance(long id, boolean mark) {
        this.attendance.put(id, mark);
    }
}
