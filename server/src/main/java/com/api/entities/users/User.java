package com.api.entities.users;

import com.api.entities.BaseEntity;
import com.api.entities.attachments.UserProfileImage;
import com.api.entities.residence.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements UserDetails {
    @Column(value = "username")
    private String username;

    @Column(value = "password")
    private String password;

    @Column(value = "email")
    private String email;

    @Column(value = "name")
    private String name;

    @Column(value = "surname")
    private String surname;

    @Column(value = "phone")
    private Long phone;

    @Column(value = "register_date")
    private LocalDate registerDate;

    @Column(value = "login_date")
    private LocalDate loginDate;

    @Column(value = "account_non_expired")
    private Boolean accountNonExpired;

    @Column(value = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(value = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column(value = "enabled")
    private Boolean enabled;

    @Column(value = "role")
    private Role role;

    @Column(value = "country_id")
    private Integer countryId;

    @Column(value = "description")
    private String description;

    @Transient
    private Country country;

    @Transient
    private Set<UserProfileImage> attachments;

    public User () {
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = true;
        role = Role.ROLE_USER;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

}
