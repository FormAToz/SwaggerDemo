package swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swagger.response.ErrorTimeResponse;

@Api(description = "Обработка ошибок")
@RestController
@RequestMapping("errors")
@ApiModel(description = "Обработка ошибок")
public class ErrorController {

    @ApiOperation(value = "Сообщение о последней ошибке", tags = { "errors" })
    @GetMapping("/last")
    public ResponseEntity<ErrorTimeResponse> getLastError() {
        // TODO реализовать(БД)
        return new ResponseEntity<>(new ErrorTimeResponse("last error", System.currentTimeMillis()), HttpStatus.OK);
    }
}
