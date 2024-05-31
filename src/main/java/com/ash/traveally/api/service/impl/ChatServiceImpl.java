package com.ash.traveally.api.service.impl;

import com.ash.traveally.api.models.User;
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
        User sender = userRepository.findByEmail(getUserEmail()).get();
        User receiver = userRepository.findById(userId).get();
        Set<Long> senderChatIds = sender.getChatIds();
        senderChatIds.add(userId);
        sender.setChatIds(senderChatIds);
        Set<Long> receiverChatIds = receiver.getChatIds();
        receiverChatIds.add(userId);
        receiver.setChatIds(receiverChatIds);
        userRepository.save(sender);
        userRepository.save(receiver);
        return "Added Successfully";
    }

    @Override
    public String deleteChat(Long userId) {
        User sender = userRepository.findByEmail(getUserEmail()).get();
        User receiver = userRepository.findById(userId).get();
        Set<Long> senderChatIds = sender.getChatIds();
        senderChatIds.remove(userId);
        sender.setChatIds(senderChatIds);
        Set<Long> receiverChatIds = receiver.getChatIds();
        receiverChatIds.remove(userId);
        receiver.setChatIds(receiverChatIds);
        userRepository.save(sender);
        userRepository.save(receiver);
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
