package it.monitoraggioinsetti.monitoraggio_insetti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.monitoraggioinsetti.monitoraggio_insetti.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
