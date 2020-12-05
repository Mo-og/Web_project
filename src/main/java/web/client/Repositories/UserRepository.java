package web.client.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.client.User;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
