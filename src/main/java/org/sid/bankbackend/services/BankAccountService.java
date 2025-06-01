package org.sid.bankbackend.services;

import org.sid.bankbackend.Exeption.CustomerNotFoundException;
import org.sid.bankbackend.dtos.*;
import org.sid.bankbackend.entities.BankAccount;
import org.sid.bankbackend.entities.CurrentAccount;
import org.sid.bankbackend.entities.Customer;
import org.sid.bankbackend.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCostomer (CustomerDTO customerDTO);

    CurrentBankAccountDTO saveCurrentAccount (double initialBalance, double overDraft , Long customerId) throws CustomerNotFoundException;
   SavingBankAccountDTO saveSavingAccount (double initialBalance, double interesRate , Long customerId) throws CustomerNotFoundException;
   // BankAccount saveBankAccount (double initialBalance, String type,Long customerId) throws CustomerNotFounfException;
    //List<Customer> listCustomers();
   List<CustomerDTO> listCustomers();//apres usage de dtos returne DTO
    BankAccountDTO getBankAccount(String accountId);

    void debit(String accountId, double amount,String description);
    void credit(String accountId, double amount,String description);
    void transfer(String accountIdSource,String accountIdDestination, double amount);


    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId);

    CustomerDTO updateCostomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);



    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size);
}
