package com.pramata.Repositry;

import com.pramata.Entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepo extends JpaRepository<Channel, Integer> {

    public Channel findByChannelname(String ChannelName);

    List<Channel> findByChannelnameIn(List<String> teamNames);

}

