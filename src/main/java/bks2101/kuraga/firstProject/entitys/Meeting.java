package bks2101.kuraga.firstProject.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Builder
@Table(name="Meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long number;
    private LocalDateTime date;
    private String topic;
    private String location;
    @ManyToOne
    @JoinColumn(name = "group_name", nullable = false)
    private Group group;
    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;
}
