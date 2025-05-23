package org.sid.bankbackend.repositories;

import org.sid.bankbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepo extends JpaRepository<AccountOperation, Long> {
}
