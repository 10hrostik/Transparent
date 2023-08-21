package com.api.services.users;

import com.api.entities.user.User;
import com.api.repositories.CountryRepository;
import com.api.repositories.UserRepository;
import com.api.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@Service
public abstract class GeneralUserService {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CountryRepository countryRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public Mono<UserDetails> findByUsername(String username) {
        Mono<User> user;
        try {
            Long number = Long.parseLong(username);
            user = userRepository.findByPhone(number);

            return user.cast(UserDetails.class);
        } catch (NumberFormatException exception){
            if (Validator.isEmail(username)) {
                user = userRepository.findByEmail(username);
            } else {
                user =  userRepository.findByUsername(username);
            }

            return user.cast(UserDetails.class);
        }
    }

    public Mono<Void> updateLoginDate(User user) {
        user.setLoginDate(LocalDate.now());
        return userRepository.save(user).then();
    }
}
