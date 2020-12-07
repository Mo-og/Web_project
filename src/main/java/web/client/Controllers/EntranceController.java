package web.client.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import web.client.EntranceForm;

import javax.validation.Valid;

@Controller
public class EntranceController {

    @GetMapping("/entrance")
    public String getEntrance(Model model) {
        model.addAttribute("entrance", new EntranceForm());
        return "user/entrance";
    }

    @GetMapping("/logout")
    public String logOut() {
        return "user/index";
    }

    @PostMapping("/entrance")
    public String validateEntrance(@Valid EntranceForm entrance, BindingResult result) {
        if (result.hasErrors()) {
            return "user/entrance";
        }
        return "authorized_user/index";
    }
}