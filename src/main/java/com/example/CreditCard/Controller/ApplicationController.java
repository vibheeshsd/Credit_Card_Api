package com.example.CreditCard.Controller;


import com.example.CreditCard.Dto.ApplicationDTO;
import com.example.CreditCard.Dto.UserDetailsDTO;
import com.example.CreditCard.Entity.Application;
import com.example.CreditCard.Entity.UserDetails;
import com.example.CreditCard.Exception.ApplicationManagementException;
import com.example.CreditCard.Exception.ApplicationNotFound;
import com.example.CreditCard.Exception.UserManagementException;
import com.example.CreditCard.Exception.UserNotFoundException;
import com.example.CreditCard.Service.ApplicationService;
import com.example.CreditCard.Service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/application")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {
    static Logger log = Logger.getLogger(ApplicationController.class);
    @Autowired
    ApplicationService service;

    @Autowired
    UserDetailsService udService;

//    @PostMapping("/insert")
//    public ResponseEntity<Object> insertApplication(@RequestBody ApplicationDTO applicationDTO) throws ApplicationManagementException, UserManagementException {
//        List<ApplicationDTO> newApplicationList;
//        System.out.println(applicationDTO.getApplicationId());
//        try{
//            log.debug("Creating new application with details: " + applicationDTO);
//            List<ApplicationDTO> currentApplicationList = service.findAllApplication();
//            log.info("Application List before inserting data: " + currentApplicationList);
//            ApplicationDTO newApplication = service.insert(applicationDTO);
//            newApplicationList = service.findAllApplication();
//            log.info("Application created successfully"+ newApplicationList);
//        } catch (ApplicationManagementException e) {
//            log.error("Application Creation failed: " + e.getMessage(), e);
//            return new ResponseEntity<>("Application Creation failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(applicationDTO, HttpStatus.OK);
//    }

    @PostMapping("/add")
    public ResponseEntity<Object> addApplication(@RequestBody ApplicationDTO applicationDTO) throws ApplicationManagementException, ApplicationNotFound {
        ApplicationDTO responseDTO = null;
        try{
        // Fetch the associated UserDetails using the userId from DTO
        UserDetails userDetails = udService.getUserById(applicationDTO.getUserId());
        // Insert the application
        Application application = service.addApplication(applicationDTO, userDetails);
        // Convert entity to DTO for the response
        responseDTO = ApplicationDTO.toApplicationDTO(application);
        } catch ( ApplicationManagementException e){
            return new ResponseEntity<>("Application Creation Failde" + e.getMessage(), HttpStatus.BAD_REQUEST);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>(applicationDTO, HttpStatus.OK);
    }


    @GetMapping("/byName/{applicantName}")
    public ResponseEntity<Object> getApplicationById(@PathVariable String applicantName) throws ApplicationManagementException, ApplicationNotFound {
       ApplicationDTO applicationDTO;
        try{
           log.debug("Fetching application by Name "+ applicantName);
           applicationDTO = service.findApplication(applicantName);
           log.info("Application found" + applicationDTO);
       } catch (ApplicationNotFound e) {
            log.error("Application with Name " + applicantName + " not found: " + e.getMessage(), e);
            return new ResponseEntity<>("Application with Name " + applicantName + " not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ApplicationManagementException e) {
            log.error("Fetching application failed: " + e.getMessage(), e);
            return new ResponseEntity<>("Failed to fetch application: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserManagementException e) {
            log.error("User details error while fetching application: " + e.getMessage(), e);
            return new ResponseEntity<>("Error fetching user details: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(applicationDTO, HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<Object> getAllApplications() throws ApplicationManagementException {
        List<ApplicationDTO> applications;
        try {
            log.debug("Fetching all application");
            applications = service.findAllApplication();
            log.info("All applications fetched successfully " + applications);
        } catch (ApplicationManagementException e) {
            log.error("Error fetching all applications: " + e.getMessage(), e);
            return new ResponseEntity<>("Error fetching all applications: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @PutMapping("/update/{applicationId}")
    public ResponseEntity<Object> updateApplication(
            @PathVariable String applicationId,
            @RequestBody ApplicationDTO applicationDto
    ) {
        try {
            log.debug("Updating application with ID: " + applicationId);
            ApplicationDTO updatedApplication = service.updateApplication(applicationId, applicationDto);
            log.info("Application updated successfully: " + updatedApplication);
            return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
        } catch (ApplicationNotFound e) {
            log.error("Application not found for update: " + e.getMessage());
            return new ResponseEntity<>("Application not found with ID: " + applicationId, HttpStatus.NOT_FOUND);
        } catch (ApplicationManagementException e) {
            log.error("Error updating application: " + e.getMessage());
            return new ResponseEntity<>("Error updating application: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error during update: " + e.getMessage());
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{applicationId}")
    public ResponseEntity<Object> deleteApplication(@PathVariable String applicationId) {
        try {
            log.debug("Deleting application with ID: " + applicationId);
            service.deleteApplication(applicationId);
            log.info("Application deleted successfully with ID: " + applicationId);
            return new ResponseEntity<>("Application deleted successfully", HttpStatus.OK);
        } catch (ApplicationNotFound e) {
            log.error("Application not found for deletion: " + e.getMessage());
            return new ResponseEntity<>("Application not found with ID: " + applicationId, HttpStatus.NOT_FOUND);
        } catch (ApplicationManagementException e) {
            log.error("Error deleting application: " + e.getMessage());
            return new ResponseEntity<>("Error deleting application: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error during deletion: " + e.getMessage());
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/namedQuery1")
    public ResponseEntity<Object> findAllByOrderByCreatedAtDesc() throws UserManagementException{
        List<ApplicationDTO> named;
        try{
            log.debug("Named query");
            named = service.findAllByOrderByCreatedAtDesc();
            log.info("Named Query 1: " + named);
        } catch (ApplicationManagementException e) {
            log.error("Failed to fetch ordered applications: " + e.getMessage(), e);
            return new ResponseEntity<>("Failed to fetch ordered applications: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Named Query 1: " + named, HttpStatus.OK);
    }
}
