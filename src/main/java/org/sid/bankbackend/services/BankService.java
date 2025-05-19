package org.sid.bankbackend.services;

import org.sid.bankbackend.entities.BankAccount;
import org.sid.bankbackend.entities.CurrentAccount;
import org.sid.bankbackend.entities.SavingAccount;
import org.sid.bankbackend.repositories.BankAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepo bankAccountRepo;
    public void consulter() {
        BankAccount bankAccount1 = bankAccountRepo.findById("136d3e74-5638-422f-814e-4fa4b493b9de").orElse(null);

        if (bankAccount1 != null) {

            System.out.println("*****************");
            System.out.println(bankAccount1.getId());
            System.out.println(bankAccount1.getBalance());
            System.out.println(bankAccount1.getCreatedAt());
            System.out.println(bankAccount1.getStatus());
            System.out.println(bankAccount1.getCustomer().getName());

            System.out.println(bankAccount1.getClass().getSimpleName());//affiche soit compte courrent soit saving
            if (bankAccount1 instanceof CurrentAccount) {
                System.out.println("Over Draft => " + ((CurrentAccount) bankAccount1).getOverDraft());

            } else if (bankAccount1 instanceof SavingAccount) {
                System.out.println("Rate => " + ((SavingAccount) bankAccount1).getInterestRate());
            }

            bankAccount1.getAccountOperations().forEach(accountOperation -> {

                System.out.println("*********************");
                System.out.println(accountOperation.getOperationDate());
                System.out.println(accountOperation.getAmount());
                System.out.println(accountOperation.getType() + "\t" + accountOperation.getOperationDate() + "\t" + accountOperation.getAmount());


            });
        }

    }
}
