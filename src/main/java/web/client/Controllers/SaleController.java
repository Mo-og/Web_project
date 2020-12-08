package web.client.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.client.Dish;
import web.client.Order;
import web.client.Sale;
import web.client.Services.OrderService;
import web.client.Services.SaleService;

@Controller
public class SaleController {

    private static SaleService service;

    @Autowired
    private OrderService orderService;
    public static void removeDishFromOrder(long dish_id, long order_id) {
        service.removeByOrderIdAndDishID(order_id, dish_id);
    }


    @Autowired
    public void setDetailsService(SaleService service) {
        SaleController.service = service;
    }

    @GetMapping("/dish_exclude")
    public String removeDishFromOrder(@RequestParam Long dish_id, @RequestParam Long order_id, Model model) {
        service.removeByOrderIdAndDishID(order_id, dish_id);
        model.addAttribute("order", orderService.getById(order_id));
        model.addAttribute("dishes", CatalogController.getAllDishes());
        model.addAttribute("container", new Sale());
        return "redirect:/order_edit?id=" + order_id;
    }

    @PostMapping("/order_dish_add")
    public String addDishToOrder(Sale container, BindingResult result, Model model) {
        System.out.println("ПОЛУЧЕНО container: " + container);
        Order order = orderService.getById(container.getOrder_id());
        Dish dish_toSave = CatalogController.getDishById(container.getDish_id());
        container.setDish(dish_toSave);
        container.setOrder(order);
        if (result.hasErrors()) {
            model.addAttribute("dish", dish_toSave);
            model.addAttribute("order", order);
            model.addAttribute("dishes", CatalogController.getAllDishes());
            model.addAttribute("container", container);
            return "client/edit_order_dish";
        }
        service.saveDetail(container);
        model.addAttribute("order", order);
        model.addAttribute("dish", dish_toSave);
        model.addAttribute("dishes", CatalogController.getAllDishes());
        model.addAttribute("container", container);
        return "redirect:/order_edit?id=" + container.getOrder_id();
    }

    @GetMapping("/order_dish_edit")
    public String viewDishFromOrder(Model model, @RequestParam Long dish_id, @RequestParam Long order_id) {
        Sale sale = service.findByOrderIdAndDishID(order_id, dish_id);
        model.addAttribute("order", sale.getOrder());
        model.addAttribute("dish", sale.getDish());
        model.addAttribute("dishes", CatalogController.getAllDishes());
        model.addAttribute("container", sale);
        return "client/edit_order_dish";
    }

    @PostMapping("/order_dish_edit")
    public String editDishFromOrder(@RequestParam Long dish_id, Sale container, BindingResult result, Model model) {
        System.out.println("ПОЛУЧЕНО container: " + container);
        Order order = orderService.getById(container.getOrder_id());
        Dish dish_toSave = CatalogController.getDishById(container.getDish_id());
        if (result.hasErrors()) {
            model.addAttribute("order", order);
            model.addAttribute("dishes", CatalogController.getAllDishes());
            model.addAttribute("container", container);
            return "client/edit_order_dish";
        }
        service.remove(container);
        container.setDish(dish_toSave);
        container.setOrder(order);
        service.saveDetail(container);
        model.addAttribute("order", order);
        model.addAttribute("dish", dish_toSave);
        model.addAttribute("dishes", CatalogController.getAllDishes());
        model.addAttribute("container", container);
        return "redirect:/order_edit?id=" + container.getOrder_id();
    }

}
