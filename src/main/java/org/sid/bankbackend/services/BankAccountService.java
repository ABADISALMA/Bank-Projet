package org.sid.bankbackend.services;

import org.sid.bankbackend.Exeption.CustomerNotFoundException;
import org.sid.bankbackend.dtos.CustomerDTO;
import org.sid.bankbackend.entities.BankAccount;
import org.sid.bankbackend.entities.CurrentAccount;
import org.sid.bankbackend.entities.Customer;
import org.sid.bankbackend.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCostomer (CustomerDTO customerDTO);

   CurrentAccount saveCurrentAccount (double initialBalance, double overDraft ,Long customerId) throws CustomerNotFoundException;
   SavingAccount saveSavingAccount (double initialBalance, double interesRate ,Long customerId) throws CustomerNotFoundException;
   // BankAccount saveBankAccount (double initialBalance, String type,Long customerId) throws CustomerNotFounfException;
    //List<Customer> listCustomers();
   List<CustomerDTO> listCustomers();//apres usage de dtos returne DTO
    BankAccount getBankAccount(String accountId);

    void debit(String accountId, double amount,String description);
    void credit(String accountId, double amount,String description);
    void transfer(String accountIdSource,String accountIdDestination, double amount);


    List<BankAccount> bankAccountList();

    CustomerDTO getCustomer(Long customerId);

    CustomerDTO updateCostomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}
