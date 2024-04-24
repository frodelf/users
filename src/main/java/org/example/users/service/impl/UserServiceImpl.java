package org.example.users.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.users.dto.PersonalInformationDto;
import org.example.users.dto.UserDto;
import org.example.users.dto.UserForFilterDto;
import org.example.users.dto.UserForViewDto;
import org.example.users.entity.User;
import org.example.users.mapper.UserMapper;
import org.example.users.repository.UserRepository;
import org.example.users.service.UserService;
import org.example.users.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    @Transactional
    public void changePersonalInformation(PersonalInformationDto personalInformationDto) {
        save(userMapper.updateEntityFromPersonalInformationDto(getById(personalInformationDto.getId()), personalInformationDto));
    }
    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()-> {
                    return new EntityNotFoundException("User with id="+id+" not found");
                }
        );
    }
    @Override
    @Transactional
    public void update(UserDto userDto) {
        save(userMapper.updateEntityFromUserDto(getById(userDto.getId()), userDto));
    }
    @Override
    @Transactional
    public void create(UserDto userDto) {
        save(userMapper.updateEntityFromUserDto(new User(), userDto));
    }
    @Override
    public Page<UserForViewDto> getAll(UserForFilterDto userForFilterDto) {
        Specification<User> specification = new UserSpecification(userForFilterDto);
        Pageable pageable = PageRequest.of(userForFilterDto.getPage(), userForFilterDto.getPageSize(), Sort.by(Sort.Order.desc("id")));
        return userMapper.toDtoPageForView(userRepository.findAll(specification, pageable));
    }
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}