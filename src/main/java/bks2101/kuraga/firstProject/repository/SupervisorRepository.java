package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.entitys.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    public Supervisor findByEmail(String username);
    public boolean existsByEmail(String email);
}
