package bks2101.kuraga.firstProject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private Date date;
    private String topic;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
