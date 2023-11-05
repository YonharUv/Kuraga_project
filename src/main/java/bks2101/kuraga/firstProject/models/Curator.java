package bks2101.kuraga.firstProject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Table(name="Curator")
public class Curator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String personal_data;
    @ManyToOne
    @JoinColumn(name="supervisor_id", nullable = false)
    private Supervisor supervisor;
    @OneToMany(mappedBy = "curator")
    private Set<Student> students;
    @OneToMany(mappedBy = "curator")
    private Set<Group> groups;
}
