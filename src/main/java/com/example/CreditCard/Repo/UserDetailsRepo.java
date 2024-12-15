package com.example.CreditCard.Repo;

import com.example.CreditCard.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, String> {

    Optional<UserDetails> findByFirstName(String userName);


    @Query("SELECT u.userId as userId, u.firstName as firstName, u.mobileNumber as mobileNumber from UserDetails u")
    List<UserDetailsProjectionInterface> findBySelectedColumn();


    @Query("select count(u) from UserDetails u")
    Long countTotalUsers();

    @Query("select u from UserDetails u where size(u.applications) = (select min(size(u2.applications)) from UserDetails u2)")
    List<UserDetails> findUsersWithMinApplication();

    @Query("SELECT u.userId AS userId, u.firstName as firstName, u.emailId AS email FROM UserDetails u ORDER BY u.firstName ASC, u.emailId ASC")
    List<UserIdProjectionInterface> findUserNamesAndEmailsOrdered();


    @Query("SELECT u FROM UserDetails u JOIN u.applications a")
    List<UserDetails> findUsersInnerJoinApplications();

    @Query("SELECT u FROM UserDetails u LEFT JOIN u.applications a")
    List<UserDetails> findAllUsersLeftJoinApplications();

    @Query("SELECT u FROM UserDetails u RIGHT JOIN u.applications a")
    List<UserDetails> findAllUsersRightJoinAgents();

    @Query("SELECT u.emailId AS email, a.applicantName AS applicantName FROM UserDetails u CROSS JOIN Application a")
//    @Query("SELECT u,a FROM UserDetails u CROSS JOIN Application a")
    List<UserApplicationProjectionInterface> findAllUsersCrossJoinApplications();

    @Query(name ="joinNamedQuery")
    List<UserDetails> findAllWithApplication();

}
