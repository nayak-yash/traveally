package com.ash.traveally.api.controller;

import com.ash.traveally.api.dto.*;
import com.ash.traveally.api.models.Role;
import com.ash.traveally.api.models.User;
import com.ash.traveally.api.repository.RoleRepository;
import com.ash.traveally.api.repository.UserRepository;
import com.ash.traveally.api.security.JwtGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.badRequest().body(new MessageDto("Email already registered!!"));
        }
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageDto("Username already registered!!"));
        }
        if (userRepository.existsByPhoneNumber(registerDto.getPhoneNumber())) {
            return ResponseEntity.badRequest().body(new MessageDto("Phone number already registered!!"));
        }
        User user = mapToEntity(registerDto);
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
        AuthResponseDto responseDto = new AuthResponseDto(token);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("user/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        User user = userRepository.save(mapToEntity(userDto));
        return ResponseEntity.ok(mapToDto(user));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(mapToDto(userRepository.findById(userId).get()));
    }

    @GetMapping("user")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(mapToDto(userRepository.findByEmail(getUserEmail()).get()));
    }


    private User mapToEntity(UserDto userDto) {
        Long id = userDto.getId();
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        User user = userRepository.findById(id).get();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCity(userDto.getCity());
        user.setCountry(userDto.getCountry());
        user.setBio(userDto.getBio());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhotoUrl(userDto.getPhotoUrl());
        return userRepository.save(user);
    }

    private User mapToEntity(RegisterDto registerDto) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setCity(registerDto.getCity());
        user.setCountry(registerDto.getCountry());
        user.setBio(registerDto.getBio());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setPhotoUrl(registerDto.getPhotoUrl());
        Optional<Role> roles = roleRepository.findByName("USER");
        roles.ifPresent(role -> user.setRoles(Collections.singletonList(role)));
        return user;
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setCity(user.getCity());
        userDto.setCountry(user.getCountry());
        userDto.setBio(user.getBio());
        userDto.setPhotoUrl(user.getPhotoUrl());
        return userDto;
    }

    private String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }
}
