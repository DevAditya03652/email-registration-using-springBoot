package com.example.demo.registration.token;

import jakarta.persistence.SequenceGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository // Indicates that this interface is a Spring Data repository.
@Transactional(readOnly = true) // All methods in this interface are read-only by default.
public interface ConfirmationTokenRepository extends
        JpaRepository<ConfirmationToken, Long> {

    // Method to find a ConfirmationToken by its token string.
    // Returns an Optional containing the found token or an empty Optional if no token is found.
    Optional<ConfirmationToken> findByToken(String token);

    // Custom query to update the 'confirmedAt' timestamp of a ConfirmationToken based on its token.
    @Transactional // The method should be executed within a transaction.
    @Modifying //  Indicates that the query is an update/delete operation.
    @Query ("UPDATE ConfirmationToken c" +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt); // Returns the number of entities updated.

}
