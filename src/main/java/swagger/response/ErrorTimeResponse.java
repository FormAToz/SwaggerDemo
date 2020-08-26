package swagger.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Класс ответа с описанием ошибки и временем возникновения")
public class ErrorTimeResponse {
    @JsonProperty("msg")
    @ApiModelProperty(notes = "Описание ошибки", example = "Пользователь не найден", position = 0)
    private final String message;
    @ApiModelProperty(notes = "Время возникновения", example = "1559751301818", position = 1)
    private final long created;

    public ErrorTimeResponse(String message, long created) {
        this.message = message;
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public long getCreated() {
        return created;
    }
}
