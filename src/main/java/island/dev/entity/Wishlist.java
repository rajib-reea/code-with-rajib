package island.dev.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.Instant;

@Entity
@Table(name = "wishlists")
public class Wishlist extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id", nullable = false)
    public Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer customer;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    public String name;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant createdAt;

    // Method to create and return a dummy Wishlist object
    public static Wishlist createDummyWishlist(Customer customer) {
        Wishlist wishlist = new Wishlist();
        wishlist.customer = customer;
        wishlist.name = "Doe";
        wishlist.createdAt = Instant.now();
        return wishlist;
    }

    // Method to print the dummy wishlist for verification
    public void printWishlistInfo() {
        System.out.println("ID: " + id);
        System.out.println("Customer: " + customer);
        System.out.println("Name: " + name);
        System.out.println("Created At: " + createdAt);
    }
}