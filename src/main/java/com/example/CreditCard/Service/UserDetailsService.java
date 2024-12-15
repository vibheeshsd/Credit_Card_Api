package com.example.CreditCard.Service;


import com.example.CreditCard.Bo.UserDetailsBo;
import com.example.CreditCard.Dto.UserDetailsDTO;
import com.example.CreditCard.Entity.UserDetails;
import com.example.CreditCard.Exception.UserManagementException;
import com.example.CreditCard.Exception.UserNotFoundException;
import com.example.CreditCard.Repo.UserApplicationProjectionInterface;
import com.example.CreditCard.Repo.UserDetailsProjectionInterface;
import com.example.CreditCard.Repo.UserDetailsRepo;
import com.example.CreditCard.Repo.UserIdProjectionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService {
    @Autowired
    UserDetailsBo userDetailsBo;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    public UserDetails getUserById(String userId) {
        return userDetailsRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }



    public UserDetails addUser(UserDetailsDTO userDetailsDTO) {
        return userDetailsBo.addUser(userDetailsDTO);
    }

    public UserDetailsDTO findUserDetails(String userName) throws UserManagementException, UserNotFoundException {
        UserDetails userDetails = userDetailsBo.findUserDetails(userName);
        return UserDetailsDTO.toUserDetailsDTO(userDetails);
    }

    public List<UserDetailsDTO> findAllUserDetails() {
        List<UserDetails> userDetailsList = userDetailsBo.findAllUserDetails();
        return userDetailsList.stream().map(UserDetailsDTO::toUserDetailsDTO).toList();
    }

    public void deleteUser(String userId) throws UserNotFoundException {
        if(!userDetailsBo.deleteUser(userId)){
            throw new UserNotFoundException("User with Id: "+ userId + " does not exist.");
        }
    }

    public UserDetailsDTO updateUser(String userId, UserDetailsDTO userDetailsDTO) throws UserNotFoundException {
        UserDetails userDetails = userDetailsBo.updateUser(userId, userDetailsDTO);
        return UserDetailsDTO.toUserDetailsDTO(userDetails);
    }

    public Long countTotalUsers(){ return userDetailsBo.countTotalUsers();}

    public List<UserDetailsDTO> findUsersWithMinApplication() {
        return userDetailsBo.findUsersWithMinApplication().stream().map(UserDetailsDTO::toUserDetailsDTO).toList();
    }

    public List<UserIdProjectionInterface> findUserNamesAndEmailsOrdered() {
        return userDetailsBo.findUserNamesAndEmailsOrdered();
    }

    public List<UserDetailsDTO> findUsersInnerJoinApplications(){
        return userDetailsBo.findUsersInnerJoinApplications().stream().map(UserDetailsDTO::toUserDetailsDTO).toList();
    }

    public List<UserDetailsDTO> findAllUsersLeftJoinApplications(){ return userDetailsBo.findAllUsersLeftJoinApplications().stream().map(UserDetailsDTO::toUserDetailsDTO).toList();}

    public List<UserDetailsDTO> findAllUsersRightJoinAgents() {
        return userDetailsBo.findAllUsersRightJoinAgents().stream().map(UserDetailsDTO::toUserDetailsDTO).toList();
    }

    public List<UserApplicationProjectionInterface> findAllUsersCrossJoinApplications(){
        return userDetailsBo.findAllUsersCrossJoinApplications();
    }

    public List<UserDetailsProjectionInterface> findBySelectedColumn() {
        return userDetailsBo.findBySelectedColumn();
    }

    public List<UserDetailsDTO> findAllWithApplication(){
        return userDetailsBo.findAllWithApplication().stream().map(UserDetailsDTO::toUserDetailsDTO).toList();
    }

}
