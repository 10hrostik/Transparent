package com.api.repositories;

import com.api.entities.users.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
    @Query("SELECT * FROM users WHERE phone = :phone")
    Mono<User> findByPhone(Long phone);

    @Query("SELECT * FROM users WHERE username = :username")
    Mono<User> findByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :email")
    Mono<User> findByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email or username = :email and password = :password")
    Mono<User> findByEmailAndPassword(String email, String password);

    @Query("UPDATE users SET password = :newPassword WHERE id = :id")
    Mono<User> updatePassword(String newPassword, Long id);
}
