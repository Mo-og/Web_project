package web.client.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.client.Dish;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllByOrderByCategory();
}
