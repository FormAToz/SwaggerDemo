package swagger.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Класс ответа с описанием ошибки")
public class ErrorResponse {
    @JsonProperty("msg")
    @ApiModelProperty(notes = "Описание ошибки", example = "Пользователь не найден")
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
