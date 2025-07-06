package omuraliev.txnmanager.transactionapi.Interfaces;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Cat implements Animal {
    @Override
    public void eat() {
        System.out.println("cat eat");
    }

    @Override
    public void sleep() {
        System.out.println("cat sleep");
    }
}
