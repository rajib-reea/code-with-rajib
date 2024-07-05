package island.dev.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    public Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer customer;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "order_date")
    public Instant orderDate;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    public BigDecimal totalAmount;

    // Method to create and return a dummy Order object
    public static Order createDummyOrder(Customer customer) {
        Order order = new Order();
        order.customer = customer;
        order.orderDate = Instant.now();
        order.totalAmount = BigDecimal.valueOf(99.99);
        return order;
    }

    // Method to print the dummy order for verification
    public void printOrderInfo() {
        System.out.println("ID: " + id);
        System.out.println("Customer: " + customer);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Total Amount: " + totalAmount);
    }
}