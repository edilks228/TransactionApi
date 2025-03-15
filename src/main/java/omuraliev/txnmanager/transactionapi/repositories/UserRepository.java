package omuraliev.txnmanager.transactionapi.repositories;

import omuraliev.txnmanager.transactionapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
