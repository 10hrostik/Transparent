package com.api.repositories.chats;

import com.api.entities.chat.Room;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository <T extends Room> extends R2dbcRepository<T, Long> {
}
