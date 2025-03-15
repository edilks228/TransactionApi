package omuraliev.txnmanager.transactionapi.repositories;

import omuraliev.txnmanager.transactionapi.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
}
