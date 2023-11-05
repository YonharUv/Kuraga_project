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
@Table(name="Group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "curator_id", nullable = false)
    private Curator curator;
    @OneToMany(mappedBy = "group")
    private Set<Student> students;
    @OneToMany(mappedBy = "group")
    private Set<Meeting> meetings;
}
