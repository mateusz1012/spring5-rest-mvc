package matt.project.spring5restmvc.repositories;

import matt.project.spring5restmvc.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
