package com.example.CreditCard.Service;

import com.example.CreditCard.Bo.UserBo;
import com.example.CreditCard.Dto.UserDTO;
import com.example.CreditCard.Dto.UserDetailsDTO;
import com.example.CreditCard.Entity.User;
import com.example.CreditCard.Entity.UserDetails;
import com.example.CreditCard.Exception.UserNotFoundException;
import com.example.CreditCard.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    UserBo userBo;

//    public User signUp(User user) {
//        // Check if username or email already exists
//        if (userRepo.findByUserName(user.getUserName()).isPresent()) {
//            throw new RuntimeException("Username already exists!");
//        }
//
//        // Hash the password
//        user.setPassword(user.getPassword());
//
//        return userRepo.save(user);
//    }
//
//    public User login(String userName, String password) {
//        User user = userRepo.findByUserName(userName)
//                .orElseThrow(() -> new RuntimeException("User not found!"));
//
//        if (!password.equals(user.getPassword())) {
//            throw new RuntimeException("Invalid credentials!");
//        }
//
//        return user; // Return user object or token
//    }

    public User signUp(User user) {
        // Logic for user sign up (check if user already exists, etc.)
        return userRepo.save(user);
    }

    public User login(String userName, String password) throws UserNotFoundException {
        // Find user by username
        User user = userRepo.findByUserName(userName);

        // If user is not found or password is incorrect, throw exception
        if (user == null || !user.getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid username or password");
        }

        // Return the user object if login is successful
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }
}
