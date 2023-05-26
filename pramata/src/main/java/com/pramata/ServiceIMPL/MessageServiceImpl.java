package com.pramata.ServiceIMPL;

import com.pramata.DTO.ChannelMessageDto;
import com.pramata.Entity.Channel;
import com.pramata.Entity.Message;
import com.pramata.Entity.User;
import com.pramata.Exception.ChannelException;
import com.pramata.Exception.JwtAuthException;
import com.pramata.Exception.UserException;
import com.pramata.Repositry.ChannelRepo;
import com.pramata.Repositry.MessageRepo;
import com.pramata.Repositry.UserRepo;
import com.pramata.Service.JwtService;
import com.pramata.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ChannelRepo channelRepo;

    @Override
    public Message sendMessage(String userId, ChannelMessageDto dto) throws UserException, JwtAuthException, ChannelException {

        if (jwtService.validateAuthToken(userId)) {
            String username = jwtService.extractUsername(userId);
            User user = userRepo.findByEmail(username).get();
            if (user == null) {
                throw new UserException("User Not Found");
            } else {
                Channel channel = channelRepo.findById(dto.getChannelId()).orElseThrow(()-> new ChannelException("channel not found"));
                Message message = new Message();
                message.setChannel(channel);
                message.setContent(dto.getMessage());
                return messageRepo.save(message);
            }
        } else {
            throw new JwtAuthException("userId not valid");
        }

    }

    @Override
    public List<Message> getChannelMessages(String userId, Integer channelId) throws UserException, JwtAuthException, ChannelException {
        if (jwtService.validateAuthToken(userId)) {
            String username = jwtService.extractUsername(userId);
            User user = userRepo.findByEmail(username).get();
            if (user == null) {
                throw new UserException("User Not Found");
            } else {
                //Channel channel = channelRepo.findById(dto.getChannelId()).orElseThrow(()-> new ChannelException("channel not found"));
                return messageRepo.findByChannelIdOrderByCreatedAtDesc(channelId);
            }
        } else {
            throw new JwtAuthException("userId not valid");
        }

    }
}
