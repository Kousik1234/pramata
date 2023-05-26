package com.pramata.Controller;

import com.pramata.DTO.ChannelDetailsDto;
import com.pramata.DTO.ChannelDto;
import com.pramata.DTO.MessageDto;
import com.pramata.Exception.ChannelException;
import com.pramata.Exception.JwtAuthException;
import com.pramata.Exception.UnAuthorizeException;
import com.pramata.Exception.UserException;
import com.pramata.Service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ChannelController {
    @Autowired
    private ChannelService channelService;


    @PostMapping("/channel/create")
    public ResponseEntity<?> createTeamHandler(@RequestParam("userId") String userId , @RequestBody ChannelDto dto) {

        MessageDto message = new MessageDto();

        try {
            String team =channelService.createChannel(userId , dto);
            return new ResponseEntity<String> (team, HttpStatus.CREATED);
        } catch (ChannelException | JwtAuthException | UserException e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        } catch (UnAuthorizeException e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllChannels/user")
    public ResponseEntity<?> getAllTeamsHandler(String userId) {

        MessageDto message = new MessageDto();

        try {
            List<ChannelDetailsDto> teams =channelService.getAllChannel(userId);
            return new ResponseEntity<> (teams, HttpStatus.OK);
        } catch (ChannelException |UserException | JwtAuthException e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/channel/{channelId}/assignUsers")
    public ResponseEntity<?> updateTeamHandler(@RequestParam("userId") String userId, @PathVariable Integer channelId,@RequestBody ChannelDto dto) {

        MessageDto message = new MessageDto();

        try {
            String teams =channelService.updateChannelMembers(userId,channelId, dto);
            return new ResponseEntity<String> (teams, HttpStatus.OK);
        } catch (ChannelException |UserException | JwtAuthException e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        } catch (UnAuthorizeException e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
