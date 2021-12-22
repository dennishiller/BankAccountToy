package de.dennishiller.cofa_challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class TransferMoneyDTO {
    @NonNull
    private String ibanWithdraw;
    @NonNull
    private String ibanDeposit;
    @NonNull
    private double amount;
}
