package web.client;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Table(name = "dishes")
@Data
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long categoryId;
    @NotBlank(message = "У блюда должно быть название!")
    private String name;
    @Range(min = 1, message = "Масса блюда не может быть меньше 1!")
    private double price;
    private String info;
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<Sale> sales;

    public void addSale(Sale sale) {
        for (Sale sale1 : sales) {
            if (sale1.getOrder_id() != sale.getOrder_id())
                sales.add(sale);
        }
    }
}
