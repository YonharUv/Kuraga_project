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
public class Supervisor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String personal_data;
    @OneToMany(mappedBy = "supervisor")
    private Set<Curator> curators;
    public void addCurator(Curator curator) {
        this.curators.add(curator);
    }
}
