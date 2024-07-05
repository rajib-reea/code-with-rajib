package island.dev.repository;

import island.dev.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi.product.id, SUM(oi.quantity * oi.price) AS totalSaleAmount FROM OrderItem oi GROUP BY oi.product.id ORDER BY totalSaleAmount DESC LIMIT 5")
    List<Object[]> findTop5SellingItemsAllTime();

    @Query("SELECT oi.product.id, SUM(oi.quantity) AS totalQuantity FROM OrderItem oi JOIN oi.order o WHERE o.orderDate >= :startDate AND o.orderDate < :endDate GROUP BY oi.product.id ORDER BY totalQuantity DESC LIMIT 5")
    List<Object[]> findTop5SellingItemsLastMonth(LocalDateTime startDate, LocalDateTime endDate);
}