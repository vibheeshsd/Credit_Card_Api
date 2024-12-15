package com.example.CreditCard.Controller;


import com.example.CreditCard.Dto.UserDetailsDTO;
import com.example.CreditCard.Entity.User;
import com.example.CreditCard.Entity.UserDetails;
import com.example.CreditCard.Exception.ApplicationManagementException;
import com.example.CreditCard.Exception.UserManagementException;
import com.example.CreditCard.Exception.UserNotFoundException;
import com.example.CreditCard.Repo.UserApplicationProjectionInterface;
import com.example.CreditCard.Repo.UserDetailsProjectionInterface;
import com.example.CreditCard.Repo.UserIdProjectionInterface;
import com.example.CreditCard.Service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserDetailsController {
    static Logger log = Logger.getLogger(UserDetailsController.class);

    @Autowired
    UserDetailsService service;

//    @PostMapping("/insert")
//    public ResponseEntity<Object> addUserDetails(@RequestBody UserDetailsDTO userDetailsDTO) throws UserManagementException, UserNotFoundException {
//        List<UserDetailsDTO> newUserDetailsList;
//        System.out.println(userDetailsDTO);
//        try {
//            log.debug("Creating new user with details: " + userDetailsDTO);
//            List<UserDetailsDTO> currentUserDetailsList = service.findAllUserDetails();
//            log.info("Current details List " + currentUserDetailsList);
//            UserDetailsDTO newUserDetails = service.insert(userDetailsDTO);
//            newUserDetailsList = service.findAllUserDetails();
//            log.info("User created successfully " + newUserDetailsList);
//        } catch (UserManagementException e) {
//            log.info("User creation failed" + e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (ApplicationManagementException e) {
//            log.info("Application error during creation: " + e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(newUserDetailsList, HttpStatus.OK);
//    }

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestBody UserDetailsDTO userDetailsDTO) throws UserManagementException, UserNotFoundException {
        UserDetails userDetails;
        UserDetailsDTO responseDTO;
        try{
            userDetails = service.addUser(userDetailsDTO);
            responseDTO = UserDetailsDTO.toUserDetailsDTO(userDetails);
            System.out.println("UserId: " + userDetails.getUserId());
        } catch (UserManagementException e){
            return new ResponseEntity<>("User Creation Failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User details not found" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/byUserName/{userName}")
    public ResponseEntity<Object> getUserByName(@PathVariable String userName) throws UserManagementException, UserNotFoundException {
        UserDetailsDTO userDetailsDTO;
        try{
            log.debug("Fetching User Details by User Id");
            userDetailsDTO = service.findUserDetails(userName);
            log.info("User Fetched successfully");
        } catch (UserNotFoundException e) {
            log.info("User with user Name " + userName +" cannot be fetched " + e.getMessage());
            return new ResponseEntity<>("User with user Name " + userName +" was not found " , HttpStatus.BAD_REQUEST);
        }catch (UserManagementException e) {
            log.error("Error fetching user: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ApplicationManagementException e) {
            log.error("Application error while fetching user: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public ResponseEntity<Object> getApplication() throws UserManagementException {
        List<UserDetailsDTO> userDetails;
        try{
            log.debug("Fetching all User Data");
            userDetails = service.findAllUserDetails();
            log.info("User Details fetched successfully" + userDetails);
        } catch (UserManagementException e) {
            log.error("Error fetching users: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ApplicationManagementException e) {
            log.error("Application error while fetching users: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userDetails,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUser (@PathVariable String userId) throws UserManagementException {
        try{
            log.debug("Deleting user with Id: " + userId);
            service.deleteUser(userId);
            log.info("User deleted successfully with ID: " + userId);
        } catch (UserNotFoundException e) {
            log.error("User not found for deletion: " + e.getMessage());
            return new ResponseEntity<>("User not found with ID: " + userId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while deleting user: " + e.getMessage());
            return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User deleted successfully with ID: " + userId, HttpStatus.OK);
    }


    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody UserDetailsDTO userDetailsDTO) {
        try {
            log.debug("Updating user with ID: " + userId);
            UserDetailsDTO updatedUser = service.updateUser(userId, userDetailsDTO);
            log.info("User updated successfully: " + updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("User not found for update: " + e.getMessage());
            return new ResponseEntity<>("User not found with ID: " + userId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while updating user: " + e.getMessage());
            return new ResponseEntity<>("Error updating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/projectionInterface")
    public ResponseEntity<Object> findBySelectedColumn() throws UserManagementException{
        List<UserDetailsProjectionInterface> findByColumn;
        try {
            log.debug("Fetching selected column from User Details");
            findByColumn = service.findBySelectedColumn();
            log.info("Selected column fetched successfully");
        } catch (UserManagementException e) {
            log.error("Couldn't fetch selected column successfully", e);
            return new ResponseEntity<>("Couldn't fetch selected column: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(findByColumn.stream().toList(), HttpStatus.OK);
    }

    @GetMapping("/countUsers")
    public ResponseEntity<Object> countTotalUsers() throws UserManagementException{
        Long totalUsers;
        try {
            log.debug("Counting total users");
            totalUsers = service.countTotalUsers();
            log.info("Total Number of Users: " + totalUsers);
        } catch (UserManagementException e) {
            log.error("Failed to get total number of users", e);
            return new ResponseEntity<>("Failed to get total number of users: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Total Number of Users: "+ totalUsers,HttpStatus.OK);
    }

    @GetMapping("/usersWithMinApplication")
    public ResponseEntity<Object> findUsersWithMinApplications() throws UserManagementException{
        List<UserDetailsDTO> usersWithMinimumApplications;
        try {
            log.debug("Finding users with minimum applications");
            usersWithMinimumApplications = service.findUsersWithMinApplication();
            log.info("Users with minimum applications found: " + usersWithMinimumApplications.size());
        } catch (UserManagementException e) {
            log.error("Failed to get users with minimum applications"+ e.getMessage());
            return new ResponseEntity<>("Failed to get users with minimum applications: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Least applications found is: " + usersWithMinimumApplications.size(), HttpStatus.OK);
    }

    @GetMapping("/userByOrder")
    public ResponseEntity<Object> findUserNamesAndEmailsOrdered() throws UserManagementException{
        List<UserIdProjectionInterface> orderedList;
        try {
            log.debug("Fetching user names and emails ordered by name");
            orderedList = service.findUserNamesAndEmailsOrdered();
            log.info("Users ordered by name: " + orderedList.stream().toList());
        } catch (UserManagementException e) {
            log.error("Failed to get users ordered by name and email", e);
            return new ResponseEntity<>("Failed to get users ordered by name and email: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderedList.stream().toList(), HttpStatus.OK);
    }

    @GetMapping("/innerJoin")
    public ResponseEntity<Object> findUsersInnerJoinApplications() throws UserManagementException{
        List<UserDetailsDTO> innerJoinApplications;
        try {
            log.debug("Fetching users with inner join on applications");
            innerJoinApplications = service.findUsersInnerJoinApplications();
            log.info("Users with inner join found: " + innerJoinApplications.size() + innerJoinApplications);
        } catch (UserManagementException e) {
            log.error("Failed to get users with inner join", e);
            return new ResponseEntity<>("Failed to get users with inner join: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>( innerJoinApplications, HttpStatus.OK);
    }

    @GetMapping("/leftJoin")
    public ResponseEntity<Object> findAllUsersLeftJoinApplications() throws UserManagementException{
        List<UserDetailsDTO> leftJoinApplications;
        try {
            log.debug("Fetching all users with left join on applicatins");
            leftJoinApplications = service.findAllUsersLeftJoinApplications();
            log.info("Users with left join found: " + leftJoinApplications.size() + leftJoinApplications);
        } catch (UserManagementException e) {
            log.error("Failed to get users with left join", e);
            return new ResponseEntity<>("Failed to get users with left join: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(leftJoinApplications,HttpStatus.OK);
    }

    @GetMapping("/rightJoin")
    public ResponseEntity<Object> findAllUsersRightJoinAgents() throws UserManagementException{
        List<UserDetailsDTO> rightJoinApplications;
        try {
            log.debug("Fetching all users with right join on applications");
            rightJoinApplications = service.findAllUsersRightJoinAgents();
            log.info("Users with right join found: " + rightJoinApplications.size() + rightJoinApplications);
        } catch (UserManagementException e) {
            log.error("Failed to get users with right join", e);
            return new ResponseEntity<>("Failed to get users with right join: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(rightJoinApplications, HttpStatus.OK);
    }

    @GetMapping("/crossJoin")
    public ResponseEntity<Object> findAllUsersCrossJoinApplications() throws UserManagementException{
        List<UserApplicationProjectionInterface> crossJoinApplications;
        try {
            log.debug("Fetching all users with cross join on applications");
            crossJoinApplications = service.findAllUsersCrossJoinApplications();
            log.info("Users with cross join found: " + crossJoinApplications.size() + crossJoinApplications);
        } catch (UserManagementException e) {
            log.error("Failed to get users with cross join", e);
            return new ResponseEntity<>("Failed to get users with cross join: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(crossJoinApplications.stream().toList(),HttpStatus.OK);
    }

    @GetMapping("/namedQuery2")
    public ResponseEntity<Object> findAllWithApplication() throws UserManagementException{
        List<UserDetailsDTO> allWithApplication;
        try {
            log.debug("Fetching all users with applications from named query");
            allWithApplication = service.findAllWithApplication();
            log.info("Users with application found: " + allWithApplication.size() + allWithApplication);
        } catch (UserManagementException e) {
            log.error("Failed to get users with application"+ e.getMessage());
            return new ResponseEntity<>("Failed to get users with application: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Users with application found: " + allWithApplication, HttpStatus.OK);
    }

}
