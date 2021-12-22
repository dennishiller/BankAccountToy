package de.dennishiller.cofa_challenge.services;

import de.dennishiller.cofa_challenge.model.TransactionHistoryEntry;
import de.dennishiller.cofa_challenge.repository.TransactionHistoryRepository;
import de.dennishiller.cofa_challenge.interfaces.ITransactionHistoryService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CommonsLog
public class TransactionHistoryService implements ITransactionHistoryService {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    public List<TransactionHistoryEntry> getTransactionHistory(String iban) {
        return transactionHistoryRepository.getAllByIban(iban);
    }

    public boolean addTransaction(TransactionHistoryEntry transactionHistoryEntry) {
        transactionHistoryRepository.save(transactionHistoryEntry);
        log.debug("Transaction added: " + transactionHistoryEntry);
        return true;
    }
}
