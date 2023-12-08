package bks2101.kuraga.firstProject.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    ADMIN("ADMIN"),
    BANNED("BANNED"),
    USER("USER");
    private final String permission;
}
