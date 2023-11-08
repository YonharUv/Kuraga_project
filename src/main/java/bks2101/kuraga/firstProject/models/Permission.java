package bks2101.kuraga.firstProject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    READ("read"),
    MODIFICATION("modification");
    private final String permission;
}
