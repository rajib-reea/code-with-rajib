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
        customer.email = "c@d.com";
        em.persist(customer);
        em.flush();

        Order order = new Order();
        order.customer=customer;
        order.orderDate= Instant.now();
        order.totalAmount=BigDecimal.valueOf(49.00);
        em.persist(order);
        em.flush();

        Product product = new Product();
        product.name = "blade";
        product.description = "gillette blade";
        product.price=BigDecimal.valueOf(19.00);
        product.createdAt=Instant.now();
        em.persist(product);
        em.flush();

        // When
        OrderItem orderItem = new OrderItem();
        orderItem.order=order;
        orderItem.product=product;
        orderItem.quantity=1;
        orderItem.price=BigDecimal.valueOf(9.00);
        em.persist(orderItem);
        em.flush();


        // Then
        //direct
        assertNotNull(orderItem);
        assertNotNull(orderItem.order);
        assertNotNull(orderItem.product);
        assertEquals(1, orderItem.quantity);
        assertEquals(BigDecimal.valueOf(9.00), orderItem.price);

        //db
        OrderItem persistedOrderItem = em.find(OrderItem.class, orderItem.id);
        assertNotNull(persistedOrderItem);
        assertEquals(order.id, persistedOrderItem.order.id);
        assertEquals(product.id, persistedOrderItem.product.id);
        assertEquals(1, persistedOrderItem.quantity);
        assertEquals(BigDecimal.valueOf(9.00), persistedOrderItem.price);
    }
}
