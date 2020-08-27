package swagger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swagger.entity.Error;
import swagger.entity.User;
import swagger.repository.ErrorRepository;
import swagger.response.ErrorResponse;
import swagger.security.jwt.AuthEntryPointJwt;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Autowired
    private ErrorRepository errorRepository;

    public ResponseEntity<?> getUser(Optional<User> user) {

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        String message = "User not found";

        logger.error(message);
        errorRepository.save(new Error(message, System.currentTimeMillis()));

        return new ResponseEntity<>(new ErrorResponse(message), HttpStatus.NOT_FOUND);
    }
}
