package com.example.demo.appuser;

import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.callback.ConfirmationCallback;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service // Marks this class as a Spring service component.
@AllArgsConstructor // Lombok annotation to generate a constructor with all class fields as parameters.
public class AppUserService implements UserDetailsService {

    // Dependencies for this service.
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    // Loads a user by their email (used as username) for authenticate.
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));

    }

    // Signs up a new user by saving their details and generating a confirmation token.
    public String signUpUser(AppUser appUser) {
        // Check if a user with the given email already exists.
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        // Encode the user's password.
        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        // Save the user in the repository.
        appUserRepository.save(appUser);

        // Generate a confirmation token.
        String token = UUID.randomUUID().toString();

        // Create a ConfirmationToken object with the generated token and user details.
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        // Save the confirmation token.
        confirmationTokenService.saveConfirmationToken(
                confirmationToken
        );

        // TODO: SEND EMAIL

        return token; // Return the generated token.
    }

    // Enables a user by updating their 'enabled' status based on their email.
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
