package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class WishlistItemTest {

    @Inject
    EntityManager em;
    Customer customer;
    Wishlist wishlist;

    @Test
    @Transactional
    public void testCreateDummyWishlistItem() {
        // Given
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "e@f.com";
        customer.createdAt= Instant.now();
        em.persist(customer);
        em.flush();

        Wishlist wishlist = new Wishlist();
        wishlist.customer = customer;
        wishlist.name = "my-wishlist";
        wishlist.createdAt= Instant.now();
        em.persist(wishlist);
        em.flush();

        Product product = new Product();
        product.name = "scissors";
        product.description = "gillette scissors";
        product.price=BigDecimal.valueOf(9.00);
        product.createdAt=Instant.now();
        em.persist(product);
        em.flush();

        // When
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.wishlist=wishlist;
        wishlistItem.product=product;
        wishlistItem.addedAt=Instant.now();
        em.persist(wishlistItem);
        em.flush();

        // Then
        //direct
        assertNotNull(wishlistItem);
        assertNotNull(wishlistItem.wishlist);
        assertNotNull(wishlistItem.product);
        assertEquals("my-wishlist", wishlistItem.wishlist.name);

        //db
        WishlistItem persistedWishlistItem = em.find(WishlistItem.class, wishlistItem.id);
        assertNotNull(persistedWishlistItem.wishlist);
        assertNotNull(persistedWishlistItem.product);
        assertNotNull(persistedWishlistItem);
        assertEquals("my-wishlist", persistedWishlistItem.wishlist.name);
    }
}
