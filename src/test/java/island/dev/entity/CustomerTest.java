package island.dev.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CustomerTest {

    @Test
    public void testCreateDummyCustomer() {
        Customer dummyCustomer = Customer.createDummyCustomer();
        assertNotNull(dummyCustomer);
        assertEquals("John", dummyCustomer.firstName);
        assertEquals("Doe", dummyCustomer.lastName);
        assertEquals("john.doe@example.com", dummyCustomer.email);
        assertNotNull(dummyCustomer.createdAt);
    }
}
