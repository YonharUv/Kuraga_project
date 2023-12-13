package bks2101.kuraga.firstProject.repository;

import bks2101.kuraga.firstProject.entitys.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
