package org.example.users.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    private LocalDate birthdate;
    @Column(length = 100)
    private String address;
    @Column(unique = true, length = 100)
    private String phoneNumber;
}