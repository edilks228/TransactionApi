package omuraliev.txnmanager.transactionapi.services;

import omuraliev.txnmanager.transactionapi.DTO.UserDto;
import omuraliev.txnmanager.transactionapi.models.Transaction;
import omuraliev.txnmanager.transactionapi.models.TransactionType;
import omuraliev.txnmanager.transactionapi.models.User;
import omuraliev.txnmanager.transactionapi.repositories.TransactionsRepository;
import omuraliev.txnmanager.transactionapi.repositories.UserRepository;
import omuraliev.txnmanager.transactionapi.utils.TooLowAmountException;
import omuraliev.txnmanager.transactionapi.utils.TransactionException;
import omuraliev.txnmanager.transactionapi.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionsService {
    TransactionsRepository transactionsrepository;
    UserRepository userRepository;
    @Autowired
    public TransactionsService(TransactionsRepository transactionsrepository, UserRepository userRepository) {
        this.transactionsrepository = transactionsrepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(UserDto userDto){
        User user = new User(userDto.getName(),userDto.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public void deposit(Long userId, BigDecimal amount){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        BigDecimal currentAmount = user.getBalance();
        currentAmount = currentAmount.add(amount);
        user.setBalance(currentAmount);

        Transaction transaction = new Transaction(user, TransactionType.DEPOSIT, amount);
        transactionsrepository.save(transaction);
    }

    public void transfer(Long fromUserId, Long toUserId, BigDecimal amount) {
        User fromUser = userRepository.findById(fromUserId).orElse(null);
        User toUser = userRepository.findById(toUserId).orElse(null);
        if(fromUser == null){
            throw new UserNotFoundException("sender user not found");
        }
        if(toUser == null){
            throw new UserNotFoundException("recipient user not found");
        }

        BigDecimal currentAmountOfFromUser = fromUser.getBalance();
        BigDecimal currentAmountOfToUser = toUser.getBalance();

        if(currentAmountOfFromUser.subtract(amount).compareTo(BigDecimal.ZERO) == -1){
            throw  new TooLowAmountException("sender has not enough balance");
        }
        BigDecimal newAmountOfFromUser = currentAmountOfFromUser.subtract(amount);
        BigDecimal newAmountOfToUser = currentAmountOfToUser.add(amount);

        fromUser.setBalance(newAmountOfFromUser);
        toUser.setBalance(newAmountOfToUser);

        Transaction transferOutTransaction = new Transaction(fromUser,TransactionType.TRANSFER_OUT,amount);
        transactionsrepository.save(transferOutTransaction);

        Transaction transferInTransaction = new Transaction(toUser,TransactionType.TRANSFER_IN,amount);
        transactionsrepository.save(transferInTransaction);
    }

    public List<Transaction> getUserTransactions(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found with this id not found");
        }
        List<Transaction> transactions = new ArrayList<>();
        if(transactions == null || transactions.isEmpty()) {
            throw new TransactionException("No transactions found for the user with ID: " + id);
        }
        return transactions;

    }
}
