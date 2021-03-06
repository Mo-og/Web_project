package web.client.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.client.services.UserService;

import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(Principal principal) {
        try{
            final UserDetails user = userService.loadUserByUsername(principal.getName());

            switch(user.getAuthorities().toString()) {
                case "[ROLE_Authorized]":
                    return "authorized_user/index";
                default: System.out.println(user.getAuthorities().toString());
            }
        }
        catch (NullPointerException e) {
            return  "user/index";
        }
        return  "user/index";
    }
}