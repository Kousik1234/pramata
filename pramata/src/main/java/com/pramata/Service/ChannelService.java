package com.pramata.Service;

import com.pramata.DTO.ChannelDetailsDto;
import com.pramata.DTO.ChannelDto;
import com.pramata.Exception.ChannelException;
import com.pramata.Exception.JwtAuthException;
import com.pramata.Exception.UnAuthorizeException;
import com.pramata.Exception.UserException;

import java.util.List;

public interface ChannelService {

    public String createChannel(String userId , ChannelDto dto) throws ChannelException, JwtAuthException, UserException, UnAuthorizeException;

    public List<ChannelDetailsDto> getAllChannel(String userId) throws ChannelException,UserException,JwtAuthException,UnAuthorizeException;

    public String updateChannelMembers(String userId ,Integer teamId,ChannelDto dto)throws ChannelException,UserException,UnAuthorizeException,JwtAuthException;

}
