package island.dev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import island.dev.entity.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Tuple;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

//    public BigDecimal findTotalSaleAmountOfCurrentDay() {
//        return find("orderDate >= ?1 and orderDate < ?2", LocalDateTime.now().toLocalDate().atStartOfDay(), LocalDateTime.now().toLocalDate().atStartOfDay().plusDays(1))
//                .project(BigDecimal.class)
//                .singleResultOptional().orElse(BigDecimal.ZERO);
//    }

    public BigDecimal findTotalSaleAmountOfCurrentDay() {
        LocalDateTime startOfDayLocal = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDayLocal = startOfDayLocal.plusDays(1);
        Instant startOfDay = startOfDayLocal.atZone(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = endOfDayLocal.atZone(ZoneId.systemDefault()).toInstant();


        return getEntityManager().createQuery(
                        "SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE DATE(o.orderDate) >= :startOfDay AND o.orderDate < :endOfDay",
                        BigDecimal.class)
                .setParameter("startOfDay", startOfDay)
                .setParameter("endOfDay", endOfDay)
                .getSingleResult();
    }

    public List<Tuple> findMaxSaleDayOfTimeRange(LocalDateTime startDate, LocalDateTime endDate) {
        return getEntityManager()
                .createQuery("SELECT o.orderDate as orderDate, SUM(o.totalAmount) as totalAmount FROM Order o WHERE DATE(o.orderDate) BETWEEN :startDate AND :endDate GROUP BY o.orderDate ORDER BY SUM(o.totalAmount) DESC LIMIT 1", Tuple.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

}
