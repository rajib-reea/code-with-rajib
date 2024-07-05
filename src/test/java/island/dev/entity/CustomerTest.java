package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CustomerTest {
    @Inject
    EntityManager em;

    @Test
    @Transactional
    public void testCreateDummyCustomer() {
       //Given

        //When
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "a@b.com";
        customer.createdAt= Instant.now();
        em.persist(customer);
        em.flush();

        // Then
        //direct
        assertNotNull(customer);
        assertNotNull(customer.firstName);
        assertNotNull(customer.lastName);
        assertNotNull(customer.email);
        assertNotNull(customer.createdAt);

        //db
        Customer persistedCustomer = em.find(Customer.class, customer.id);
        assertNotNull(persistedCustomer);
        assertEquals("John", persistedCustomer.firstName);
        assertEquals("Doe", persistedCustomer.lastName);
        assertEquals("a@b.com", persistedCustomer.email);
        assertNotNull(persistedCustomer.createdAt);
    }
}
