package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.models.Curator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuratorRepository extends JpaRepository<Curator, Long> {
}
