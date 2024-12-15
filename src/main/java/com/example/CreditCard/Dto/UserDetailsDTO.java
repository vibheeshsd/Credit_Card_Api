package com.example.CreditCard.Dto;

//import com.example.CreditCard.Entity.Application;
//import com.example.CreditCard.Entity.UserDetails;
//import com.example.CreditCard.Exception.UserManagementException;
//import com.example.CreditCard.Repo.*;
//import lombok.Builder;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Data
//@Builder
//public class UserDetailsDTO {
//    private String firstName;
//    private String lastName;
//    private LocalDate dateOfBirth;
//    private String occupation;
//    private String address;
//    private String email;
//    private String number;
//    private Long userId;
//    private Integer city;
//    private Integer state;
//    private String alternateNumber;
//    private List<Long> applicationsIds; // Application IDs
//
//    // Convert UserDetails entity to UserDetailsDTO
//    public static UserDetailsDTO toUserDetailsDTO(UserDetails ud) {
//        return UserDetailsDTO.builder()
//                .userId(ud.getUserId())
//                .firstName(ud.getFirstName())
//                .lastName(ud.getLastName())
//                .dateOfBirth(ud.getDateOfBirth())
//                .occupation(ud.getOccupation())
//                .email(ud.getEmailId())
//                .number(ud.getMobileNumber())
//                .alternateNumber(ud.getAlternateMobileNumber())
//                .address(ud.getAddress())
//                .city(ud.getCity().getCityId())
//                .state(ud.getState().getStateId())
//                // Map applications list to application IDs
//                .applicationsIds(ud.getApplications().stream()
//                        .map(Application::getApplicationId) // Get the application ID
//                        .collect(Collectors.toList()))
//                .build();
//    }
//
//    // Convert UserDetailsDTO to UserDetails entity
//    public static UserDetails toUserEntity(
//            UserDetailsDTO udDto,
//            ApplicationRepository applicationRepository,
//            UserDetailsRepo udRepository,
//            CityRepository cityRepository,
//            StateRepository stateRepository
//    ) {
//        // Convert application IDs to Application entities
//        List<Application> applications = udDto.getApplicationsIds().stream()
//                .map(applicationId -> applicationRepository.findById(applicationId)
//                        .orElseThrow(() -> new UserManagementException("Application not found for ID: " + applicationId)))
//                .collect(Collectors.toList());
//
//        // Convert UserDetailsDTO to UserDetails entity
//        return UserDetails.builder()
//                .city(cityRepository.findById(udDto.getCity())
//                        .orElseThrow(() -> new UserManagementException("City not found")))
//
//                .state(stateRepository.findById(udDto.getState())
//                        .orElseThrow(() -> new UserManagementException("State not found")))
//
//                .firstName(udDto.getFirstName())
//                .lastName(udDto.getLastName())
//                .emailId(udDto.getEmail())
//                .mobileNumber(udDto.getNumber())
//                .address(udDto.getAddress())
//                .alternateMobileNumber(udDto.getAlternateNumber())
//                .occupation(udDto.getOccupation())
//                .dateOfBirth(udDto.getDateOfBirth())
//
//                // Do not set userId if it's not required for new records (auto-generated)
//                // .userId(udDto.getUserId()) // Normally, userId is auto-generated.
//
//                .applications(applications) // Set the applications (mapped from application IDs)
//                .build();
//    }
//
//}

import com.example.CreditCard.Entity.City;
import com.example.CreditCard.Entity.State;
import com.example.CreditCard.Entity.UserDetails;
import com.example.CreditCard.Repo.CityRepository;
import com.example.CreditCard.Repo.StateRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    private String occupation;
    private String emailId;
    private String alternateMobileNumber;
    private String address;
    private Integer cityId; // Changed to Integer
    private Integer stateId; // Changed to Integer
    private String cityName;
    private String stateName;

    // Static utility method to convert Entity to DTO
    public static UserDetailsDTO toUserDetailsDTO(UserDetails userDetails) {
        return UserDetailsDTO.builder()
                .userId(userDetails.getUserId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .dateOfBirth(userDetails.getDateOfBirth())
                .mobileNumber(userDetails.getMobileNumber())
                .occupation(userDetails.getOccupation())
                .emailId(userDetails.getEmailId())
                .alternateMobileNumber(userDetails.getAlternateMobileNumber())
                .address(userDetails.getAddress())
                .cityName(userDetails.getCity().getCityName())
                .stateName(userDetails.getState().getStateName())
                .cityId(userDetails.getCity() != null ? userDetails.getCity().getCityId() : null)
                .stateId(userDetails.getState() != null ? userDetails.getState().getStateId() : null)
                .build();
    }

    // Static utility method to convert DTO to Entity
    public UserDetails toEntity(CityRepository cityRepository, StateRepository stateRepository) {
        UserDetails userDetails = UserDetails.builder()
                .userId(this.userId)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .dateOfBirth(this.dateOfBirth)
                .mobileNumber(this.mobileNumber)
                .occupation(this.occupation)
                .emailId(this.emailId)
                .alternateMobileNumber(this.alternateMobileNumber)
                .address(this.address)
                .build();

        // Fetch City and State entities based on IDs
        if (this.cityId != null) {
            City city = cityRepository.findById( this.cityId)
                    .orElseThrow(() -> new IllegalArgumentException("City not found with ID: " + this.cityId));
            userDetails.setCity(city);
        }

        if (this.stateId != null) {
            State state = stateRepository.findById( this.stateId)
                    .orElseThrow(() -> new IllegalArgumentException("State not found with ID: " + this.stateId));
            userDetails.setState(state);
        }

        return userDetails;
    }
}


