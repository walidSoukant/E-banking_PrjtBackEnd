package walid_soukant.prjt.ebanking_backend.repositories;

import walid_soukant.prjt.ebanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
