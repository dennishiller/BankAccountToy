package de.dennishiller.cofa_challenge.interfaces;

import de.dennishiller.cofa_challenge.model.TransactionHistoryEntry;

import java.util.List;

public interface ITransactionHistoryService {

    List<TransactionHistoryEntry> getTransactionHistory(String iban);

    boolean addTransaction(TransactionHistoryEntry transactionHistoryEntry);
}
