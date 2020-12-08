package web.client.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.client.Repositories.SaleRepository;
import web.client.Sale;

import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository repository;

    public void saveDetail(Sale sale) {
        Sale det = repository.findByOrder_idAndDish_id(sale.getOrder_id(), sale.getDish_id());
        if (det != null) {
            det.setQuantity(det.getQuantity() + sale.getQuantity());
            repository.save(det);
            return;
        }
        repository.save(sale);
    }

    public void forceSaveDetail(Sale sale) {
        repository.save(sale);
    }

    public Sale findByOrderIdAndDishID(long order_id, long dish_id) {
        return repository.findByOrder_idAndDish_id(order_id, dish_id);
    }

    public void removeByOrderIdAndDishID(long order_id, long dish_id) {
        Sale sale = repository.findByOrder_idAndDish_id(order_id, dish_id);
        sale.setDish(null);
        sale.setOrder(null);
        repository.delete(sale);
    }

    public void remove(Sale detail) {
        detail.setDish(null);
        detail.setOrder(null);
        repository.delete(detail);
    }

    public List<Sale> getAllDishes() {
        return repository.findAll();
    }

    public void removeById(long id) {
        repository.deleteById(id);
    }

    public boolean existsWithId(long id) {
        return repository.existsById(id);
    }

    public Sale getById(long id) {
        return repository.getOne(id);
    }

}