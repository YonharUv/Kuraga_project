package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
