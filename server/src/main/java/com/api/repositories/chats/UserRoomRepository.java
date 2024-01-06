package com.api.repositories.chats;

import com.api.entities.rooms.UserChat;
import com.api.entities.rooms.UserRoom;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository<T extends UserRoom> extends R2dbcRepository<T, Long> {
}
