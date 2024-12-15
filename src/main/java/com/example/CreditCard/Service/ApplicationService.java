package com.example.CreditCard.Service;


import com.example.CreditCard.Bo.ApplicationBo;
import com.example.CreditCard.Dto.ApplicationDTO;
import com.example.CreditCard.Entity.Application;
import com.example.CreditCard.Entity.UserDetails;
import com.example.CreditCard.Exception.ApplicationManagementException;
import com.example.CreditCard.Exception.ApplicationNotFound;
import com.example.CreditCard.Exception.UserManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationBo applicationBo;

    public Application addApplication(ApplicationDTO applicationDto, UserDetails userDetails) throws ApplicationManagementException, UserManagementException {
        return applicationBo.addApplication(applicationDto, userDetails);
    }



    public ApplicationDTO findApplication(String applicantName) throws ApplicationManagementException, ApplicationNotFound {
        Application application = applicationBo.findApplication(applicantName);
        if(application == null) {
            throw new ApplicationNotFound("Application not found");
        }
        return ApplicationDTO.toApplicationDTO(application);
    }

    public List<ApplicationDTO> findAllApplication() {
        List<Application> applications = applicationBo.findAllApplications();
        return applications.stream().map(ApplicationDTO::toApplicationDTO).toList();
    }

    public ApplicationDTO updateApplication(String applicationId, ApplicationDTO applicationDto) throws ApplicationManagementException, ApplicationNotFound {
        return applicationBo.updateApplication(applicationId, applicationDto);
    }

    public void deleteApplication(String applicationId) throws ApplicationManagementException, ApplicationNotFound {
        applicationBo.deleteApplication(applicationId);
    }



    public List<ApplicationDTO> findAllByOrderByCreatedAtDesc(){
        return applicationBo.findAllByOrderByCreatedAtDesc().stream().map(ApplicationDTO::toApplicationDTO).toList();
    }

}
