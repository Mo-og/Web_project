package web.client.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.client.Order;

import java.util.Date;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findFirstByDateOrdered(Date dateOrdered);
}
