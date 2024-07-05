package island.dev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import island.dev.entity.Wishlist;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
@ApplicationScoped
public class WishlistRepository implements PanacheRepository<Wishlist> {
    public List<Wishlist> findByCustomerId(Long customerId) {
        return list("customer.id", customerId);
    }
}