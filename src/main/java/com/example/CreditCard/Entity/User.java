package com.example.CreditCard.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users") // Your table name
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
