package web.client;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Pattern(regexp = "(\\+)?\\d{12}", message = "Номер телефона должен состоять из 12 цифр, включая код страны")
    private String username; //phone
    @Size(max = 500, min = 6, message = "Пароль должен быть в пределах 6-50 символов!")
    private String password;

    @Pattern(regexp = "[ A-Za-zА-Яа-яЁё]{2,100}", message = "Имя должно содержать только буквы (латинские или русские) и быть в пределах 2-100 символов.")
    private String name;
    @Pattern(regexp = "[ A-Za-zА-Яа-яЁё]{2,100}", message = "Фамилия должна содержать только буквы (латинские или русские) и быть в пределах 2-100 символов.")
    private String surname;
    @Pattern(regexp = "[ A-Za-zА-Яа-яЁё]{2,100}", message = "Отчество должно содержать только буквы (латинские или русские) и быть в пределах 2-100 символов.")
    private String patronymic;
    private String address;
    @Email(message = "Адрес электронной почты должен быть действительным")
    private String email;
    private String roles;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }



    public User(@Pattern(regexp = "(\\+)?\\d{12}", message = "Номер телефона должен состоять из 12 цифр, включая код страны") String username, @Size(max = 500, min = 6, message = "Пароль должен быть в пределах 6-50 символов!") String password, @Pattern(regexp = "[ A-Za-zА-Яа-яЁё]{2,100}", message = "Имя должно содержать только буквы (латинские или русские) и быть в пределах 2-100 символов.") String name, @Pattern(regexp = "[ A-Za-zА-Яа-яЁё]{2,100}", message = "Фамилия должна содержать только буквы (латинские или русские) и быть в пределах 2-100 символов.") String surname, @Pattern(regexp = "[ A-Za-zА-Яа-яЁё]{2,100}", message = "Отчество должно содержать только буквы (латинские или русские) и быть в пределах 2-100 символов.") String patronymic, String address, @Email(message = "Адрес электронной почты должен быть действительным") String email, String roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.address = address;
        this.email = email;
        this.roles = roles;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", \npatronymic='" + patronymic + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
