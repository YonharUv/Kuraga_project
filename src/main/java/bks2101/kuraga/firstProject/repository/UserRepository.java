package bks2101.kuraga.firstProject.repository;
import bks2101.kuraga.firstProject.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    ApplicationUser findByEmail(String email);
    Optional<ApplicationUser> findById(Long id);
}