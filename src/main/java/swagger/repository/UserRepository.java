package swagger.repository;

import org.springframework.data.repository.CrudRepository;
import swagger.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String username);

    // TODO realize query
    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);
}
