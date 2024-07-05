package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class OrderTest {

    @Inject
    EntityManager em;
    Customer customer;

    @Test
    @Transactional
    public void testCreateDummyOrder() {
        // Given
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "a@b.com";
        // Persist the customer to simulate the real scenario
        em.persist(customer);
        em.flush();

        // When
        Order order = Order.createDummyOrder(customer);

        // Then
        assertNotNull(order);
        assertNotNull(order.customer);
        assertNotNull(order.orderDate);
        assertEquals(BigDecimal.valueOf(99.99), order.totalAmount);

        // Persist and retrieve the order to ensure it can be saved in the database
        em.persist(order);
        em.flush();
        Order persistedOrder = em.find(Order.class, order.id);

        assertNotNull(persistedOrder);
        assertEquals(customer.firstName, persistedOrder.customer.firstName);
        assertEquals(BigDecimal.valueOf(99.99), persistedOrder.totalAmount);
    }
}
