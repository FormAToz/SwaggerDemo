package swagger.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Класс ответа с id пользователя")
public class UserIdResponse {
    @JsonProperty("idUser")
    @ApiModelProperty(notes = "id пользователя", example = "7")
    private final int userId;

    public UserIdResponse(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
