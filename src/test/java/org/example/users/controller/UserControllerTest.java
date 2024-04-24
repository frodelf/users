package org.example.users.controller;

import org.example.users.dto.PersonalInformationDto;
import org.example.users.dto.UserDto;
import org.example.users.dto.UserForFilterDto;
import org.example.users.dto.UserForViewDto;
import org.example.users.service.UserService;
import org.example.users.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private UserValidator userValidator;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private UserController userController;
    @Test
    void changeName() {
        PersonalInformationDto personalInformationDto = mock(PersonalInformationDto.class);
        ResponseEntity<String> response = userController.changeName(personalInformationDto);
        assertEquals("Personal information changed.", response.getBody());
        verify(userService, times(1)).changePersonalInformation(personalInformationDto);
    }
    @Test
    void changeAllFields() throws MethodArgumentNotValidException {
        UserDto userDto = mock(UserDto.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<String> response = userController.changeAllFields(userDto, bindingResult);
        assertEquals("All fields changed.", response.getBody());
        verify(userValidator, times(1)).validate(userDto, bindingResult);
        verify(userService, times(1)).update(userDto);
    }
    @Test
    void changeAllFieldsWithException() {
        UserDto userDto = mock(UserDto.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(MethodArgumentNotValidException.class, () -> {
            userController.changeAllFields(userDto, bindingResult);
        });
    }
    @Test
    void create() throws MethodArgumentNotValidException {
        UserDto userDto = mock(UserDto.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<String> response = userController.create(userDto, bindingResult);
        assertEquals("User created.", response.getBody());
        verify(userValidator, times(1)).validate(userDto, bindingResult);
        verify(userService, times(1)).create(userDto);
    }
    @Test
    void createWithException() {
        UserDto userDto = mock(UserDto.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(MethodArgumentNotValidException.class, () -> {
            userController.create(userDto, bindingResult);
        });
    }
    @Test
    void getAll() {
        UserForFilterDto userForFilterDto = mock(UserForFilterDto.class);
        Page<UserForViewDto> expectedResponse = mock(Page.class);
        when(userService.getAll(userForFilterDto)).thenReturn(expectedResponse);

        ResponseEntity<Page<UserForViewDto>> response = userController.getAll(userForFilterDto);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(userService, times(1)).getAll(userForFilterDto);
    }
    @Test
    void delete() {
        ResponseEntity<String> response = userController.delete(1L);
        assertEquals("User deleted.", response.getBody());
        verify(userService, times(1)).deleteById(1L);
    }
}