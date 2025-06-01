package org.sid.bankbackend.web;

import org.sid.bankbackend.Exeption.BankNotFoundException;
import org.sid.bankbackend.dtos.AccountHistoryDTO;
import org.sid.bankbackend.dtos.AccountOperationDTO;
import org.sid.bankbackend.dtos.BankAccountDTO;
import org.sid.bankbackend.dtos.CustomerDTO;
import org.sid.bankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{acountId}")
 public BankAccountService getBankAccount(@PathVariable String accountId) throws BankNotFoundException {
        BankAccountDTO bankAccountDTO = bankAccountService.getBankAccount(accountId);
        return (BankAccountService) bankAccountService.getBankAccount(accountId);
 }

 @GetMapping("/accounts")
 public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
 }

 @GetMapping("/accounts/{accounId}/pageOperations")
 public AccountHistoryDTO getAccountHistory(@PathVariable String accountId, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5") int size) {
         return bankAccountService.getAccountHistory(accountId,page,size);
 }
}
