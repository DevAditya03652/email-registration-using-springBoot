package com.example.demo.appuser;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Lombok annotations to generate getter, setter, equals, hashcode, and no-args constructor methods.

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {


    //Defines a sequence generator for generating primary key values.
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;  // Unique identifier for the AppUser entity.
    private String firstName;  // First name of the user.
    private String lastName;  // Last name of the user.
    private String email;  // Email of the user, used as username.
    private String password;  // Password of the user.
    @Enumerated(EnumType.STRING)  // Enum to represent the role of the user (e.g., USER, ADMIN)
    private AppUserRole appUserRole;
    private Boolean locked = false; // Indicate whether the user's account is locked.
    private Boolean enabled = false; // Indicates whether the user's account is enabled.

    public AppUser(String firstName,   // Constructor to initialize an AppUser with the given details.
                   String lastName,
                   String email,
                   String password,
                   AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    // Returns the authorities granted to the user.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    // Returns the password used to authenticate the user.
    @Override
    public String getPassword() {
        return password;
    }

    // Returns the username used to authenticate the user.
    @Override
    public String getUsername() {
        return email;
    }

    // Returns the first name of the user.
    public String getFirstName() {
        return firstName;
    }

    // Returns the last name of the user.
    public String getLastName() {
        return lastName;
    }

    // Indicates whether the user's account has expired.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indicates whether the user is locked or unlocked.
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    // Indicates whether the user's credentials (password) has expired.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indicates whether the user is enabled or disabled.
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
