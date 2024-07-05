package island.dev.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;

@Entity
@Table(name = "customers")
public class Customer extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    public Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 50)
    public String firstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    public String lastName;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    public String email;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant createdAt;

    // Method to create and return a dummy Customer object
    public static Customer createDummyCustomer() {
        Customer customer = new Customer();
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "john.doe@example.com";
        customer.createdAt = Instant.now();
        return customer;
    }

    // Method to print the dummy customer for verification
    public void printCustomerInfo() {
        System.out.println("ID: " + id);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Created At: " + createdAt);
    }
}