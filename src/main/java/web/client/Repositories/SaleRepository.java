package web.client.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import web.client.Sale;

public interface SaleRepository  extends JpaRepository<Sale,Long> {
    Sale findByOrder_idAndDish_id(long order_id, long dish_id);
}