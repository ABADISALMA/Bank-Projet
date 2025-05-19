package org.sid.bankbackend.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity //si vous metter que entitie quand on herite de bankaccount ne va pas cree currentaccount car dans bankaccount on a mentionner singletable
@DiscriminatorValue("CA")//la val cur sera affecter a la colonne type dans singletable
public class CurrentAccount extends BankAccount {
    private double overDraft;
}
