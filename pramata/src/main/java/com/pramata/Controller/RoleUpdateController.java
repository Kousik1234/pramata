package com.pramata.Controller;

import com.pramata.DTO.MessageDto;
import com.pramata.Exception.UserException;
import com.pramata.Service.RoleUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class RoleUpdateController {
    @Autowired
    private RoleUpdateService roleUpdateService;
    @PutMapping ("/update/{empId}/role")
    public ResponseEntity<?> updateRoleHandeller (@PathVariable Integer empId) {
        MessageDto messageDto = new MessageDto();
        try {
            String msz = roleUpdateService.updateRole(empId);
            return new ResponseEntity<>(msz,HttpStatus.OK);
        } catch (UserException e) {
            messageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            messageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
