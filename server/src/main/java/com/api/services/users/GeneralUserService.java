package com.api.services.users;

import com.api.entities.users.User;
import com.api.repositories.CountryRepository;
import com.api.repositories.UserRepository;
import com.api.utils.Validator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@Service
public abstract class GeneralUserService {
    protected UserRepository userRepository;

    protected CountryRepository countryRepository;

    protected PasswordEncoder passwordEncoder;

    public GeneralUserService(UserRepository userRepository, CountryRepository countryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<UserDetails> findByUsername(String username) {
        Mono<User> user;
        try {
            Long number = Long.parseLong(username);
            user = userRepository.findByPhone(number);

            return user.cast(UserDetails.class).defaultIfEmpty(new User());
        } catch (NumberFormatException exception){
            if (Validator.isEmail(username)) {
                user = userRepository.findByEmail(username);
            } else {
                user =  userRepository.findByUsername(username);
            }

            return user.cast(UserDetails.class).defaultIfEmpty(new User());
        }
    }

    public Mono<Void> updateLoginDate(User user) {
        user.setLoginDate(LocalDate.now());
        return userRepository.save(user).then();
    }
}
