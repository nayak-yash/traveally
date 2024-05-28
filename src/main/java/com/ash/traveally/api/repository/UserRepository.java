package com.ash.traveally.api.repository;

import com.ash.traveally.api.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT id FROM UserEntity WHERE email =:email")
    Optional<Long> findIdFromEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByPhoneNumber(Long phoneNumber);
}
