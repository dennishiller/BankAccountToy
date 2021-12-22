package de.dennishiller.cofa_challenge.repository;

import de.dennishiller.cofa_challenge.model.Account;
import de.dennishiller.cofa_challenge.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findAccountByIban(String iban);

    List<Account> findAccountsByType(AccountType type);
}
