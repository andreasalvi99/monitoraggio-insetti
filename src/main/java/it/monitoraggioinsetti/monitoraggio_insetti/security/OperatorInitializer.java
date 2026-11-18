package it.monitoraggioinsetti.monitoraggio_insetti.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("create-operator")
public class OperatorInitializer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
