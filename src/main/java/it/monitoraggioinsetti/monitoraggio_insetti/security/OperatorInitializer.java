package it.monitoraggioinsetti.monitoraggio_insetti.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.monitoraggioinsetti.monitoraggio_insetti.repository.UserRepository;
import it.monitoraggioinsetti.monitoraggio_insetti.service.UserService;

@Component
@Profile("create-operator")
public class OperatorInitializer implements CommandLineRunner {
    private final UserService userService;
    private final UserRepository userRepository;

    public OperatorInitializer(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String name = System.getenv("OPERATOR_NAME");
        String email = System.getenv("OPERATOR_EMAIL");
        String password = System.getenv("OPERATOR_PASSWORD");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nome operatore obbligatorio");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email operatore obbligatoria");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password operatore obbligatoria");

        if (this.userRepository.findByEmail(email).isEmpty()) {
            this.userService.createOperator(name, email, password);
        }
    }
}
