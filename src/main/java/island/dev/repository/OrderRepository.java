package island.dev.repository;

import island.dev.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.orderDate >= CURRENT_DATE AND o.orderDate < CURRENT_DATE + 1")
    BigDecimal findTotalSaleAmountOfCurrentDay();

    @Query("SELECT o.orderDate, SUM(o.totalAmount) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY o.orderDate ORDER BY SUM(o.totalAmount) DESC")
    List<Object[]> findMaxSaleDayOfTimeRange(LocalDateTime startDate, LocalDateTime endDate);
}