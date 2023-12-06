package bks2101.kuraga.firstProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuratorResponse {
        private String curator_email;
        private String curator_first_name;
        private String curator_last_name;
        private String curator_personal_data;
}
