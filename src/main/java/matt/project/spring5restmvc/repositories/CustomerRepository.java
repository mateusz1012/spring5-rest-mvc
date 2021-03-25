package matt.project.spring5restmvc.repositories;

import matt.project.spring5restmvc.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
