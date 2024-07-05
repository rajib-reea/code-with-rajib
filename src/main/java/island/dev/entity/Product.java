package island.dev.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    public Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    public String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String description;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    public BigDecimal price;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant createdAt;

    // Method to create and return a dummy Product object
    public static Product createDummyProduct() {
        Product product = new Product();
        product.name = "razor";
        product.description = "gillette razor";
        product.price = BigDecimal.valueOf(49.00);
        product.createdAt = Instant.now();
        return product;
    }

    // Method to print the dummy product for verification
    public void printProductInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
        System.out.println("Created At: " + createdAt);
    }
}