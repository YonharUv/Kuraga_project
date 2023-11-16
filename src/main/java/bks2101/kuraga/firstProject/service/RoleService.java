package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.models.Role;
import bks2101.kuraga.firstProject.models.UserRole;
import bks2101.kuraga.firstProject.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role getUserRole() {
        return roleRepository.findByName(UserRole.USER);
    }
}
