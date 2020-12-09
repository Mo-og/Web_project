package web.client;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sales")
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(insertable = false, updatable = false)
    private long dish_id = -1;
    @Column(insertable = false, updatable = false)
    private long order_id = -1;
    private int quantity;
    private int status=0;//0-new 1-inprocess 2-completed

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    public long getOrder_id() {
        if (order == null)
            return order_id;
        return order.getId();
    }
    public long getDish_id() {
        if (dish == null)
            return dish_id;
        return dish.getId();
    }
    public double getCost() {
        return dish.getPrice() * quantity;
    }

}
