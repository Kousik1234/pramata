package com.pramata.ServiceIMPL;

import com.pramata.Entity.User;
import com.pramata.Enum.Role;
import com.pramata.Exception.UserException;
import com.pramata.Repositry.UserRepo;
import com.pramata.Service.RoleUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleUpdateServiceImpl implements RoleUpdateService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public String updateRole(Integer empId) throws UserException {
        User user  = userRepo.findById(empId).orElseThrow(()-> new UserException("user not found"));
        user.setRole(Role.ADMIN);
        userRepo.save(user);
        return "Role Updated SuccesFully";
    }
}
