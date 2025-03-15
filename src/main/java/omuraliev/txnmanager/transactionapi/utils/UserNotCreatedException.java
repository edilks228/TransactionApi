package omuraliev.txnmanager.transactionapi.utils;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message) {
        super(message);
    }
}
