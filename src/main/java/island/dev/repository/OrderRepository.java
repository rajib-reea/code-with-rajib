package island.dev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import island.dev.entity.Order;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public BigDecimal findTotalSaleAmountOfCurrentDay() {
        return find("orderDate >= ?1 and orderDate < ?2", LocalDateTime.now().toLocalDate().atStartOfDay(), LocalDateTime.now().toLocalDate().atStartOfDay().plusDays(1))
                .project(BigDecimal.class)
                .singleResultOptional().orElse(BigDecimal.ZERO);
    }

    public List findMaxSaleDayOfTimeRange(LocalDateTime startDate, LocalDateTime endDate) {
        return getEntityManager()
                .createQuery("SELECT o.orderDate, SUM(o.totalAmount) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY o.orderDate ORDER BY SUM(o.totalAmount) DESC")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
