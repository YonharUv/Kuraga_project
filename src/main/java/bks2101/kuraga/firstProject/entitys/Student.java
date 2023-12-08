package bks2101.kuraga.firstProject.entitys;

import jakarta.persistence.*;
import lombok.*;

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
}
