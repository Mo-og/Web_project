package web.client.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.client.Category;
import web.client.Services.CategoryService;
import web.client.Services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
public class CategoryController {
    @Autowired
    CategoryService service;
    @Autowired
    UserService userService;

    @GetMapping("/categories")
    public String getCategory(Model model, Principal principal) {
        try {
            final UserDetails user = userService.loadUserByUsername(principal.getName());
            model.addAttribute("categories", service.getAllCategories());
            switch (user.getAuthorities().toString()) {
                case "[ROLE_Admin]":
                    return "admin/categories";
                default:return "user/catalog";
            }
        } catch (NullPointerException e) {
            return "user/index";
        }
    }

    @GetMapping("/add_category")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "admin/add_category";
    }

    @GetMapping("/category_edit")
    public String editCategory(Model model, @RequestParam Long id) {
        Category category = service.getById(id);
        model.addAttribute("category", category);
        System.out.println("Получено " + category);
        return "admin/edit_category";
    }

    @PostMapping("/category_update")
    public String updateCategory(@Valid Category category, BindingResult result) {
        if (result.hasErrors())
            return "admin/edit_category";
        service.saveCategory(category);
        System.out.println("Обновлено " + category);
        return "redirect:/categories";
    }

    @PostMapping("/add_category")
    public String addCategoryPost(@Valid Category category, BindingResult result) {
        System.out.println("Отправлено " + category);
        if (result.hasErrors())
            return "admin/add_category";
        service.saveCategory(category);
        System.out.println("Успешно добавлено " + category);
        return "redirect:/categories";
    }

    @GetMapping("/category_remove")
    public String removeCategory(@RequestParam Long id) {
        if (!service.existsWithId(id))
            throw new NoSuchElementException();
        service.removeById(id);
        return "redirect:/categories";
    }
}