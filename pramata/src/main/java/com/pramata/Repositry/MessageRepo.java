package com.pramata.Repositry;

import com.pramata.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Integer> {

    List<Message> findByChannelIdOrderByCreatedAtDesc(Integer channelId);

}
