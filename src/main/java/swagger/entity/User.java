package swagger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
                @UniqueConstraint(columnNames = "email")
        })
@ApiModel(description = "Класс cущности пользователя")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Уникальный id", example = "7", position = 0)
    private int id;
    @ApiModelProperty(notes = "Имя пользователя", example = "Андрей", position = 1)
    private String name;
    @ApiModelProperty(notes = "Email пользователя", example = "7.danilov@gmail.com", position = 2)
    private String email;
    @ApiModelProperty(notes = "Возраст пользователя", example = "34", position = 3)
    private int age;
    @ApiModelProperty(notes = "Время регистрации", example = "1559751301818", position = 4)
    private long created;
    @ApiModelProperty(notes = "Пароль (в данный момент совпадает с email)", position = 5)
    private String password;
    @JsonIgnore
    private  String role;

    public User() {
    }

    public User(String name, String email, int age, long created, String password, String role) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.created = created;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
