package com.example.demo.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service // Marks this class as a Spring service component.
@AllArgsConstructor // Lombok annotation to generate a constructor with all class fields as parameters.
public class ConfirmationTokenService {

    // Dependency on the ConfirmationTokenRepository.
    private final ConfirmationTokenRepository confirmationTokenRepository;

    // Saves the given confirmation token to the repository.
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    // Retrieves a confirmation token by its token string.
    // Returns an Optional containing the found token or an empty Optional if no token is found.
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    // Updates the 'confirmedAt' timestamp of a confirmation token to the current time.
    // Return the number of entities updated.
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now()
        );
    }
}
