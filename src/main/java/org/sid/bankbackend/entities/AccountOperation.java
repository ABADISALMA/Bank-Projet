package org.sid.bankbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.bankbackend.enums.OperationType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Date operationDate;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)//on utilise string pour afficher les enums en string et pas un nbr
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;
}
