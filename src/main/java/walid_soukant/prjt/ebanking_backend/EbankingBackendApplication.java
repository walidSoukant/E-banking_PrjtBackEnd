package walid_soukant.prjt.ebanking_backend;

import walid_soukant.prjt.ebanking_backend.dtos.BankAccountDTO;
import walid_soukant.prjt.ebanking_backend.dtos.CurrentBankAccountDTO;
import walid_soukant.prjt.ebanking_backend.dtos.CustomerDTO;
import walid_soukant.prjt.ebanking_backend.dtos.SavingBankAccountDTO;
import walid_soukant.prjt.ebanking_backend.entities.*;
import walid_soukant.prjt.ebanking_backend.enums.AccountStatus;
import walid_soukant.prjt.ebanking_backend.enums.OperationType;
import walid_soukant.prjt.ebanking_backend.exceptions.BalanceNotSufficientException;
import walid_soukant.prjt.ebanking_backend.exceptions.BankAccountNotFoundException;
import walid_soukant.prjt.ebanking_backend.exceptions.CustomerNotFoundException;
import walid_soukant.prjt.ebanking_backend.repositories.AccountOperationRepository;
import walid_soukant.prjt.ebanking_backend.repositories.BankAccountRepository;
import walid_soukant.prjt.ebanking_backend.repositories.CustomerRepository;
import walid_soukant.prjt.ebanking_backend.services.BankAccountService;
import walid_soukant.prjt.ebanking_backend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("meryem","yassin","ayoub").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomer().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO)
                        accountId=((SavingBankAccountDTO)bankAccount).getId();
                    else
                        accountId=((CurrentBankAccountDTO)bankAccount).getId();
                    bankAccountService.credit(accountId, 10+Math.random()*12,"Credit");
                    bankAccountService.debit(accountId,10+Math.random()*9,"Debit");
                }
            }
        };
    }
    //@Bean

    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("walid","salman","zakia").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreateAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreateAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }


            });



        };
    }
}


