package com.example.repository;

import com.example.model.User;
import com.example.security.dto.AuthenticatedUserDto;
import com.example.security.dto.RegistrationRequest;
import com.example.security.dto.RegistrationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);

    List<User> username(String username);
}
