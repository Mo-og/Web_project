package web.client.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.client.Category;
import web.client.Dish;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category,Long> {
    List<Category> findAllByOrderByName();
}