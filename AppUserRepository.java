package com.example.demo.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository  // Indicates that this interface is a Spring Data repository.
@Transactional(readOnly = true)  // All methods in this interface are read-only by default.
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    // Method to find an AppUser by their email.
    // Returns an Optional containing the found user or an empty Optional if no user is found.
    Optional<AppUser> findByEmail(String email);

    // Custom query to enable an AppUser by setting the 'enabled' field to TRUE based on the provided email.
    @Transactional // The method should be executed within a transation.
    @Modifying  // Indicates that the query is an update/delete operation.
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")

    int enableAppUser(String email); // Returns the number of entities updated.
}
