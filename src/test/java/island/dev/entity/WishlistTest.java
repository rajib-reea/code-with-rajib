package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.Instant;

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
        customer.email = "d@e.com";
        em.persist(customer);
        em.flush();

        // When
        Wishlist wishlist = new Wishlist();
        wishlist.customer=customer;
        wishlist.name="my-wishlist";
        wishlist.createdAt= Instant.now();
        em.persist(wishlist);
        em.flush();

        // Then
        //direct
        assertNotNull(wishlist);
        assertNotNull(wishlist.customer);
        assertNotNull(wishlist.createdAt);
        assertEquals("my-wishlist", wishlist.name);

        //db
        Wishlist persistedWishlist = em.find(Wishlist.class, wishlist.id);
        assertNotNull(persistedWishlist);
        assertEquals(customer.firstName, persistedWishlist.customer.firstName);
        assertEquals("my-wishlist", persistedWishlist.name);
    }
}
