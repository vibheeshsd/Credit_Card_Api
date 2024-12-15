package com.example.CreditCard.Repo;


import com.example.CreditCard.Entity.Application;
import com.example.CreditCard.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    Application findApplicationByApplicantName(String ApplicantName);
    Optional<Application> findByApplicantName(String ApplicantName);

    @Query("select a.applicationId as applicationId, a.applicantName as applicantName from Application a")
    List<ApplicationProjectionInterface> findBySelectedColumn();

    Application findByApplicationId(String applicationId);

    @Query(name = "findAllByOrderByCreatedAtDesc")
    List<Application> findAllByOrderByCreatedAtDesc();
}
