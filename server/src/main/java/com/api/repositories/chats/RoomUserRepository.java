package com.api.repositories.chats;

import com.api.entities.chat.UserChat;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomUserRepository extends R2dbcRepository<UserChat, Long> {
}
