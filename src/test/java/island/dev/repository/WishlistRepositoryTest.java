package island.dev.repository;

import io.quarkus.test.junit.QuarkusTest;
import island.dev.entity.Customer;
import island.dev.entity.Wishlist;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class WishlistRepositoryTest {
    @Inject
    EntityManager em;
    WishlistRepository wishlistRepository;

    @BeforeEach
    @Transactional
    public void setUp() {
        wishlistRepository = new WishlistRepository();
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "f@g.com";
        em.persist(customer);
        em.flush();

        Wishlist wishlist = new Wishlist();
        wishlist.customer=customer;
        wishlist.name="my-wishlist";
        wishlist.createdAt= Instant.now();
        em.persist(wishlist);
        em.flush();
    }

    @Test
    @Transactional
    public void testFindByCustomerId() {
        // Given
        Customer customer = em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                .setParameter("email", "f@g.com")
                .getSingleResult();

        // When
        List<Wishlist> wishlists = wishlistRepository.findByCustomerId(customer.id);

        // Then
        assertNotNull(wishlists);
        assertFalse(wishlists.isEmpty());
        assertEquals(1, wishlists.size());
        assertEquals("f@g.com", wishlists.get(0).customer.email);
    }
}
