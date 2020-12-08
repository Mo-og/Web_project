package web.client.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.client.Order;
import web.client.Repositories.OrderRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private OrderRepository repository;

    @Autowired
    public void setRepository(OrderRepository repository) {
        this.repository = repository;
    }

    public void saveOrder(Order order) {
        repository.save(order);
    }

    public List<Order> getAllOrders() {
        List<Order> n = repository.findAll();
        n.sort(comparator);
        return n;
    }

    Comparator<Order> comparator = (o1, o2) -> {
        if (o1.getDateOrdered().getTime() < o2.getDateOrdered().getTime())
            return -1;
        else if (o1.getDateOrdered().getTime() == o2.getDateOrdered().getTime())
            return 0;
        return 1;
    };


    public void removeById(long id) {
        repository.deleteById(id);
    }

    public boolean existsWithId(long id) {
        return repository.existsById(id);
    }

    public Order getById(long id) {
        return repository.getOne(id);
    }

    public Order getByDateOrdered(Date date) {
        return repository.findFirstByDateOrdered(date);
    }
}