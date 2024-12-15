package com.example.CreditCard.Repo;


import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ApplicationProjectionInterface {
    public Integer getApplicationId();
    public String getApplicantName();
    public Date getDateOfBirth();
}
