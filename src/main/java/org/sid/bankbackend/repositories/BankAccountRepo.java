package org.sid.bankbackend.repositories;

import org.sid.bankbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//gere les 2 type de compte
public interface BankAccountRepo extends JpaRepository<BankAccount, String> {
}
