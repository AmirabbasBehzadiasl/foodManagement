package com.spring.foodManagement.Services;

import com.spring.foodManagement.Dtos.UserCreateDto;
import com.spring.foodManagement.Dtos.UserResponseDto;
import com.spring.foodManagement.Exceptions.AlreadyExistException;
import com.spring.foodManagement.Exceptions.NotFoundException;
import com.spring.foodManagement.Mapper.UserMapper;
import com.spring.foodManagement.Models.User;
import com.spring.foodManagement.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponseDto getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("No user found with userName: " + userName));
        return userMapper.toDto(user);
    }

    public UserResponseDto addUser(UserCreateDto userDto) {
        System.err.println(userDto+"in ser 1");
        userRepository.findByUserName(userDto.getUserName())
                .ifPresent(existing -> {
                    throw new AlreadyExistException("User with userName " + userDto.getUserName() + " already exists");
                });
        System.err.println(userDto+"in ser 2");
        System.err.println(userDto.getEmail());
        System.err.println(userDto.getUserName());
        return userMapper.toDto(userRepository.save(userMapper.toModel(userDto)));
    }

    public void updateUserByUserName(String userName, UserCreateDto userDto) {
        userRepository.findByUserName(userName)
                .ifPresentOrElse(
                        existingUser -> {
                            userMapper.updateUserFromDto(userDto, existingUser);
                            userRepository.save(existingUser);
                        },
                        () -> {
                            throw new NotFoundException("User with userName " + userName + " not found");
                        }
                );
    }

    public void deleteUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("User with userName " + userName + " not found"));
        userRepository.delete(user);
    }
}
