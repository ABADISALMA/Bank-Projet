package org.sid.bankbackend.dtos;

import jakarta.persistence.*;
import lombok.Data;

import org.sid.bankbackend.enums.AccountStatus;

import java.util.Date;


@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    @Id
    private String id ;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;



}
