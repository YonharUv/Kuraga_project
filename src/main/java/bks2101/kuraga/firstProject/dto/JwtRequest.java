package bks2101.kuraga.firstProject.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
