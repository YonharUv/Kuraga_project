package bks2101.kuraga.firstProject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Table(name="Meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String topic;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
