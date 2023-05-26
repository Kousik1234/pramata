package com.pramata.Controller;

import com.pramata.DTO.MessageDto;
import com.pramata.DTO.SignUpDetailsDto;
import com.pramata.DTO.UserDto;
import com.pramata.Exception.UserException;
import com.pramata.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup/user")
    public ResponseEntity<?> signupUserHandeller(@Valid @RequestBody UserDto userDto) {

        MessageDto message = new MessageDto();

        try {
            SignUpDetailsDto signUpDetailsDto = userService.signUpUser(userDto);
            return new ResponseEntity<>(signUpDetailsDto, HttpStatus.CREATED);
        } catch (UserException e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/login/user")
    public ResponseEntity<?> logInUserHandeller(@RequestParam("email") String email , @RequestParam("password") String password) {

        MessageDto message = new MessageDto();

        try {
                SignUpDetailsDto signUpDetailsDto = userService.logInUser(email, password);
                return new ResponseEntity<>(signUpDetailsDto,HttpStatus.OK);
        } catch (UserException e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
