package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class OrderItemTest {

    @Inject
    EntityManager em;
    Order order;
    Product product;

    @Test
    @Transactional
    public void testCreateDummyOrderItem() {
        // Given
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "b@c.com";
        em.persist(customer);
        em.flush();

        Order order = new Order();
        order.customer = customer;
        order.totalAmount = BigDecimal.valueOf(100.00);
        em.persist(order);
        em.flush();

        Product product = new Product();
//        product.id = 1;
        product.name = "Sample Product";
        product.price=BigDecimal.valueOf(19.00);
        em.persist(product);
        em.flush();

        // When
        OrderItem orderItem = OrderItem.createDummyOrderItem(order, product);

        // Then
        assertNotNull(orderItem);
        assertNotNull(orderItem.order);
        assertNotNull(orderItem.product);
        assertEquals(1, orderItem.quantity);
        assertEquals(BigDecimal.valueOf(19.00), orderItem.price);

        //Persist and retrieve the order item to ensure it can be saved in the database
        em.persist(orderItem);
        em.flush();
        OrderItem persistedOrderItem = em.find(OrderItem.class, orderItem.id);

        assertNotNull(persistedOrderItem);
        assertEquals(order.id, persistedOrderItem.order.id);
        assertEquals(product.id, persistedOrderItem.product.id);
        assertEquals(1, persistedOrderItem.quantity);
        assertEquals(BigDecimal.valueOf(19.00), persistedOrderItem.price);
    }
}
