package walid_soukant.prjt.ebanking_backend.services;

import walid_soukant.prjt.ebanking_backend.dtos.*;
import walid_soukant.prjt.ebanking_backend.exceptions.BalanceNotSufficientException;
import walid_soukant.prjt.ebanking_backend.exceptions.BankAccountNotFoundException;
import walid_soukant.prjt.ebanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomer();

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BalanceNotSufficientException, BankAccountNotFoundException;
    void credit(String accountId, double amount, String description) throws BalanceNotSufficientException, BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
