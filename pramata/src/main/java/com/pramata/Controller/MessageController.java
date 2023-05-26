package com.pramata.Controller;

import com.pramata.DTO.ChannelMessageDto;
import com.pramata.DTO.MessageDto;
import com.pramata.Entity.Message;
import com.pramata.Exception.ChannelException;
import com.pramata.Exception.JwtAuthException;
import com.pramata.Exception.UserException;
import com.pramata.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping("/send/message")
    public ResponseEntity<?> sendMessage (@RequestParam String userId ,@RequestBody ChannelMessageDto message) {
        MessageDto messageDto = new MessageDto();
        try {
            Message message1 = messageService.sendMessage(userId, message);
            return new ResponseEntity<>(message1,HttpStatus.OK);
        } catch (UserException | JwtAuthException | ChannelException e) {
            messageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            messageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{channelId}/messages")
    public ResponseEntity<?> getChannelMessages(@RequestParam String userId,@PathVariable Integer channelId) {
       MessageDto messageDto = new MessageDto();
       try {
           List<Message> messages = messageService.getChannelMessages(userId,channelId);
           return new ResponseEntity<>(messages,HttpStatus.OK);
       } catch (UserException | JwtAuthException |ChannelException e) {
           messageDto.setMessage(e.getMessage());
           return new ResponseEntity<>(messageDto,HttpStatus.BAD_REQUEST);
       } catch (Exception e) {
           messageDto.setMessage(e.getMessage());
           return new ResponseEntity<>(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }

}
