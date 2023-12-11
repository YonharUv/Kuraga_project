package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.entitys.Curator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuratorRepository extends JpaRepository<Curator, Long> {
    boolean existsByEmail(String email);
    Curator getByEmail(String email);
    Optional<Curator> findById(Long id);

    Curator getById(Long id);
    Curator findByEmail(String email);
    List<Curator> findAllByEmail(String email);
}
