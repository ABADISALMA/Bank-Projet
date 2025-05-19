package org.sid.bankbackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.bankbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)// LE TYPE D'HERITAGE
@DiscriminatorColumn (name = "TYPE",length = 4,discriminatorType = DiscriminatorType.STRING)//la colone type dans la table singletable
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)// si en fais table per classe on fais abstract pour ne pas cree une table bankaccount dans la base
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BankAccount {
    @Id
    private String id ;
    private double balance;
    private Date createdAt;

@Enumerated(EnumType.STRING)//donner une val string pour status
    private AccountStatus status;
    @ManyToOne // (fetch = FetchType.EAGER) eager (tu charge tous) et lazy (Tu n’as pas besoin de charger toutes les relations à chaque fois)
    private Customer customer;

    @OneToMany (mappedBy = "bankAccount",fetch = FetchType.LAZY)//dire que dans accountoperation pn a un attribut bacnkaccount

    private List<AccountOperation> accountOperations;
}
