package omuraliev.txnmanager.transactionapi.utils;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
