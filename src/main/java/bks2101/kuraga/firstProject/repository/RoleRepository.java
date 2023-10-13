package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.models.ERole;
import bks2101.kuraga.firstProject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
