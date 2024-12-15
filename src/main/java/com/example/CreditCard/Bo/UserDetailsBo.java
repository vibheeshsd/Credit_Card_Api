package com.example.CreditCard.Bo;


import com.example.CreditCard.Controller.ApplicationController;
import com.example.CreditCard.Dto.UserDetailsDTO;
import com.example.CreditCard.Entity.UserDetails;
import com.example.CreditCard.Exception.ApplicationManagementException;
import com.example.CreditCard.Exception.UserManagementException;
import com.example.CreditCard.Exception.UserNotFoundException;
import com.example.CreditCard.Repo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class UserDetailsBo {


    @Autowired
    private UserDetailsRepo userRepo;

    @Autowired
    private ApplicationRepository repo;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;


    static Logger log = Logger.getLogger(UserDetailsBo.class);

//    @Transactional
//    public UserDetailsDTO insert(UserDetailsDTO userDetailsDTO) throws UserManagementException, ApplicationManagementException {
//        // Convert DTO to entity
//        UserDetails userDetails = UserDetailsDTO.toUserEntity(userDetailsDTO, repo, userRepo,  cityRepository, stateRepository  );
//
//        // Validation or other business logic
//        isValidateInsert(userDetails);
//
//        // Check if user already exists by mobile number or email (example)
//        if (userRepo.existsById(userDetails.getUserId())) {
//            throw new UserManagementException("User already exists");
//        }
//
//        // Save the user entity and convert back to DTO
//        userDetails = userRepo.save(userDetails);
//
//        return UserDetailsDTO.toUserDetailsDTO(userDetails);  // Convert entity back to DTO
//    }

    public UserDetails addUser(UserDetailsDTO userDetailsDTO) {
        UserDetails userDetails = userDetailsDTO.toEntity(cityRepository, stateRepository);
         // Validation or other business logic
        isValidateInsert(userDetails);

        // Check if user already exists by mobile number or email (example)
        if (userRepo.existsById(userDetails.getUserId())) {
            throw new UserManagementException("User already exists");
        }
        return userRepo.save(userDetails);
    }



    @Transactional(readOnly = true)
    public UserDetails findUserDetails(String userName) throws ApplicationManagementException, UserManagementException {
        UserDetails ud = userRepo.findByFirstName(userName).orElse(null);
        if(ud == null){
            throw new UserManagementException("User with " + userName+ " not found");
        }
        return ud;
    }

    @Transactional(readOnly = true)
    public List<UserDetails> findAllUserDetails() {
        return userRepo.findAll();
    }

    @Transactional
    public boolean deleteUser(String userId){
        if(userRepo.existsById(userId)){
            userRepo.deleteById(userId);
            log.info("User deleted successfully");
            return true;
        } else {
            log.warn("User with Id: "+ userId+ " not found for deletion.");
            return false;
        }
    }

    @Transactional
    public UserDetails updateUser(String userId, UserDetailsDTO userDetailsDTO) throws UserNotFoundException, UserManagementException {
        UserDetails existingUser = userRepo.findById(userId).orElseThrow(() -> new UserManagementException("No such user with userId: " + userId));

        // Update fields
        if (userDetailsDTO.getFirstName() != null){
            existingUser.setFirstName(userDetailsDTO.getFirstName());
        } else {
            throw new UserManagementException("User's FirstName is Null");
        }
        if (userDetailsDTO.getLastName() != null) {
            existingUser.setLastName(userDetailsDTO.getLastName());
        } else {
            throw new UserManagementException("User's Last Name is Null");
        }
        if (userDetailsDTO.getEmailId() != null) {
            existingUser.setEmailId(userDetailsDTO.getEmailId());
        } else {
            throw new UserManagementException("User's Email is Null");
        }
        if (userDetailsDTO.getMobileNumber() != null) {
            existingUser.setMobileNumber(userDetailsDTO.getMobileNumber());
        } else {
            throw new UserManagementException("User's Mobile Number is Null");
        }

        if (userDetailsDTO.getAlternateMobileNumber() != null){
            existingUser.setAlternateMobileNumber(userDetailsDTO.getAlternateMobileNumber());
        } else {
            throw new UserManagementException("User's Alternate Mobile is Null");
        }
        if (userDetailsDTO.getAddress() != null){
            existingUser.setAddress(userDetailsDTO.getAddress());
        } else {
            throw new UserManagementException("User's Address is Null");
        }

        if(userDetailsDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(userDetailsDTO.getDateOfBirth());
        } else {
            throw new UserManagementException("User's Date of Birth is Null");
        }
        if(userDetailsDTO.getOccupation() != null) {
            existingUser.setOccupation(userDetailsDTO.getOccupation());
        } else {
            throw new UserManagementException("User's Occupation is Null");
        }
        existingUser.setCity(cityRepository.findById(userDetailsDTO.getCityId())
                .orElseThrow(() -> new ApplicationManagementException("City not found")));
        existingUser.setState(stateRepository.findById(userDetailsDTO.getStateId())
                .orElseThrow(() -> new ApplicationManagementException("State not found")));

        log.info("Updating user details: " + existingUser);
        return userRepo.save(existingUser);
    }

    private void isValidateInsert(UserDetails user) throws UserManagementException{
        log.debug("Validating user details for insertion:" + user);

        if(user.getFirstName() == null) {
            log.error("User First Name cannot be null");
            throw new UserManagementException("User First Name cannot be null");
        }

        if(user.getLastName() == null) {
            log.error("User Last Name cannot be null");
            throw new UserManagementException("User Last Name cannot be null");
        }

//        if(user.getEmailId() == null) {
//            log.error("User Email cannot be null");
//            throw new UserManagementException("User Email cannot be null");
//        }

        if(user.getDateOfBirth() == null) {
            log.error("User Date of Birth cannot be null");
            throw new ApplicationManagementException("User Date of Birth cannot be null");
        }

        if (user.getCity() == null) {
            log.error("City cannot be null");
            throw new ApplicationManagementException("City cannot be null");
        }
        if (user.getState() == null) {
            log.error("State cannot be null");
            throw new ApplicationManagementException("State cannot be null");
        }

        if(user.getMobileNumber() == null || user.getMobileNumber().isEmpty()) {
            log.error("User Mobile Number cannot be null");
            throw new UserManagementException("User Mobile Number cannot be null");
        }
        if(user.getAlternateMobileNumber() == null || user.getAlternateMobileNumber().isEmpty()){
            log.error("User Alternate Mobile Number cannot be null");
            throw new UserManagementException("User Alternate Mobile Number cannot be null");
        }

        if (!user.getFirstName().matches("[A-Za-z ]+")) {
            log.error("User Name should only contain letters, no digits allowed: "+ user.getFirstName());
            throw new UserManagementException("User Name should only contain letters, no digits allowed: " + user.getFirstName());
        }

        if (!user.getLastName().matches("[A-Za-z ]+")) {
            log.error("User Name should only contain letters, no digits allowed: "+ user.getLastName());
            throw new UserManagementException("User Name should only contain letters, no digits allowed: " + user.getLastName());
        }

        if (!user.getMobileNumber().matches("[0-9]+")) {
            log.error("Contact number should only contain digits: "+ user.getMobileNumber());
            throw new UserManagementException("Contact number should only contain digits: " + user.getMobileNumber());
        }

        if (!user.getAlternateMobileNumber().matches("[0-9]+")) {
            log.error("Alternate Contact number should only contain digits: "+ user.getAlternateMobileNumber());
            throw new UserManagementException("Alternate Contact number should only contain digits: " + user.getAlternateMobileNumber());
        }

        if (user.getMobileNumber().length() != 10) {
            log.error("User contact number must be 10 digits: "+ user.getMobileNumber());
            throw new UserManagementException("User contact number must be 10 digits: " + user.getMobileNumber());
        }

        if (user.getAlternateMobileNumber().length() != 10) {
            log.error("User alternate contact number must be 10 digits: "+ user.getAlternateMobileNumber());
            throw new UserManagementException("User alternate contact number must be 10 digits: " + user.getAlternateMobileNumber());
        }

        if (!isValidEmail(user.getEmailId())) {
            log.error("Invalid email format: "+ user.getEmailId());
            throw new UserManagementException("Invalid email format: " + user.getEmailId());
        }
        log.debug("User details validated successfully");
    }

    private boolean isValidEmail(String email) {
        log.debug("Validating provider email: "+ email);
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    public Long countTotalUsers(){ return userRepo.countTotalUsers();}

    public List<UserDetails> findUsersWithMinApplication() { return userRepo.findUsersWithMinApplication(); }


    public List<UserIdProjectionInterface> findUserNamesAndEmailsOrdered() {
        return userRepo.findUserNamesAndEmailsOrdered();
    }

    public List<UserDetails> findUsersInnerJoinApplications(){
        return userRepo.findUsersInnerJoinApplications();
    }

    public List<UserDetails> findAllUsersLeftJoinApplications(){ return userRepo.findAllUsersLeftJoinApplications();}

    public List<UserDetails> findAllUsersRightJoinAgents() { return userRepo.findAllUsersRightJoinAgents();}

    public List<UserApplicationProjectionInterface> findAllUsersCrossJoinApplications(){
        return userRepo.findAllUsersCrossJoinApplications();
    }

    public List<UserDetailsProjectionInterface> findBySelectedColumn() {
        return userRepo.findBySelectedColumn();
    }

    public List<UserDetails> findAllWithApplication(){
        return userRepo.findAllWithApplication();
    }
}
