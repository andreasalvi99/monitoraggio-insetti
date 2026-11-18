package it.monitoraggioinsetti.monitoraggio_insetti.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.monitoraggioinsetti.monitoraggio_insetti.model.User;
import it.monitoraggioinsetti.monitoraggio_insetti.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createOperator(String name, String email, String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("La password è obbligatoria");
        String passwordHash = passwordEncoder.encode(password);

        User operator = new User(name, email, passwordHash, "OPERATORE");
        return this.userRepository.save(operator);
    }
}
