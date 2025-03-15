package omuraliev.txnmanager.transactionapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import omuraliev.txnmanager.transactionapi.DTO.AmountDto;
import omuraliev.txnmanager.transactionapi.DTO.TransferRequest;
import omuraliev.txnmanager.transactionapi.DTO.UserDto;
import omuraliev.txnmanager.transactionapi.models.Transaction;
import omuraliev.txnmanager.transactionapi.models.User;
import omuraliev.txnmanager.transactionapi.services.TransactionsService;
import omuraliev.txnmanager.transactionapi.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User API")
public class WalletController {

    private final TransactionsService transactionsService;

    @Autowired
    public WalletController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/users")
    @Operation(summary = "add new user")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errors.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("\n");
            }
            throw new UserNotCreatedException(errors.toString());
        }
        User user = transactionsService.save(userDto);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "deposit to user")
    @PostMapping("/users/{id}/deposit")
    public ResponseEntity<HttpStatus> depositUser(@PathVariable Long id, @Valid @RequestBody AmountDto amountDto) {
        transactionsService.deposit(id, amountDto.getAmount());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users/{id}/transactions")
    @Operation(summary = "get all transactions of user")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable Long id) {
        List<Transaction> transactions = transactionsService.getUserTransactions(id);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transactions/transfer")
    @Operation(summary = "do transfer from one user to other user")
    public ResponseEntity<HttpStatus> transferUser(@Valid @RequestBody TransferRequest transferRequest) {
        transactionsService.transfer(transferRequest.getFromUserId(),transferRequest.getToUserId(),transferRequest.getAmount());
        return ResponseEntity.ok(HttpStatus.OK);
    }



    //Exception Handlers
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<TransactionErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        TransactionErrorResponse transactionErrorResponse = new TransactionErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(transactionErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotCreatedException.class)
    private ResponseEntity<TransactionErrorResponse> handleUserNotCreatedException(UserNotCreatedException e) {
        TransactionErrorResponse transactionErrorResponse = new TransactionErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(transactionErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TooLowAmountException.class)
    private ResponseEntity<TransactionErrorResponse> handleTooLowAmountException(TooLowAmountException e) {
        TransactionErrorResponse transactionErrorResponse = new TransactionErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(transactionErrorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TransactionException.class)
    private ResponseEntity<TransactionErrorResponse> handleTransactionException(TransactionException e) {
        TransactionErrorResponse transactionErrorResponse = new TransactionErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(transactionErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
