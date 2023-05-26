package com.pramata.DTO;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private Integer id;
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    private String password;


}

