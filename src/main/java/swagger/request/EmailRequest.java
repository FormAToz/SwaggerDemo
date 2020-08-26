package swagger.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Класс запроса email`а пользователя")
public class EmailRequest {
    @ApiModelProperty(notes = "Email пользователя", example = "7.danilov@gmail.com")
    private final String email;

    public EmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
