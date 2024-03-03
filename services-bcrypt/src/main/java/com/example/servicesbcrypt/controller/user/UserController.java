package com.example.servicesbcrypt.controller.user;

import com.example.servicesbcrypt.model.BeanUser;
import com.example.servicesbcrypt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody BeanUser user) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insertUser(user));
    }

    @PostMapping("/get")
    public ResponseEntity<?> getUser(@RequestBody BeanUser user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUser(user.getEmail()));
    }
}
