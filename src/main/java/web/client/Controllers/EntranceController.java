package web.client.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.client.EntranceForm;
import web.client.Services.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class EntranceController {
    @Autowired
    UserService service;

    @GetMapping("/")
    public String index(Principal principal) {
        try{
            final UserDetails user = service.loadUserByUsername(principal.getName());

            switch(user.getAuthorities().toString()) {
                case "[ROLE_Client]":return "client/index";
                case "[ROLE_Admin]":return "admin/index";
                default: System.out.println(user.getAuthorities().toString()); return  "user/index";
            }
        }
        catch (NullPointerException e) {
            return  "user/index";
        }

    }

    @GetMapping("/entrance")
    public String getEntrance(Model model, @RequestParam(defaultValue="false", value = "error") boolean hasError) {
        model.addAttribute("entrance", new EntranceForm());
        if (hasError)
            return "user/entrance_error";
        return "user/entrance";
    }

    @GetMapping("/logout")
    public String logOut() {
        return "user/index";
    }

    @PostMapping("/entrance")
    public String validateEntrance(Model model, @Valid EntranceForm entrance, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("entrance", entrance);
            return "user/entrance_error";
        }
        return "client/index";
    }
}