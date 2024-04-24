package org.example.users.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForFilterDto {
    private Integer page;
    private Integer pageSize;
    private LocalDate fromDate;
    private LocalDate toDate;
}