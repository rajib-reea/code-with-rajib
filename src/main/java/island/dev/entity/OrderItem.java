package island.dev.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_items")
public class OrderItem extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    public Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    public Order order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    @NotNull
    @Column(name = "quantity", nullable = false)
    public Integer quantity;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    public BigDecimal price;

    // Method to create and return a dummy OrderItem object
    public static OrderItem createDummyOrderItem(Order order, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.order = order;
        orderItem.product = product;
        orderItem.quantity = 1;
        orderItem.price = BigDecimal.valueOf(19.00);
        return orderItem;
    }

    // Method to print the dummy orderItem for verification
    public void printOrderItemInfo() {
        System.out.println("ID: " + id);
        System.out.println("Order: " + order);
        System.out.println("Product: " + product);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);
    }
}