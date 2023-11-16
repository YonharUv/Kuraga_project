package bks2101.kuraga.firstProject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    ADMIN("ADMIN"),
    USER("USER");
    private final String permission;
}
