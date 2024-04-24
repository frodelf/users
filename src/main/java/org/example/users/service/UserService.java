package org.example.users.service;

import org.example.users.dto.PersonalInformationDto;
import org.example.users.dto.UserDto;
import org.example.users.dto.UserForFilterDto;
import org.example.users.dto.UserForViewDto;
import org.example.users.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    void changePersonalInformation(PersonalInformationDto personalInformationDto);
    void save(User user);
    User getById(Long id);
    void update(UserDto userDto);
    void create(UserDto userDto);
    Page<UserForViewDto> getAll(UserForFilterDto userForFilterDto);
    void deleteById(Long id);
}