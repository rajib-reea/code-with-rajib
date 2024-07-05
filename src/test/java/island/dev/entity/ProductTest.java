package island.dev.entity;

import com.google.inject.Inject;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ProductTest {
    @Inject
    EntityManager em;
    Customer customer;

    @Test
    public void testCreateDummyProduct() {
        // Given
        //When
        Product dummyProduct = Product.createDummyProduct();
        assertNotNull(dummyProduct);
        assertEquals("razor", dummyProduct.name);
        assertEquals("gillette razor", dummyProduct.description);
        assertEquals(BigDecimal.valueOf(49.00), dummyProduct.price);
        assertNotNull(dummyProduct.createdAt);
        dummyProduct.printProductInfo();
    }
}
