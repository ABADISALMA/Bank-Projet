package org.sid.bankbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
     private String accountId;
     private double balance;
     private int currentPage;
     private int totalPages;
     private int pagesSize;
    private List<AccountOperationDTO> accountOperationDTOS;
}

