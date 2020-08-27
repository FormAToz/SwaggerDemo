package swagger.repository;

import org.springframework.data.repository.CrudRepository;
import swagger.entity.Error;

import java.util.Optional;

public interface ErrorRepository extends CrudRepository<Error, Integer>{
    Optional<Error> findFirstByOrderByIdDesc();
}
