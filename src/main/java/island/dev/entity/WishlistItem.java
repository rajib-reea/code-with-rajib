package island.dev.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.Instant;

@Entity
@Table(name = "wishlist_items")
public class WishlistItem extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_item_id", nullable = false)
    public Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "wishlist_id", nullable = false)
    public Wishlist wishlist;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "added_at")
    public Instant addedAt;

    // Method to create and return a dummy WishlistItem object
    public static WishlistItem createDummyWishlistItem(Wishlist wishlist, Product product) {
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.wishlist = wishlist;
        wishlistItem.product = product;
        wishlistItem.addedAt = Instant.now();
        return wishlistItem;
    }

    // Method to print the dummy wishlistItem for verification
    public void printWishlistItemInfo() {
        System.out.println("ID: " + id);
        System.out.println("Wishlist: " + wishlist);
        System.out.println("Product: " + product);
        System.out.println("Added At: " + addedAt);
    }
}