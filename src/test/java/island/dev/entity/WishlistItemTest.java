package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

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
        customer.email = "b@c.com";
        em.persist(customer);
        em.flush();

        Wishlist wishlist = new Wishlist();
        wishlist.customer = customer;
        wishlist.name = "Doe";
        em.persist(wishlist);
        em.flush();

        Product product = new Product();
//        product.id = 1;
        product.name = "Sample Product";
        product.price=BigDecimal.valueOf(19.00);
        em.persist(product);
        em.flush();

        // When
        WishlistItem wishlistItem = WishlistItem.createDummyWishlistItem(wishlist, product);

        // Then
        assertNotNull(wishlistItem);
        assertNotNull(wishlistItem.wishlist);
        assertNotNull(wishlistItem.product);
        assertEquals("Doe", wishlistItem.wishlist.name);

        //Persist and retrieve the order item to ensure it can be saved in the database
        em.persist(wishlistItem);
        em.flush();
        OrderItem persistedWishlistItem = em.find(OrderItem.class, wishlistItem.id);

        assertNotNull(persistedWishlistItem);
        assertEquals(product.id, persistedWishlistItem.product.id);
    }
}
