package bks2101.kuraga.firstProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuratorDto {
    private String first_name;
    private String last_name;
    private String personal_data;
    private String supervisor_username;
}
