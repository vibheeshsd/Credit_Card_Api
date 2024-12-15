package com.example.CreditCard.Controller;

import com.example.CreditCard.Dto.LoginRequestDTO;
import com.example.CreditCard.Dto.UserDTO;
import com.example.CreditCard.Dto.UserDetailsDTO;
import com.example.CreditCard.Entity.User;
import com.example.CreditCard.ErrorResponse.ErrorResponse;
import com.example.CreditCard.Exception.ApplicationManagementException;
import com.example.CreditCard.Exception.UserManagementException;
import com.example.CreditCard.Exception.UserNotFoundException;
import com.example.CreditCard.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public ResponseEntity<Object> getApplication() throws UserManagementException {
        List<User> user;
        try{
            user = userService.findAllUsers();
        } catch (UserManagementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ApplicationManagementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return ResponseEntity.ok(userService.signUp(user));
    }

    // Login endpoint - accepts userName and password from request body
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            User user = userService.login(loginRequest.getUserName(), loginRequest.getPassword());
            return ResponseEntity.ok(user);  // Return user on successful login
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(400).body("Invalid username or password");
        }
    }
}
