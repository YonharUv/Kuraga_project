package bks2101.kuraga.firstProject.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Data
@AllArgsConstructor
@Table(name="Student_Group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String faculty_name;
    private String name;
    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;
    @OneToMany(mappedBy = "group")
    private Set<Student> students;
    @OneToMany(mappedBy = "group")
    private Set<Meeting> meetings;
    public void AddMeeting(Meeting meeting) {
        this.meetings.add(meeting);
    }
}
