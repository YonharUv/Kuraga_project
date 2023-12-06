package bks2101.kuraga.firstProject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@AllArgsConstructor
public enum Role {
    USER(Set.of(Permission.USER)),
    ADMIN(Set.of(Permission.ADMIN));
    @Getter
    private  final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> grantedAuthority() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        return authorities;
    }
}
