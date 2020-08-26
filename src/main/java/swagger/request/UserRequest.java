package swagger.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Класс запроса регистрации пользователя")
public class UserRequest {
    @ApiModelProperty(notes = "Имя пользователя", example = "Андрей", position = 0)
    private final String name;
    @ApiModelProperty(notes = "Email пользователя", example = "7.danilov@gmail.com", position = 1)
    private final String email;
    @ApiModelProperty(notes = "Возраст пользователя", example = "34", position = 2)
    private final int age;

    public UserRequest(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
}
