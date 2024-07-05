package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class WishlistTest {

    @Inject
    EntityManager em;
    Customer customer;

    @Test
    @Transactional
    public void testCreateDummyWishlist() {
        // Given
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "x@y.com";
        // Persist the customer to simulate the real scenario
        em.persist(customer);
        em.flush();

        // When
        Wishlist wishlist = Wishlist.createDummyWishlist(customer);

        // Then
        assertNotNull(wishlist);
        assertNotNull(wishlist.customer);
        assertNotNull(wishlist.createdAt);
        assertEquals("Doe", wishlist.name);

        // Persist and retrieve the order to ensure it can be saved in the database
        em.persist(wishlist);
        em.flush();
        Wishlist persistedWishlist = em.find(Wishlist.class, wishlist.id);

        assertNotNull(persistedWishlist);
        assertEquals(customer.firstName, persistedWishlist.customer.firstName);
        assertEquals("Doe", persistedWishlist.name);
    }
}
