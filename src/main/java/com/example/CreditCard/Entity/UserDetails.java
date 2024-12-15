package com.example.CreditCard.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_details")
@NamedQuery(name = "joinNamedQuery", query = "SELECT u FROM UserDetails u JOIN u.applications a")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false, unique = true)
    private String userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Column(name = "mobile_number", unique = true, nullable = false)
    private String mobileNumber;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "email", unique = true)
    private String emailId;

    @Column(name = "alternate_mobile_number", unique = true, nullable = false)
    private String alternateMobileNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "city_id", referencedColumnName = "city_id", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "state_id", nullable = false)
    private State state;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "userDetails")
    private List<Application> applications;
}
