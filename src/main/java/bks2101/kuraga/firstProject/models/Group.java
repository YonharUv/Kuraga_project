package bks2101.kuraga.firstProject.models;

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
    private String name;
    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;
    @OneToMany(mappedBy = "group")
    private Set<Student> students;
    @OneToMany(mappedBy = "group")
    private Set<Meeting> meetings;
}
