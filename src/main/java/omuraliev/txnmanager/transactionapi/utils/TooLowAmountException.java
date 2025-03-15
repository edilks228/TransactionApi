package omuraliev.txnmanager.transactionapi.utils;

public class TooLowAmountException extends RuntimeException {
    public TooLowAmountException(String message ) {
        super(message);
    }
}
