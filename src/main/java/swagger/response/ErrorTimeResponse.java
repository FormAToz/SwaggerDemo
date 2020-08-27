package swagger.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Класс ответа с описанием ошибки и временем возникновения")
public class ErrorTimeResponse extends ErrorResponse{
    @ApiModelProperty(notes = "Время возникновения", example = "1559751301818", position = 1)
    private final long created;

    public ErrorTimeResponse(String message, long created) {
        super(message);
        this.created = created;
    }

    public String getMessage() {
        return super.getMessage();
    }

    public long getCreated() {
        return created;
    }
}
