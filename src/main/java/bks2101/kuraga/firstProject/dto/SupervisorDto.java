package bks2101.kuraga.firstProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupervisorDto {
    private String email;
    private String first_name;
    private String last_name;
    private String personal_data;
    private List<CuratorResponse> curators;
}
