package walid_soukant.prjt.ebanking_backend.dtos;

import lombok.Data;
import walid_soukant.prjt.ebanking_backend.enums.AccountStatus;

import java.util.Date;

@Data
public class SavingBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
