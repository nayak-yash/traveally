package com.ash.traveally.api.controller;

import com.ash.traveally.api.dto.AuthResponseDto;
import com.ash.traveally.api.dto.LoginDto;
import com.ash.traveally.api.dto.RegisterDto;
import com.ash.traveally.api.dto.MessageDto;
import com.ash.traveally.api.models.Role;
import com.ash.traveally.api.models.UserEntity;
import com.ash.traveally.api.repository.RoleRepository;
import com.ash.traveally.api.repository.UserRepository;
import com.ash.traveally.api.security.JwtGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("register")
    public ResponseEntity<MessageDto> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageDto("User already registered!!"));
        }
        UserEntity user = mapToEntity(registerDto);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageDto("User registered successfully!!"));
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtGenerator.generateToken(auth);
        Integer userId = userRepository.findIdFromEmail(loginDto.getEmail()).orElseThrow(() ->
        new UsernameNotFoundException("User doesn't exist"));
        AuthResponseDto responseDto = new AuthResponseDto(token, userId);
        return ResponseEntity.ok(responseDto);
    }

    private UserEntity mapToEntity(RegisterDto registerDto) {
        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setProfileUrl(registerDto.getProfileUrl());
        Optional<Role> roles = roleRepository.findByName("USER");
        roles.ifPresent(role -> user.setRoles(Collections.singletonList(role)));
        return user;
    }
}
