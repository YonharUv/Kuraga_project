package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.entitys.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String username);
    Student findByEmail(String username);
}
