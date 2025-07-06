package omuraliev.txnmanager.transactionapi.Interfaces;

import org.springframework.stereotype.Component;

@Component
public class Dog implements Animal {

    @Override
    public void eat() {
        System.out.println("Dog eats");
    }

    @Override
    public void sleep() {
        System.out.println("Dog sleeps");
    }
}
