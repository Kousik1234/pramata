package com.pramata.ServiceIMPL;

import com.pramata.DTO.SignUpDetailsDto;
import com.pramata.DTO.UserDto;
import com.pramata.Entity.User;
import com.pramata.Enum.Role;
import com.pramata.Exception.UserException;
import com.pramata.Repositry.UserRepo;
import com.pramata.Service.JwtService;
import com.pramata.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;

    @Override
    public SignUpDetailsDto signUpUser(UserDto userDto) throws UserException {

        BCryptPasswordEncoder bcrEncoder = new BCryptPasswordEncoder();
        String msz = "User Added Succesfully";

        User user = new User();
        SignUpDetailsDto signUpDetailsDto = new SignUpDetailsDto();


            Optional<User> dbuser = userRepo.findByEmail(userDto.getEmail());
            if (dbuser.isEmpty()) {
                String encryptpass = bcrEncoder.encode(userDto.getPassword());
                user.setName(userDto.getName());
                user.setEmail(userDto.getEmail());
                user.setPassword(encryptpass);
                user.setRole(Role.USER);
                userRepo.save(user);
                String token = jwtService.generateToken(userDto.getEmail());
                signUpDetailsDto.setUserId(token);
                signUpDetailsDto.setMessege(msz);
                return signUpDetailsDto;
            } else {
                throw new UserException("User Is Already Exist With This Email");
            }

    }

    @Override
    public SignUpDetailsDto logInUser(String email, String password) throws UserException {
        BCryptPasswordEncoder bcrEncoder = new BCryptPasswordEncoder();
        User user = new User();
        SignUpDetailsDto signUpDetailsDto = new SignUpDetailsDto();
        Optional<User> dbuser = userRepo.findByEmail(email);

        if (dbuser.isPresent()) {
            user = dbuser.get();
            if (bcrEncoder.matches(password, user.getPassword())) {

                String auth = jwtService.generateToken(email);

                signUpDetailsDto.setUserId(auth);
                signUpDetailsDto.setMessege("User LogIn SuccesFully");
                return signUpDetailsDto;
            } else {
                throw new UserException("incorrect password");
            }
        } else {
            throw new UserException("user not found");
        }
    }
}
