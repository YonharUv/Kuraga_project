package bks2101.kuraga.firstProject.repository;
import org.springframework.data.repository.CrudRepository;
import bks2101.kuraga.firstProject.models.User;
public interface UserRepository extends CrudRepository<User, Long> {
}