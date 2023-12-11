package bks2101.kuraga.firstProject.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    ADMIN("ADMIN"),
    USER("USER"),
    SUPERVISOR("SUPERVISOR"),
    BANNED("BANNED");
    private final String permission;
}
