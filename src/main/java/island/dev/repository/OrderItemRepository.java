package island.dev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import island.dev.entity.OrderItem;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderItemRepository implements PanacheRepository<OrderItem> {

    public List<String> findTop5SellingItemsAllTime() {
        return getEntityManager()
                .createQuery("SELECT p.name " +
                        "FROM ( " +
                        "    SELECT oi.product.id AS productId, SUM(oi.quantity) AS totalQuantity " +
                        "    FROM Order o, OrderItem oi, Product p " +
                        "    WHERE oi.order.id = o.id " +
                        "    GROUP BY oi.product.id " +
                        "    ORDER BY totalQuantity DESC " +
                        ") sub, Product p " +
                        "WHERE sub.productId = p.id", String.class)
                .getResultList();
    }

    public List<String> findTop5SellingItemsLastMonth() {
        String nativeQuery = "SELECT p.name " +
                "FROM products p " +
                "JOIN ( " +
                "    SELECT oi.product_id AS productId, SUM(oi.quantity) AS totalQuantity " +
                "    FROM orders o " +
                "    JOIN order_items oi ON oi.order_id = o.order_id " +
                "    WHERE o.order_date BETWEEN DATE_TRUNC('month', NOW()) - INTERVAL '1 month' " +
                "                        AND DATE_TRUNC('month', NOW()) - INTERVAL '1 day' " +
                "    GROUP BY oi.product_id " +
                "    ORDER BY totalQuantity DESC " +
                "    LIMIT 5 " +
                ") sub ON sub.productId = p.product_id";

        @SuppressWarnings("unchecked")
        List<String> result = getEntityManager()
                .createNativeQuery(nativeQuery)
                .getResultList();
        return result;
    }



}