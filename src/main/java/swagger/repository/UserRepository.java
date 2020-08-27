package swagger.repository;

import org.springframework.data.repository.CrudRepository;
import swagger.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String username);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findById(int id);

    Optional<User> findFirstByOrderByIdDesc();

    Boolean existsByEmailIgnoreCase(String email);
}
