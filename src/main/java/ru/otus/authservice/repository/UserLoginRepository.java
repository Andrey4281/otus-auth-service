package ru.otus.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import ru.otus.authservice.domain.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, String> {
    Boolean existsByLogin(String login);
    @Modifying
    void deleteByLogin(String login);
}
