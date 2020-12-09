package web.client;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date dateOrdered;
    private boolean isDone = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Sale> sales;


    public Order() {
    }

    public Order(ArrayList<Sale> sales) {
        this.sales = sales;
    }

    public void sortByQuantity() {
        sales.sort(Comparator.comparingInt(Sale::getQuantity).reversed());
    }

    public Sale getSalesIfPresent(long id) {
        return sales.stream().filter(i -> i.getDish_id() == id).findFirst().orElse(null);
    }

    public boolean removeFromOrder(long id) {
        Sale sale = sales.stream().filter(i -> i.getDish().getId() == id
                || i.getDish_id() == id).findFirst().orElse(null);
        if (sale == null)
            throw new NoSuchElementException();
        System.out.println("Удаляем из заказа №" + id + ": " + sale);
        return sales.remove(sale);
    }

    public void removeDetail(Sale sale) {
        sales.remove(sale);
    }

    public String getStatus() {
        if (isDone) return "Выполнен";
        boolean inProcess = false;
        boolean isNew = true;
        boolean isFinished = false;
        for (Sale sale : sales) {
            if (sale.getStatus() == 1)
                inProcess = true;
            else if (sale.getStatus() != 0)
                isNew = false;
            else isFinished = true;
        }
        int currentCase =
                isNew ? 0 :
                        (inProcess && !isFinished ? 1 :
                                (inProcess && isFinished ? 2 : 3));
        switch (currentCase) {
            case 0:
                return "Новый";
            case 1:
                return "Сбор заказа начат";
            case 2:
                return "Выполняется";
            case 3:
                return "Собран и доставляется";
            default:
                throw new IllegalStateException("Unexpected status value: " + currentCase);
        }
    }

    public void setDoneStatus(Boolean isDone) {
        this.isDone = isDone;
    }


    public void addSale(Sale sale) {
        if (sales == null) sales = new ArrayList<>();
        Sale presented = getSalesIfPresent(sale.getDish_id());
        if (presented != null) {
            int newQuantity = sale.getQuantity() + presented.getQuantity();
            if (newQuantity == 0) {
                sales.remove(sale);
            } else if (newQuantity < 0)
                throw new IllegalArgumentException("Incorrect dish quality. Cannot be less than 0.");
            else presented.setQuantity(newQuantity);
            System.out.println("Увеличеваем количество блюд: quantity(" + presented.getDish().getName() + ") = " + sale.getQuantity());
        } else {
            sales.add(sale);
            System.out.println("Добавляем блюдо: (" + sale.getDish().getName() + ") в количестве: " + sale.getQuantity());
        }
    }

    public String getDishNames() {
        String dishNames = "";
        for (Sale sale : sales) {
            dishNames = dishNames.concat(", " + sale.getDish().getName() + " (" + sale.getQuantity() + " шт.)");
        }
        dishNames = dishNames.replaceFirst(", ", "");
        return dishNames;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public double getCost() {
        double cost = 0;
        for (Sale sale : sales) {
            cost += sale.getCost();
        }
        return cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date_ordered=" + dateOrdered +
                ", isDone=" + isDone +
                ", details=" + sales +
                '}';
    }
}