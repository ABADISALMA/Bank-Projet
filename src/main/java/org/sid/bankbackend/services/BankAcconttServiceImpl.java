package org.sid.bankbackend.services;


import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.sid.bankbackend.Exeption.BankNotFoundException;
import org.sid.bankbackend.Exeption.CustomerNotFoundException;
import org.sid.bankbackend.dtos.*;
import org.sid.bankbackend.entities.*;
import org.sid.bankbackend.enums.OperationType;
import org.sid.bankbackend.mappers.BankAccountMapperImpl;
import org.sid.bankbackend.repositories.AccountOperationRepo;
import org.sid.bankbackend.repositories.BankAccountRepo;
import org.sid.bankbackend.repositories.CustomerRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j// au lieu de cree un objet Log via lombook
public class BankAcconttServiceImpl implements BankAccountService {


    private CustomerRepo customerRepo;
    private BankAccountRepo bankAccountRepo;
    private AccountOperationRepo accountOperationRepo;
    private BankAccountMapperImpl dtoMapper;
    //Logger log= LoggerFactory.getLogger(this.getClass().getName()); //framework pour logger dans java log4j a traver le nom de la classe

    @Override
    public CustomerDTO saveCostomer(CustomerDTO customerDTO) {

        log.info("Saving customer");
        Customer customer= dtoMapper.fromCustomerDTO(customerDTO);
        return dtoMapper.fromCustomer(customerRepo.save(customer));
    }

    @Override
    public CurrentBankAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        BankAccount bankAccount;


        if (customer == null) {
            throw new CustomerNotFoundException("Customer not find");
        }

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepo.save(currentAccount);

        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingAccount(double initialBalance, double interesRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        BankAccount bankAccount;


        if (customer == null) {
            throw new CustomerNotFoundException("Customer not find");
        }

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interesRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepo.save(savingAccount);

        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers= customerRepo.findAll();

        /*List<CustomerDTO> dtos = new ArrayList<>();//prog interactif
        for (Customer customer : customers) {
            CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
            dtos.add(customerDTO);
        }*/

        List<CustomerDTO> customerDTOS= customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());//prog fonctionnel
        return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) {
         BankAccount bankAccount= bankAccountRepo.findById(accountId)
                 .orElseThrow(() -> new BankNotFoundException("Bank account not found"));
         if (bankAccount instanceof SavingAccount) {
             SavingAccount savingAccount= (SavingAccount) bankAccount;
             return dtoMapper.fromSavingBankAccount(savingAccount);
         }
         else  {
             CurrentAccount currentAccount= (CurrentAccount) bankAccount;
             return dtoMapper.fromCurrentBankAccount(currentAccount);
         }
    }

    @Override
    public void debit(String accountId, double amount, String description) {
        BankAccount bankAccount= bankAccountRepo.findById(accountId)
                .orElseThrow(() -> new BankNotFoundException("Bank account not found"));
        //les regles meties gere les exeptions les tests
        //if (bankAccount.getBalance() < amount) {
           // throw new RuntimeException("Balance not sufficient");
        //}

        AccountOperation operation = new AccountOperation();
        operation.setType(OperationType.DEBIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setBankAccount(bankAccount);

        accountOperationRepo.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepo.save(bankAccount);

        log.info("Account debited: " + amount);
    }

    @Override
    public void credit(String accountId, double amount, String description) {
        BankAccount bankAccount= bankAccountRepo.findById(accountId)
                .orElseThrow(() -> new BankNotFoundException("Bank account not found"));

        AccountOperation operation = new AccountOperation();
        operation.setType(OperationType.CREDIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setBankAccount(bankAccount);

        accountOperationRepo.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepo.save(bankAccount);

        log.info("Account credited: " + amount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) {
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);//opperation transactionnel commit ou rollback
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);//op transact
        log.info("Transfer completed from " + accountIdSource + " to " + accountIdDestination);
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccountList = bankAccountRepo.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccountList.stream().map(bankAccount -> {
            if(bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount= (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            }
            else {
                CurrentAccount currentAccount= (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());

        return bankAccountDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId){
       Customer customer=  customerRepo.findById(customerId)
               .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
         return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCostomer(CustomerDTO customerDTO) {

        log.info("Saving customer");
        Customer customer= dtoMapper.fromCustomerDTO(customerDTO);
        return dtoMapper.fromCustomer(customerRepo.save(customer));
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepo.deleteById(customerId);
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
    List<AccountOperation> accountOperations = accountOperationRepo.findByAccountId(accountId);
   return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());



}

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) {
        BankAccount bankAccount = bankAccountRepo.findById(accountId).orElse(null);
        if (bankAccount == null) throw new BankNotFoundException("Bank account not found");{}
     Page<AccountOperation> accountOperations = accountOperationRepo.findByAccountId(accountId, PageRequest.of(page, size));
     AccountHistoryDTO accountHistoryDTO= new AccountHistoryDTO();
     accountHistoryDTO.setAccountOperationDTOS(accountOperations.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList()));
accountHistoryDTO.setAccountId(accountId);
accountHistoryDTO.setBalance(bankAccount.getBalance());
accountHistoryDTO.setCurrentPage(page);
accountHistoryDTO.setPagesSize(size);
accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return accountHistoryDTO;
    }
}
