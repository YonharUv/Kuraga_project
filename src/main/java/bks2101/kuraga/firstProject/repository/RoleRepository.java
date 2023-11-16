package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.models.Role;
import bks2101.kuraga.firstProject.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(UserRole roleName);
}
