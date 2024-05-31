package com.ash.traveally.api.service;

import java.util.Set;

public interface ChatService {

    Set<Long> getAllChats();

    Set<Long> searchUser(String query);

    String addChat(Long userId);

    String deleteChat(Long userId);
}
