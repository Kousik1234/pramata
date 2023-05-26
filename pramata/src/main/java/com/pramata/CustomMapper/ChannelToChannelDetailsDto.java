package com.pramata.CustomMapper;

import com.pramata.DTO.ChannelDetailsDto;
import com.pramata.Entity.Channel;
import org.springframework.core.convert.converter.Converter;

public class ChannelToChannelDetailsDto implements Converter<Channel, ChannelDetailsDto> {
    @Override
    public ChannelDetailsDto convert(Channel source) {
        ChannelDetailsDto channelDetailsDto = new ChannelDetailsDto();
        channelDetailsDto.setChannelId(source.getId());
        channelDetailsDto.setChannelName(source.getChannelname());
        return channelDetailsDto;
    }
}
