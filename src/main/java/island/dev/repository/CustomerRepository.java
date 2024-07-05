package island.dev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import island.dev.entity.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {
}