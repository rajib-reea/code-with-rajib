package island.dev.repository;

import io.quarkus.test.junit.QuarkusTest;
import island.dev.entity.Customer;
import island.dev.entity.Order;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class OrderRepositoryTest {
    @Inject
    EntityManager em;
    OrderRepository orderRepository;

    @Transactional
    public void setUp() {
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "g@h.com";
        customer.createdAt=Instant.now();
        em.persist(customer);
        em.flush();

        // When
        Order order = new Order();
        order.customer=customer;
        order.orderDate= Instant.now();
        order.totalAmount=BigDecimal.valueOf(49.00);
        em.persist(order);
        em.flush();
    }

    @Test
    @Transactional
    public void testFindTotalSaleAmountOfCurrentDay() {
        orderRepository = new OrderRepository();
        // Given

        // When
        BigDecimal result = orderRepository.findTotalSaleAmountOfCurrentDay();

        //Then
        assertNotNull(result);
    }

    @Test
    @Transactional
    public void testFindMaxSaleDayOfTimeRange() {
        orderRepository = new OrderRepository();
        // Given
        LocalDateTime startDate = LocalDateTime.of(2024, 7, 5, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 7, 5, 0, 0);

        // When
        List<Tuple> result = orderRepository.findMaxSaleDayOfTimeRange(startDate, endDate);

        //Then
        assertFalse(result.isEmpty(), "Result should not be empty");

        Tuple maxSaleDay = result.get(0);
        Instant orderDateInstant = maxSaleDay.get("orderDate", Instant.class);
        LocalDateTime orderDate = orderDateInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        BigDecimal totalAmount = maxSaleDay.get("totalAmount", BigDecimal.class);
        assertNotNull(orderDate);
    }
}
