package bks2101.kuraga.firstProject.entitys;

import bks2101.kuraga.firstProject.dto.MeetingList;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Table(name="Curator")
@Builder
public class Curator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String personal_data;
    private String email;
    private String username;
    @ManyToOne(optional = true)
    @JoinColumn(name="supervisor_id")
    private Supervisor supervisor;
    @OneToMany(mappedBy = "curator")
    private List<Group> groups;
    @OneToMany(mappedBy = "curator")
    private List<Meeting> meetings;
    public void AddMeetings(Meeting meeting) {
        this.meetings.add(meeting);
    }
}
