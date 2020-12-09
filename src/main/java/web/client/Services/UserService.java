package web.client.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import web.client.MyUserDetails;
import web.client.Repositories.UserRepository;
import web.client.User;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getById(long id) {
        return repository.getOne(id);
    }

    public void removeById(long id) {
        repository.deleteById(id);
    }

    public boolean existsWithId(long id) {
        return repository.existsById(id);
    }

    public User getByUsername(String username) {
        Optional temp = repository.findByUsername(username);
        if (temp.isPresent())
            return (User) temp.get();
        else return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        System.out.println(user);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user.map(MyUserDetails::new).get();
    }
}
