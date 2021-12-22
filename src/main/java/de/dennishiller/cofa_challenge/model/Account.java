package de.dennishiller.cofa_challenge.model;

import de.dennishiller.cofa_challenge.enums.AccountType;
import lombok.*;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String iban;

    @NonNull
    private double balance;

    @NonNull
    private String checkingAccount = "";

    @NonNull
    @Enumerated(EnumType.STRING)
    private AccountType type;
}

