package swagger.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swagger.entity.Error;
import swagger.repository.ErrorRepository;
import swagger.response.ErrorResponse;
import swagger.response.ErrorTimeResponse;

import java.util.Optional;

@Api(tags = "Обработка ошибок")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("errors")
public class ErrorController {

    private final ErrorRepository errorRepository;

    public ErrorController(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    // Информация о последней ошибке
    @ApiOperation(value = "Сообщение о последней ошибке")
    @GetMapping("/last")
    public ResponseEntity<ErrorResponse> getLastError() {
        Optional<Error> error = errorRepository.findFirstByOrderByIdDesc();

        return error.<ResponseEntity<ErrorResponse>>map(value -> new ResponseEntity<>(new ErrorTimeResponse(value.getMessage(),
                value.getCreated()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ErrorResponse("No Errors"), HttpStatus.NOT_FOUND));

    }
}
