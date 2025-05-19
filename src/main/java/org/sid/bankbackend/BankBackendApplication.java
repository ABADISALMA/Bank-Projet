package org.sid.bankbackend;

import org.sid.bankbackend.Exeption.BankNotFoundException;
import org.sid.bankbackend.Exeption.CustomerNotFoundException;
import org.sid.bankbackend.dtos.CustomerDTO;
import org.sid.bankbackend.entities.*;
import org.sid.bankbackend.enums.AccountStatus;
import org.sid.bankbackend.enums.OperationType;
import org.sid.bankbackend.repositories.AccountOperationRepo;
import org.sid.bankbackend.repositories.BankAccountRepo;
import org.sid.bankbackend.repositories.CustomerRepo;
import org.sid.bankbackend.services.BankAccountService;
import org.sid.bankbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankBackendApplication.class, args);
    }




    //@Bean
    CommandLineRunner commandLineRunner(BankService bankService) {
        return args -> {
            bankService.consulter();
        };


    }


    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
             Stream.of("Hassan","Imane","Mohamed").forEach(name -> {
                 CustomerDTO customer = new CustomerDTO();
                 customer.setName(name);
                 customer.setEmail(name+"@gmail.com");
                 bankAccountService.saveCostomer(customer);
             });

             bankAccountService.listCustomers().forEach(customer -> {
                 try {
                     bankAccountService.saveCurrentAccount(Math.random()*120000,9000,customer.getId());
                     bankAccountService.saveSavingAccount(Math.random()*120000,5.5,customer.getId());
                     List<BankAccount> bankAccountList = bankAccountService.bankAccountList();

                     for (BankAccount bankAccount : bankAccountList) {
                         for (int i = 0; i < 10; i++) {
                             double creditAmount = 10000 + Math.random() * 120000;
                             bankAccountService.credit(bankAccount.getId(), creditAmount, "CREDIT");

                             double debitAmount = 10000 + Math.random() * 120000;

                             // Vérifie le solde avant débit
                             if (bankAccount.getBalance() >= debitAmount) {
                                 bankAccountService.debit(bankAccount.getId(), debitAmount, "DEBIT");
                             } else {
                                 System.out.println("Solde insuffisant pour le débit de " + debitAmount + " sur le compte " + bankAccount.getId());
                             }
                         }
                     }


                 } catch (CustomerNotFoundException e) {
                     e.printStackTrace();
                 }catch (BankNotFoundException e) {
                     e.printStackTrace();
                 }

             });
        };


    }

    //@Bean
    CommandLineRunner start(CustomerRepo customerRepo , BankAccountRepo bankAccountRepo, AccountOperationRepo accountOperationRepo) {
        return args -> {

            Stream.of("Hassan","Yassin","Aicha").forEach(name -> {
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepo.save(customer);
            });

            customerRepo.findAll().forEach(customer -> {//pour chaque client un compte
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setBalance(Math.random()*10000);
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepo.save(currentAccount);//save current account pour chaque client

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setBalance(Math.random()*10000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
                bankAccountRepo.save(savingAccount);//save savingaccount pour chaque client
            });

            bankAccountRepo.findAll().forEach(bankAccount -> {
                for (int i=0;i<10;i++) {
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*10000);
                    accountOperation.setDescription("test");
                    accountOperation.setBankAccount(bankAccount);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperationRepo.save(accountOperation);
                }


            });


        };
    }

}
