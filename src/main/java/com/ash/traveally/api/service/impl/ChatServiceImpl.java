package com.ash.traveally.api.service.impl;

import com.ash.traveally.api.repository.UserRepository;
import com.ash.traveally.api.service.ChatService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ChatServiceImpl implements ChatService {

    private UserRepository userRepository;

    public ChatServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<Long> getAllChats() {
        return userRepository.findByEmail(getUserEmail()).get().getChatIds();
    }

    @Override
    public Set<Long> searchUser(String query) {
        return userRepository.findUserIdsByNameOrUsernameStartingWith(query);
    }

    @Override
    public String addChat(Long userId) {
        userRepository.findByEmail(getUserEmail()).get().getChatIds().add(userId);
        userRepository.findById(userId).get().getChatIds().add(userRepository.findIdFromEmail(getUserEmail()).get());
        return "Added Successfully";
    }

    @Override
    public String deleteChat(Long userId) {
        userRepository.findByEmail(getUserEmail()).get().getChatIds().remove(userId);
        userRepository.findById(userId).get().getChatIds().remove(userRepository.findIdFromEmail(getUserEmail()).get());
        return "Deleted Successfully";
    }

    private String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }
}
