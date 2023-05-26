package com.pramata.Service;

import com.pramata.DTO.SignUpDetailsDto;
import com.pramata.DTO.UserDto;
import com.pramata.Exception.UserException;

public interface UserService {

    public SignUpDetailsDto signUpUser(UserDto userDto) throws UserException;

    public SignUpDetailsDto logInUser(String email , String password) throws UserException;


}
