package bks2101.kuraga.firstProject.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;
@Data
public class RequestAddCurators {
    private List<String> curators_email;
}
