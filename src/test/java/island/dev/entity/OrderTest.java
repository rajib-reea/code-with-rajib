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
        customer.email = "b@c.com";
        em.persist(customer);
        em.flush();

        // When
        Order order = new Order();
        order.customer=customer;
        order.orderDate= Instant.now();
        order.totalAmount=BigDecimal.valueOf(49.00);
        em.persist(order);
        em.flush();

        // Then
        //direct
        assertNotNull(order);
        assertNotNull(order.customer);
        assertNotNull(order.orderDate);
        assertEquals(BigDecimal.valueOf(49.00), order.totalAmount);

        //db
        Order persistedOrder = em.find(Order.class, order.id);
        assertNotNull(persistedOrder);
        assertEquals(customer.firstName, persistedOrder.customer.firstName);
        assertEquals(BigDecimal.valueOf(49.00), persistedOrder.totalAmount);
    }
}
