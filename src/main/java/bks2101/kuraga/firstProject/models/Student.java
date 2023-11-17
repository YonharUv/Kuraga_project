package bks2101.kuraga.firstProject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Table(name="Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String personal_data;
    private String username;
    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
