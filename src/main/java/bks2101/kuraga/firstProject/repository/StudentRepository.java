package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByUsername(String username);
    Student findByUsername(String username);
}
