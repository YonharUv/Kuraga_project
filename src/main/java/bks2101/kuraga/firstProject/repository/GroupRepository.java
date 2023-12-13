package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.entitys.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
