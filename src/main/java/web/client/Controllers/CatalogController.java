package web.client.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.client.Dish;
import web.client.Services.CatalogService;
import web.client.Services.CategoryService;
import web.client.Services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class CatalogController {

    private static CatalogService service;

    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;


    @Autowired
    public void setService(CatalogService service) {
        CatalogController.service = service;
    }

    public static List<Dish> getAllDishes() {
        return service.getAllDishes();
    }

    public static Dish getDishById(long id) {
        return service.getById(id);
    }

    @GetMapping("/catalog")
    public String getMenu(Model model, Principal principal) {
        try {
            model.addAttribute("dishes", service.getAllDishes());
            final UserDetails user = userService.loadUserByUsername(principal.getName());

            switch (user.getAuthorities().toString()) {
                case "[ROLE_Client]":
                    return "client/catalog";
                case "[ROLE_Admin]":
                    return "admin/catalog";
            }
        } catch (NullPointerException e) {
            return "user/catalog";
        }
        return "user/catalog";
    }

    @GetMapping("/add_dish")
    public String addDish(Model model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/add_dish";
    }

    @GetMapping("/dish_edit")
    public String editDish(Model model, @RequestParam Long id) {
        Dish dish = service.getById(id);
        model.addAttribute("dish", dish);
        model.addAttribute("categories", categoryService.getAllCategories());
        System.out.println("Получено " + dish);
        return "admin/edit_dish";
    }

    @PostMapping("/dish_update")
    public String updateDish(@Valid Dish dish, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/edit_dish";
        }
        service.saveDish(dish);
        System.out.println("Обновлено " + dish);
        return "redirect:/menu";
    }

    @PostMapping("/add_dish")
    public String greetingSubmit(@Valid Dish dish, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/add_dish";
        }
        service.saveDish(dish);
        return "redirect:/menu";
    }

    @GetMapping("/dish_remove")
    public String removeDish(@RequestParam Long id) {
        if (!service.existsWithId(id))
            throw new NoSuchElementException();
        service.removeById(id);
        return "redirect:/menu";
    }
}