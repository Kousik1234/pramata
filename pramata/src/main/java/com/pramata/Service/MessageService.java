package com.pramata.Service;

import com.pramata.DTO.ChannelMessageDto;
import com.pramata.Entity.Message;
import com.pramata.Exception.ChannelException;
import com.pramata.Exception.JwtAuthException;
import com.pramata.Exception.UserException;

import java.util.List;

public interface MessageService {

    public Message sendMessage(String userId , ChannelMessageDto dto) throws UserException, JwtAuthException, ChannelException;

    public List<Message> getChannelMessages(String userId, Integer channelId) throws UserException,JwtAuthException,ChannelException;

}
