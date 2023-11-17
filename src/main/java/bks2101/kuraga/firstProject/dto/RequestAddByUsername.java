package bks2101.kuraga.firstProject.dto;

import lombok.Data;

import java.util.Set;
@Data
public class RequestAddByUsername {
    private Set<String> curatorsUsername;
}
