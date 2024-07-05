package island.dev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import island.dev.entity.WishlistItem;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WishlistItemRepository implements PanacheRepository<WishlistItem> {
}