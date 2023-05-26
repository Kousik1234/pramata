package com.pramata.ServiceIMPL;

import com.pramata.CustomMapper.ChannelToChannelDetailsDto;
import com.pramata.DTO.ChannelDetailsDto;
import com.pramata.DTO.ChannelDto;
import com.pramata.Entity.Channel;
import com.pramata.Entity.ChannelUserMapping;
import com.pramata.Entity.User;
import com.pramata.Exception.ChannelException;
import com.pramata.Exception.JwtAuthException;
import com.pramata.Exception.UnAuthorizeException;
import com.pramata.Exception.UserException;
import com.pramata.Repositry.ChannelRepo;
import com.pramata.Repositry.ChannelUserMappingRepo;
import com.pramata.Repositry.UserRepo;
import com.pramata.Service.ChannelService;
import com.pramata.Service.JwtService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelServiceIMPL implements ChannelService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ChannelRepo channelRepo;
    @Autowired
    private ChannelUserMappingRepo channelUserMappingRepo;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ChannelToChannelDetailsDto channelToChannelDetailsDto;


    @Override
    public String createChannel(String userId, ChannelDto dto) throws ChannelException, JwtAuthException, UserException, UnAuthorizeException {
        String Admin = "ADMIN";
        List<Integer> uids = dto.getEmpIds();

        if (jwtService.validateAuthToken(userId)) {
            String username = jwtService.extractUsername(userId);
            User checkuser = userRepo.findByEmail(username).get();
            String role = String.valueOf(checkuser.getRole());
            if (checkuser == null) {
                throw new UserException("User Not Exist");
            } else {
                if (Admin.equals(role)) {
                    Channel dbchannel = channelRepo.findByChannelname(dto.getChannelName());
                    if (dbchannel == null) {
                        Channel channel = new Channel();
                        channel.setChannelname(dto.getChannelName());
                        channelRepo.save(channel);

                            List<User> users = entityManager
                                    .createNativeQuery("SELECT * FROM user WHERE id IN (:uids)", User.class)
                                    .setParameter("uids", uids)
                                    .getResultList();
                            users.add(checkuser);
                            List<ChannelUserMapping> channelUserMappings = new ArrayList<>();
                            users.forEach(user -> {
                                ChannelUserMapping channelUserMapping = new ChannelUserMapping();
                                channelUserMapping.setChannel(channel);
                                channelUserMapping.setUser(user);
                                channelUserMappings.add(channelUserMapping);
                            });
                           channelUserMappingRepo.saveAll(channelUserMappings);
                            return  "Team created Successfully!";

                    } else {

                            List<User> users = entityManager
                                    .createNativeQuery("SELECT * FROM user WHERE id IN (:uids)", User.class)
                                    .setParameter("uids", uids)
                                    .getResultList();
                            users.add(checkuser);
                            List<ChannelUserMapping> channelUserMappings = new ArrayList<>();
                            users.forEach(user -> {
                                ChannelUserMapping channelUserMapping = new ChannelUserMapping();
                                channelUserMapping.setChannel(dbchannel);
                                channelUserMapping.setUser(user);
                                channelUserMappings.add(channelUserMapping);
                            });
                            channelUserMappingRepo.saveAll(channelUserMappings);
                            return  "Team created Successfully!";
                    }
                } else {
                    throw new UnAuthorizeException("You Are Not Elgiable For Create Team");
                }
            }
        } else {
            throw new JwtAuthException("userId NotValid");
        }

    }

    @Override
    public List<ChannelDetailsDto> getAllChannel(String userId) throws ChannelException, UserException, JwtAuthException, UnAuthorizeException {
        String Admin = "ADMIN";
        if (jwtService.validateAuthToken(userId)) {
            String username = jwtService.extractUsername(userId);
            User user = userRepo.findByEmail(username).get();
            String role = String.valueOf(user.getRole());
            if (user == null) {
                throw new UserException("User Not Exist");
            } else {
                List<Channel> teams = channelUserMappingRepo.findByUser(user).stream().map(ChannelUserMapping::getChannel).collect(Collectors.toList());
                return teams.stream().map(channelToChannelDetailsDto::convert).collect(Collectors.toList());
            }
        } else {
            throw new JwtAuthException("userId Not Valid");
        }
    }

    @Override
    public String updateChannelMembers(String userId, Integer teamId, ChannelDto dto) throws ChannelException, UserException, UnAuthorizeException, JwtAuthException {

        String Admin = "ADMIN";
        if (jwtService.validateAuthToken(userId)) {
            String username = jwtService.extractUsername(userId);
            User dbuser = userRepo.findByEmail(username).get();
            if (dbuser == null) {
                throw new UserException("User Not Exist");
            } else {
                if (Admin.equals(dbuser.getRole().name())) {
                    Channel channel = channelRepo.findById(teamId).orElseThrow(()-> new ChannelException("No Channel present with Id: " + dto.getChannelId()));
                    List<Integer> uids = dto.getEmpIds();
                    List<User> users = entityManager
                            .createNativeQuery("SELECT * FROM user WHERE id IN (:uids)", User.class)
                            .setParameter("uids", uids)
                            .getResultList();
                    if (users.isEmpty()) {
                        throw new UserException("user not found");
                    } else {
                        for (User user : users) {
                            List<ChannelUserMapping> existingMappings = entityManager.createQuery(
                                            "FROM ChannelUserMapping WHERE channel = :channel AND user = :user", ChannelUserMapping.class)
                                    .setParameter("channel", channel)
                                    .setParameter("user", user)
                                    .getResultList();
                            if (!existingMappings.isEmpty()) {
                                continue;
                            }
                            ChannelUserMapping teamUserMapping = new ChannelUserMapping();
                            teamUserMapping.setChannel(channel);
                            teamUserMapping.setUser(user);
                            channelUserMappingRepo.save(teamUserMapping);
                        }
                        return  "Channel Updated Successfully!";
                    }
                } else {
                    throw new UnAuthorizeException("user Have No Acces");
                }
            }
        } else {
            throw new JwtAuthException("userId Not Valid");
        }


    }
}
