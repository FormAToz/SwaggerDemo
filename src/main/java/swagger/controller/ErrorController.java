package swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swagger.entity.Error;
import swagger.repository.ErrorRepository;
import swagger.response.ErrorResponse;
import swagger.response.ErrorTimeResponse;

import java.util.Optional;

@Api(description = "Обработка ошибок")
@RestController
@RequestMapping("errors")
@ApiModel(description = "Обработка ошибок")
public class ErrorController {

    private final ErrorRepository errorRepository;

    public ErrorController(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    // Информация о последней ошибке
    @ApiOperation(value = "Сообщение о последней ошибке", tags = { "errors" })
    @GetMapping("/last")
    public ResponseEntity<ErrorResponse> getLastError() {
        Optional<Error> error = errorRepository.findFirstByOrderByIdDesc();

        return error.<ResponseEntity<ErrorResponse>>map(value -> new ResponseEntity<>(new ErrorTimeResponse(value.getMessage(),
                value.getCreated()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ErrorResponse("No Errors"), HttpStatus.NOT_FOUND));

    }
}
