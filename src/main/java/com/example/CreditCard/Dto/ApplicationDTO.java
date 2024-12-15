package com.example.CreditCard.Dto;

//import com.example.CreditCard.Entity.*;
//import com.example.CreditCard.Exception.ApplicationManagementException;
//import com.example.CreditCard.Repo.*;
//import lombok.Builder;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.util.Date;
//
//@Data
//@Builder
//public class ApplicationDTO {
//    private Long applicationId;
//    private String applicantName;
//    private String accountType;
//    private Long userId;
//    private LocalDate submissionDate;
//    private LocalDate approvalDate;
//    private String rejectionReason;
//    private String applicationStatus;
//    private Date createdAt;
//    private Date updatedAt;
//
//    // Convert ApplicationDTO to Application entity
//    public static Application toApplicationEntity(
//            ApplicationDTO applicationDTO,
//            UserDetailsRepo userRepo,
//            ApplicationRepository repo,
//            CityRepository cityRepository,
//            StateRepository stateRepository
//            // Repositories should be injected as dependencies, not passed in
//    ) throws ApplicationManagementException {
//        // Fetch the user details based on userId
//        UserDetails userDetails = userRepo.findById(applicationDTO.getUserId())
//                .orElseThrow(() -> new ApplicationManagementException("User not found for ID: " + applicationDTO.getUserId()));
//
//        return Application.builder()
//                .userDetails(userDetails) // Set user details
//                .applicationId(applicationDTO.getApplicationId()) // This is typically not needed unless you're updating an existing application
//                .applicantName(applicationDTO.getApplicantName())
//                .accountType(applicationDTO.getAccountType())
//                .submissionDate(applicationDTO.getSubmissionDate())
//                .approvalDate(applicationDTO.getApprovalDate())
//                .rejectionReason(applicationDTO.getRejectionReason())
//                .createdAt(applicationDTO.getCreatedAt())
//                .updatedAt(applicationDTO.getUpdatedAt())
//                .applicationStatus(applicationDTO.getApplicationStatus())
//                .build();
//    }
//
//    // Convert Application entity to ApplicationDTO
//    public static ApplicationDTO toApplicationDTO(Application application) {
//        return ApplicationDTO.builder()
//                .applicationId(application.getApplicationId()) // Map application ID
//                .applicantName(application.getApplicantName())
//                .accountType(application.getAccountType())
//                .submissionDate(application.getSubmissionDate())
//                .approvalDate(application.getApprovalDate())
//                .rejectionReason(application.getRejectionReason())
//                .createdAt(application.getCreatedAt())
//                .updatedAt(application.getUpdatedAt())
//                .applicationStatus(application.getApplicationStatus())
//                .userId(application.getUserDetails().getUserId()) // Map user ID
//                .build();
//    }
//}

import com.example.CreditCard.Entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDTO {

    private String applicationId;
    private String applicantName;
    private String accountType;
    private String applicationStatus;
    private LocalDate submissionDate;
    private LocalDate approvalDate;
    private String rejectionReason;
    private Date createdAt;
    private Date updatedAt;
    private String userId; // To hold the user ID instead of the entire UserDetails entity

    // Static method to convert Entity to DTO
    public static ApplicationDTO toApplicationDTO(Application application) {
        return ApplicationDTO.builder()
                .applicationId(application.getApplicationId())
                .applicantName(application.getApplicantName())
                .accountType(application.getAccountType())
                .applicationStatus(application.getApplicationStatus())
                .submissionDate(application.getSubmissionDate())
                .approvalDate(application.getApprovalDate())
                .rejectionReason(application.getRejectionReason())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .userId(application.getUserDetails() != null ? application.getUserDetails().getUserId() : null)
                .build();
    }

    // Static method to convert DTO to Entity
    public Application toApplicationEntity() {
        Application application = Application.builder()
                .applicationId(this.applicationId)
                .applicantName(this.applicantName)
                .accountType(this.accountType)
                .applicationStatus(this.applicationStatus)
                .submissionDate(this.submissionDate)
                .approvalDate(this.approvalDate)
                .rejectionReason(this.rejectionReason)
                .build();
        return application;
    }
}
