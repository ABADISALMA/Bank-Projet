package org.sid.bankbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.bankbackend.entities.BankAccount;
import org.sid.bankbackend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {

    private  Long id;
    private Date operationDate;
    private double amount;
    private String description;

    private OperationType type;

    private BankAccount bankAccount;
}
