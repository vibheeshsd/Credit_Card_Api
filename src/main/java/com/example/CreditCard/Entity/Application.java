//package com.example.CreditCard.Entity;
//
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.stereotype.Component;
//import lombok.Data;
//
//import java.util.Date;
//import java.time.LocalDate;
//
//@Data
//@Builder
//@Component
//@Entity
//@NamedQuery(name = "findAllByOrderByCreatedAtDesc", query = "SELECT a FROM Application a ORDER BY a.createdAt DESC")
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "application")
//public class Application {
//
//    @Id
//    @Column(name = "application_id")
//    private String applicationId;
//
//    @Column(name = "applicant_name")
//    private String applicantName;
//
//    @Column(name = "dob")
//    @Temporal(TemporalType.DATE)
//    private LocalDate dateOfBirth;
//
//    @Column(name = "employment_status")
//    private String employmentStatus;
//
//    @Column(name = "income")
//    private String income;
//
//    @Column(name = "application_status")
//    private String status;
//
//    @Column(updatable = false, nullable = false)
//    @CreationTimestamp
//    private Date createdAt;
//
//    @Column(nullable = false)
//    @UpdateTimestamp
//    private Date updatedAt;
//
//    @ManyToOne
//    @JoinColumn(name = "city_id", referencedColumnName = "city_id", nullable = false)
//    private City city;
//
//    @ManyToOne
//    @JoinColumn(name = "state_id", referencedColumnName = "state_id", nullable = false)
//    private State state;
//
//    @ManyToOne
//    @JoinColumn(name = "country_id", referencedColumnName = "country_id", nullable = false)
//    private Country country;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
//    @JoinColumn(name = "username", referencedColumnName = "username")
//    private UserDetails userDetails;

//    public Date getUpdatedAt() {
//        return UpdatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        UpdatedAt = updatedAt;
//    }
//
//    public PersonalDetails getPersonalDetails() {
//        return personalDetails;
//    }
//
//    public void setPersonalDetails(PersonalDetails personalDetails) {
//        this.personalDetails = personalDetails;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//
//    public Country getCountry() {
//        return country;
//    }
//
//    public void setCountry(Country country) {
//        this.country = country;
//    }
//
//    public State getState() {
//        return state;
//    }
//
//    public void setState(State state) {
//        this.state = state;
//    }
//
//    public City getCity() {
//        return city;
//    }
//
//    public void setCity(City city) {
//        this.city = city;
//    }
//
//
//    public int getIncome() {
//        return income;
//    }
//
//    public void setIncome(int income) {
//        this.income = income;
//    }
//
//    public String getEmploymentStatus() {
//        return employmentStatus;
//    }
//
//    public void setEmploymentStatus(String employmentStatus) {
//        this.employmentStatus = employmentStatus;
//    }
//
//    public LocalDate getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getApplicantName() {
//        return applicantName;
//    }
//
//    public void setApplicantName(String applicantName) {
//        this.applicantName = applicantName;
//    }
//
//    public int getApplicationId() {
//        return applicationId;
//    }
//
//    public void setApplicationId(int applicationId) {
//        this.applicationId = applicationId;
//    }
//
//    @Override
//    public String toString() {
//        return "Application{" +
//                "applicationId=" + applicationId +
//                ", applicantName='" + applicantName + '\'' +
//                ", dateOfBirth=" + dateOfBirth +
//                ", employmentStatus='" + employmentStatus + '\'' +
//                ", income=" + income +
//                ", status='" + status + '\'' +
//                ", createdAt=" + createdAt +
//                ", UpdatedAt=" + UpdatedAt +
//                ", city=" + city +
//                ", state=" + state +
//                ", country=" + country +
//                ", personalDetails=" + personalDetails +
//                '}';
//    }
//}
package com.example.CreditCard.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;
import lombok.Data;

import java.util.Date;
import java.time.LocalDate;

@Data
@Builder
@Component
@Entity
@NamedQuery(name = "findAllByOrderByCreatedAtDesc", query = "SELECT a FROM Application a ORDER BY a.createdAt DESC")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
public class Application {

    @Id
    @Column(name = "application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String applicationId;

    @Column(name = "applicant_name")
    private String applicantName;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "application_status")
    private String applicationStatus;

    @Column(name = "submission_date")
    @Temporal(TemporalType.DATE)
    private LocalDate submissionDate;

    @Column(name = "approval_date")
    @Temporal(TemporalType.DATE)
    private LocalDate approvalDate;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserDetails userDetails;
}
