package org.example.users.validator;

import lombok.RequiredArgsConstructor;
import org.example.users.dto.UserDto;
import org.example.users.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;
    @Value("${maximum-user-age}")
    private int maximumUserAge;
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        UserDto user = (UserDto) target;
        if (user.getId() == null) {
            if (userService.existsByPhoneNumber(user.getPhoneNumber()))
                errors.rejectValue("phone", "", "The phone is already in use.");
            if (userService.existsByEmail(user.getEmail()))
                errors.rejectValue("email", "", "The email is already in use.");
        }
        if(user.getBirthdate()!=null){
            if (Period.between(user.getBirthdate(), LocalDate.now()).getYears() < maximumUserAge) {
                errors.rejectValue("birthdate", "", "User must be over 18 years old.");
            }
        }
    }
}
