package island.dev.repository;

import io.quarkus.test.junit.QuarkusTest;
import island.dev.entity.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class OrderItemRepositoryTest {
    @Inject
    EntityManager em;
    OrderItemRepository orderItemRepository;

    @Transactional
    public void setUp() {
        //Given
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "h@i.com";
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
    }

    @Test
    @Transactional
    public void testFindTop5SellingItemsAllTime() {
        orderItemRepository = new OrderItemRepository();
        // Given

        // When
        List<String> result = orderItemRepository.findTop5SellingItemsAllTime();

        //Then
        assertFalse(result.isEmpty(), "Result should not be empty");
        String name = result.get(0);
        assertNotNull(name);
    }

    @Test
    @Transactional
    public void testFindTop5SellingItemsLastMonth() {
        orderItemRepository = new OrderItemRepository();
        // Given

        // When
        List<String> result = orderItemRepository.findTop5SellingItemsLastMonth();

        //Then
        assertTrue(result.isEmpty(), "Result should be empty");
    }
}
