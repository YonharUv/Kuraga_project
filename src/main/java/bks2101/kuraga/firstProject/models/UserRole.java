package bks2101.kuraga.firstProject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import bks2101.kuraga.firstProject.models.Permission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static java.util.stream.Collectors.toSet;
@Getter
@AllArgsConstructor
public enum UserRole {
    USER(Set.of(Permission.READ)),
    ADMIN(Set.of(Permission.READ, Permission.MODIFICATION));
    private  final Set<Permission> permissions;
    public Set<SimpleGrantedAuthority> grantedAuthority() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(toSet());
    }
}
