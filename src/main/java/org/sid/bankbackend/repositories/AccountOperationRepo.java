package org.sid.bankbackend.repositories;

import org.sid.bankbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepo extends JpaRepository<AccountOperation, Long> {
     List<AccountOperation> findByAccountId(String accountId);

     Page<AccountOperation> findByAccountId(String accountId, Pageable pageable);

}
