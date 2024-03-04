package com.api.repositories;

import com.api.entities.users.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
  @Query("SELECT * FROM users WHERE email = :credential or username = :credential or phone = :credential")
  Mono<User> findByUsernameOrPhoneOrEmail(@Param("credential") String credential);

  @Query("SELECT * FROM users WHERE email = :credential or username = :credential or phone = :credential and password = :password")
  Mono<User> findByUsernameOrPhoneOrEmailAndPassword(@Param("credential") String credential, @Param("password") String password);

  @Query("UPDATE users SET password = :password WHERE id = :id")
  Mono<User> updatePassword(@Param("password") String password, @Param("id") Long id);
}
