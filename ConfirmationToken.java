package com.example.demo.registration.token;

import com.example.demo.appuser.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


// Lombok annotations to generate getter, setter, and no-args constructor methods.
@Getter
@Setter
@NoArgsConstructor
@Entity // Marks this class as a JPA entity.
public class ConfirmationToken {

    // Defines a sequence generator for generating primary key values.
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )

    private Long id; // Unique identifier for the ConfirmationToken entity.

    @Column(nullable = false) // Token cannot be null.
    private String token; // The confirmation token string.

    @Column(nullable = false) // CreatedAt cannot be null.
    private LocalDateTime createdAt; // The timestamp when the token was created.

    @Column(nullable = false) // ExpiredAt cannot be null.
    private LocalDateTime expiredAt; // The timestamp when the token expires.

    private LocalDateTime confirmedAt;

    @ManyToOne // Many tokens can be associated with one AppUser.
    @JoinColumn(
            nullable = false,
            name = "app_user_id" // The name of the foreign key comlumn in the database.
    )
    private AppUser appUser; // The associated AppUSer entity.

    // Constructor to initialize a ConfirmatonToken with the given details.
    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.confirmedAt = confirmedAt;
        this.appUser = appUser;
    }

}
