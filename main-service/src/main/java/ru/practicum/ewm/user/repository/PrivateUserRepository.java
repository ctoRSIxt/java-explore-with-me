package ru.practicum.ewm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.user.model.User;

public interface PrivateUserRepository extends JpaRepository<User, Long> {
}
