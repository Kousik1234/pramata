package com.pramata.Repositry;

import com.pramata.Entity.ChannelUserMapping;
import com.pramata.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelUserMappingRepo extends JpaRepository<ChannelUserMapping,Integer> {

    List<ChannelUserMapping> findByUser(User user);
}
