package org.example.users.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForViewDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String address;
    private String phoneNumber;
}