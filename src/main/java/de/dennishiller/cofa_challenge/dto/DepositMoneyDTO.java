package de.dennishiller.cofa_challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class DepositMoneyDTO {
    @NonNull
    private String iban;
    @NonNull
    private double amount;
}
