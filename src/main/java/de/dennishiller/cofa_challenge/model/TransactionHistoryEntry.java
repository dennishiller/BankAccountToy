package de.dennishiller.cofa_challenge.model;

import de.dennishiller.cofa_challenge.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionHistoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String iban;

    @NonNull
    private LocalDateTime time = LocalDateTime.now();

    @NonNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NonNull
    private double amount;
}
