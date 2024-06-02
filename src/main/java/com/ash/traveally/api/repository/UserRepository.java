package com.ash.traveally.api.repository;

import com.ash.traveally.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT id FROM User WHERE email =:email")
    Optional<Long> findIdFromEmail(@Param("email") String email);

    @Query("SELECT u.id FROM User u WHERE u.name ILIKE %:query% OR u.username ILIKE %:query%")
    Set<Long> searchUser(@Param("query") String query);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByPhoneNumber(Long phoneNumber);
}
