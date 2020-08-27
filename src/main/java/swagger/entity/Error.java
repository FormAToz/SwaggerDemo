package swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "errors")
@ApiModel(description = "Класс cущности ошибок")
public class Error {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Уникальный id", example = "7", position = 0)
    private int id;

    @ApiModelProperty(notes = "Сообщение", example = "Пользователь не найден", position = 1)
    private String message;

    @ApiModelProperty(notes = "Время регистрации ошибки", example = "1559751301818", position = 2)
    private long created;

    public Error() {
    }

    public Error(String message, long created) {
        this.message = message;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
