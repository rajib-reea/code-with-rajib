package island.dev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import island.dev.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderItemRepository implements PanacheRepository<OrderItem> {

    public List<Object[]> findTop5SellingItemsAllTime(int limit) {
        return find("SELECT oi.product.id, SUM(oi.quantity * oi.price) AS totalSaleAmount FROM OrderItem oi GROUP BY oi.product.id ORDER BY totalSaleAmount DESC")
//                .page(0, limit)
                .project(Object[].class)
                .list();
    }

    public List findTopSellingItemsLastMonth(LocalDateTime startDate, LocalDateTime endDate, int limit) {
        return getEntityManager()
                .createQuery("SELECT oi.product.id, SUM(oi.quantity) AS totalQuantity FROM OrderItem oi JOIN oi.order o WHERE o.orderDate >= :startDate AND o.orderDate < :endDate GROUP BY oi.product.id ORDER BY totalQuantity DESC")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setMaxResults(limit)
                .getResultList();
    }
}