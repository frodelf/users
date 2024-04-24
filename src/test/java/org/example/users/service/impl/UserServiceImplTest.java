package org.example.users.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.users.dto.PersonalInformationDto;
import org.example.users.dto.UserDto;
import org.example.users.dto.UserForFilterDto;
import org.example.users.dto.UserForViewDto;
import org.example.users.entity.User;
import org.example.users.mapper.UserMapper;
import org.example.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl userService;
    @Test
    void existsByPhoneNumber() {
        String phoneNumber = "1234567890";
        when(userRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);
        boolean result = userService.existsByPhoneNumber(phoneNumber);
        assertTrue(result);
    }
    @Test
    void existsByEmail() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        boolean result = userService.existsByEmail(email);
        assertFalse(result);
    }
    @Test
    void changePersonalInformation() {
        PersonalInformationDto personalInformationDto = mock(PersonalInformationDto.class);
        User user = new User();
        when(userRepository.findById(personalInformationDto.getId())).thenReturn(Optional.of(user));
        when(userMapper.updateEntityFromPersonalInformationDto(user, personalInformationDto)).thenReturn(user);
        userService.changePersonalInformation(personalInformationDto);
        verify(userMapper, times(1)).updateEntityFromPersonalInformationDto(user, personalInformationDto);
        verify(userRepository, times(1)).findById(personalInformationDto.getId());
    }
    @Test
    void save() {
        User user = new User();

        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }
    @Test
    void getById() {
        Long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        User result = userService.getById(id);
        assertNotNull(result);
        assertEquals(user, result);
    }
    @Test
    void getByIdWithException() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.getById(id);
        });
    }
    @Test
    void update() {
        UserDto userDto = new UserDto();
        User user = new User();
        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(user));
        when(userMapper.updateEntityFromUserDto(user, userDto)).thenReturn(user);

        userService.update(userDto);

        verify(userRepository, times(1)).save(user);
    }
    @Test
    void create() {
        UserDto userDto = new UserDto();
        User user = new User();
        when(userMapper.updateEntityFromUserDto(any(User.class), eq(userDto))).thenReturn(user);

        userService.create(userDto);

        verify(userRepository, times(1)).save(user);
    }
    @Test
    void getAll() {
        UserForFilterDto userForFilterDto = new UserForFilterDto();
        userForFilterDto.setPage(1);
        userForFilterDto.setPageSize(10);
        Page<User> userPage = mock(Page.class);
        Page<UserForViewDto> extResult = mock(Page.class);
        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(userPage);
        when(userMapper.toDtoPageForView(any())).thenReturn(extResult);
        Page<UserForViewDto> result = userService.getAll(userForFilterDto);

        assertNotNull(result);
        assertEquals(extResult, result);
    }
    @Test
    void deleteById() {
        Long id = 1L;

        userService.deleteById(id);

        verify(userRepository, times(1)).deleteById(id);
    }
}