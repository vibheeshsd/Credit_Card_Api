package com.example.CreditCard.Bo;

import com.example.CreditCard.Dto.ApplicationDTO;
import com.example.CreditCard.Dto.UserDetailsDTO;
import com.example.CreditCard.Entity.Application;
import com.example.CreditCard.Entity.UserDetails;
import com.example.CreditCard.Exception.ApplicationManagementException;
import com.example.CreditCard.Exception.ApplicationNotFound;
import com.example.CreditCard.Exception.UserManagementException;
import com.example.CreditCard.Exception.UserNotFoundException;
import com.example.CreditCard.Repo.*;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Component
public class ApplicationBo {

    @Autowired
    private ApplicationRepository repo;

    @Autowired
    private UserDetailsRepo userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;


    static Logger log = Logger.getLogger(ApplicationBo.class);

//     @Transactional
//    public ApplicationDTO insert (ApplicationDTO applicationDto)  throws  ApplicationManagementException, UserManagementException {
//        Application application = ApplicationDTO.toApplicationEntity(applicationDto, userRepository, repo, cityRepository, stateRepository);
//        isValidInsert(application);
//        log.info("Inserting application: "+ applicationDto);
//        if(repo.findByApplicationId(application.getApplicationId()) != null) {
//            throw new ApplicationManagementException("Application already exists");
//        }
//        log.info("Application inserted successfully "+ application.getApplicationId());
//        return ApplicationDTO.toApplicationDTO(repo.save(application));
//    }

    public Application addApplication(ApplicationDTO applicationDTO, UserDetails userDetails) {
    // Convert DTO to Entity
    Application application = applicationDTO.toApplicationEntity();

    // Set the associated UserDetails
    application.setUserDetails(userDetails);

    // Save the application entity
    return repo.save(application);
}



    @Transactional(readOnly = true)
    public Application findApplication(String applicantName) throws ApplicationManagementException, ApplicationNotFound {
        log.info("Fetching application with Name: "+ applicantName);
        Application application = repo.findByApplicantName(applicantName).orElse(null);
        if(application == null) {
            log.warn("Application not found: "+ applicantName);
            throw new ApplicationNotFound("Application not found: "+ applicantName);
        }
        log.info("Application found: "+ applicantName);
        return application;
    }

    @Transactional(readOnly = true)
    public List<Application> findAllApplications() {
        log.info("Fetching all applications");
        List<Application> application = repo.findAll();
        log.info("Total applications found: " + application.size());
        return application;
    }

    @Transactional
    public ApplicationDTO updateApplication(String applicationId, ApplicationDTO applicationDto) throws ApplicationManagementException, ApplicationNotFound {
        log.info("Updating application with ID: " + applicationId);
        validateApplicationId(applicationId);

        Application existingApplication = repo.findByApplicationId(applicationId);
        if (existingApplication == null) {
            log.error("Application not found for update: " + applicationId);
            throw new ApplicationNotFound("Application not found: " + applicationId);
        }

        // Update fields
        existingApplication.setApplicantName(applicationDto.getApplicantName());
        existingApplication.setAccountType(applicationDto.getAccountType());
        existingApplication.setApplicationStatus(applicationDto.getApplicationStatus());
        existingApplication.setSubmissionDate(applicationDto.getSubmissionDate());
        existingApplication.setApprovalDate(applicationDto.getApprovalDate());
        existingApplication.setRejectionReason(applicationDto.getRejectionReason());
        log.info("Application updated successfully: " + applicationId);
        return ApplicationDTO.toApplicationDTO(repo.save(existingApplication));
    }

    @Transactional
    public void deleteApplication(String applicationId) throws ApplicationManagementException, ApplicationNotFound {
        log.info("Deleting application with ID: " + applicationId);
        validateApplicationId(applicationId);

        Application application = repo.findByApplicationId(applicationId);
        if (application == null) {
            log.error("Application not found for deletion: " + applicationId);
            throw new ApplicationNotFound("Application not found: " + applicationId);
        }

        repo.deleteById(applicationId);
        log.info("Application deleted successfully: " + applicationId);
    }

    public void isValidInsert(Application application){
        log.debug("Validating application data for insertion:" + application);
//        if(application.getApplicationId() == null){
//            log.error("Application Id cannot be null");
//              throw new ApplicationManagementException("Application Id cannot be null");
//        }

        if(application.getApplicantName() == null) {
            log.error("Applicant Name cannot be null");
            throw new ApplicationManagementException("Applicant Name cannot be null");
        }

        if (!application.getApplicantName().matches("[A-Za-z ]+")) {
            log.error("Applicant Name should only contain letters, no digits allowed: "+ application.getApplicantName());
            throw new ApplicationManagementException("Applicant Name should only contain letters, no digits allowed: " + application.getApplicantName());
        }

        if(application.getUserDetails() == null){
            log.error("User Details cannot be null");
            throw new ApplicationManagementException("User Data cannot be null");
        }

        if(application.getApplicationStatus() == null) {
            log.error("Application Status cannot be null");
            throw new ApplicationManagementException("Application Status cannot be null");
        }

        if(application.getUserDetails().getUserId() == null) {
            log.error("User Id cannot be null");
            throw new ApplicationManagementException("User Id cannot be null");
        }
        log.debug("Applicant data validated successfully");
    }

    private void validateApplicationId(String applicationId) throws ApplicationManagementException {
        if (applicationId == null) {
            log.error("Application ID cannot be empty");
            throw new ApplicationManagementException("Application ID cannot be empty");
        }
        log.debug("Application ID {} validated successfully."+ applicationId);
    }

    public List<Application> findAllByOrderByCreatedAtDesc(){
         return repo.findAllByOrderByCreatedAtDesc();
     }

}


