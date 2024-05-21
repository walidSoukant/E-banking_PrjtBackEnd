package walid_soukant.prjt.ebanking_backend.repositories;

import walid_soukant.prjt.ebanking_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String > {

}
