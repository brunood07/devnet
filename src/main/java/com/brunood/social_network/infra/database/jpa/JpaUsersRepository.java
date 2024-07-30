package com.brunood.social_network.infra.database.jpa;

import com.brunood.social_network.infra.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUsersRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);
}
