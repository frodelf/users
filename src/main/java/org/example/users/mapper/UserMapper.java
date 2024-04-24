package org.example.users.mapper;

import org.example.users.dto.PersonalInformationDto;
import org.example.users.dto.UserDto;
import org.example.users.dto.UserForViewDto;
import org.example.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User updateEntityFromPersonalInformationDto(User user, PersonalInformationDto personalInformationDto) {
        user.setFirstName(personalInformationDto.getFirstName());
        user.setLastName(personalInformationDto.getLastName());
        return user;
    }
    public User updateEntityFromUserDto(User user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthdate(userDto.getBirthdate());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return user;
    }
    public UserForViewDto toDtoForView(User user) {
        UserForViewDto userForViewDto = new UserForViewDto();
        userForViewDto.setEmail(user.getEmail());
        userForViewDto.setFirstName(user.getFirstName());
        userForViewDto.setLastName(user.getLastName());
        userForViewDto.setBirthdate(user.getBirthdate());
        userForViewDto.setAddress(user.getAddress());
        userForViewDto.setPhoneNumber(user.getPhoneNumber());
        return userForViewDto;
    }
    public Page<UserForViewDto> toDtoPageForView(Page<User> users){
        return new PageImpl<>(users.getContent().stream()
                .map(this::toDtoForView)
                .collect(Collectors.toList()), users.getPageable(), users.getTotalElements());
    }
}