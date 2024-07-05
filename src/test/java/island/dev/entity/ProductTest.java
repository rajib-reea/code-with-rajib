package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ProductTest {
    @Inject
    EntityManager em;

    @Test
    @Transactional
    public void testCreateDummyProduct() {
        //Given

        //When
        Product product = new Product();
        product.name = "razor";
        product.description = "gillette razor";
        product.price = BigDecimal.valueOf(49.00);
        product.createdAt = Instant.now();
        em.persist(product);
        em.flush();

        //Then
        //direct
        assertNotNull(product);
        assertNotNull(product.name);
        assertNotNull(product.description);
        assertNotNull(product.price);
        assertNotNull(product.createdAt);

        //db
        Product persistedProduct = em.find(Product.class, product.id);
        assertNotNull(persistedProduct);
        assertEquals("razor", persistedProduct.name);
        assertEquals("gillette razor", persistedProduct.description);
        assertEquals(BigDecimal.valueOf(49.00), persistedProduct.price);
        assertNotNull(persistedProduct.createdAt);
    }
}
