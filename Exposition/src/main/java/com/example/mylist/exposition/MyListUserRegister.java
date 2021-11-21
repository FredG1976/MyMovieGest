package com.example.mylist.exposition;

import com.example.mylist.MyListException.UserMlException;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.user.UserMlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "Public", tags = "PUBLIC - REGISTER  - MyList")
@RestController
@RequestMapping("register")
public class MyListUserRegister {
    @Autowired
    UserMlService userMlService;

    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @ApiOperation(value ="Register a new user.")
    @PostMapping
    public UserMl registerNewUser(@RequestBody UserMl userMl) throws UserMlException {
//        System.out.println(userMl.getPassword());
        String encryptedPassword = passwordEncoder.encode(userMl.getPassword());
//        System.out.println(encryptedPassword);
        userMl.setPassword(encryptedPassword);
        UserMl newUserMl = userMlService.addUser(userMl);
        return newUserMl;
    }
}