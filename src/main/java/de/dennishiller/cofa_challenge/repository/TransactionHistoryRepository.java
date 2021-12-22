package de.dennishiller.cofa_challenge.repository;

import de.dennishiller.cofa_challenge.model.TransactionHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntry, Long> {

    List<TransactionHistoryEntry> getAllByIban(String iban);
}
