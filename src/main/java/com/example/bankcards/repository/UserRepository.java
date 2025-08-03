package com.example.bankcards.repository;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    void deleteUserByUsername(String username);

    List<User> findAllByRole(Role role);

    void deleteUsersByRole(Role role);
}
