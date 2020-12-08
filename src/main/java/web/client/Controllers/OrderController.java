package web.client.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.client.Order;
import web.client.Sale;
import web.client.Services.OrderService;
import web.client.Services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.NoSuchElementException;

@Controller
public class OrderController {

    public static OrderService service;
    @Autowired
    UserService userService;


    @Autowired
    public void setService(OrderService service) {
        OrderController.service = service;
    }


    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        try {
            model.addAttribute("orders", service.getAllOrders());
            model.addAttribute("new_order", new Order());

            final UserDetails user = userService.loadUserByUsername(principal.getName());

            switch (user.getAuthorities().toString()) {
                case "[ROLE_Admin]":
                    return "admin/orders";
                case "[ROLE_Client]":
                    return "client/orders";
                default:
                    return "user/index";
            }
        } catch (NullPointerException e) {
            return "user/index";
        }
    }


    @PostMapping("/orders")
    public String saveOrder(Model model, Principal principal) {
        Order order = new Order();
        order.setDateOrdered(new Date(System.currentTimeMillis()));
        service.saveOrder(order);
        Order newOrder=service.getByDateOrdered(order.getDateOrdered());
        return "redirect:/edit_order?id="+newOrder.getId();
    }


    @PostMapping("/order_update")
    public String updateOrder(@Valid Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("order", order);
            model.addAttribute("container", new Sale());
            model.addAttribute("dishes", CatalogController.getAllDishes());
            return "client/edit_order";
        }
        Order to_save = service.getById(order.getId());
        service.saveOrder(to_save);
        model.addAttribute("order", order);
        model.addAttribute("container", new Sale());
        model.addAttribute("dishes", CatalogController.getAllDishes());
        return "redirect:/order_edit?id=" + order.getId();
    }

    @GetMapping("/order_edit")
    public String editDish(Model model, @RequestParam Long id) {
        Order order = service.getById(id);
        Sale container = new Sale();
        model.addAttribute("order", order);
        model.addAttribute("container", container);
        model.addAttribute("dishes", CatalogController.getAllDishes());
        return "client/edit_order";
    }


    @GetMapping("/change_status")
    public String editStatus(Model model, @RequestParam Long id) {
        Order order = service.getById(id);
        model.addAttribute("order", order);
        model.addAttribute("dishes", CatalogController.getAllDishes());
        return "admin/change_status";
    }

    @GetMapping("/order_remove")
    public String removeOrder(Model model, @RequestParam Long id) {
        model.addAttribute("orders", service.getAllOrders());
        model.addAttribute("new_order", new Order());
        if (!service.existsWithId(id))
            throw new NoSuchElementException();
        service.removeById(id);
        return "redirect:/orders";
    }

}
