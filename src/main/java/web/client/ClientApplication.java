package web.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        System.out.println(new User(1,"1234","234234","Name","Surname","Patronymic",
                "Address","email","Roles"));
        SpringApplication.run(ClientApplication.class, args);
    }

}
