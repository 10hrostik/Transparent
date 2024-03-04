package com.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class PasswordEncoderConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence charSequence) {
        return getSHA(charSequence.toString());
      }

      @Override
      public boolean matches(CharSequence charSequence, String s) {
        return getSHA(charSequence.toString()).equals(s);
      }
    };
  }

  public String getSHA(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] messageDigest = md.digest(input.getBytes());
      BigInteger no = new BigInteger(1, messageDigest);
      StringBuilder hashtext = new StringBuilder(no.toString(16));
      while (hashtext.length() < 32) {
        hashtext.insert(0, "0");
      }

      return hashtext.toString();
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Exception thrown"
       + " for incorrect algorithm: " + e);
      return null;
    }
  }
}
