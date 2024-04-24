package org.example.users.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.users.dto.PersonalInformationDto;
import org.example.users.dto.UserDto;
import org.example.users.dto.UserForFilterDto;
import org.example.users.dto.UserForViewDto;
import org.example.users.service.UserService;
import org.example.users.validator.UserValidator;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    @PostMapping("/change-personal-information")
    public ResponseEntity<String> changeName(@RequestBody @Valid PersonalInformationDto personalInformationDto) {
        userService.changePersonalInformation(personalInformationDto);
        return ResponseEntity.ok("Personal information changed.");
    }
    @PutMapping("/change-all-fields")
    public ResponseEntity<String> changeAllFields(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) throws MethodArgumentNotValidException {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        userService.update(userDto);
        return ResponseEntity.ok("All fields changed.");
    }
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) throws MethodArgumentNotValidException {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        userService.create(userDto);
        return ResponseEntity.ok("User created.");
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<UserForViewDto>> getAll(@RequestBody UserForFilterDto userForFilterDto){
        return ResponseEntity.ok(userService.getAll(userForFilterDto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id){
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted.");
    }
}