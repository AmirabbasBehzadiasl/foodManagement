package com.spring.foodManagement.Controllers;

import com.spring.foodManagement.Dtos.UserCreateDto;
import com.spring.foodManagement.Dtos.UserResponseDto;
import com.spring.foodManagement.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserByUserName")
    public ResponseEntity<?> getUserByUserName(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUserName(username));
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserCreateDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userDto));
    }

    @PutMapping("/updateUserByUserName")
    public ResponseEntity<?> updateUserByUserName(@RequestParam String username, @Valid @RequestBody UserCreateDto userDto) {
        return ResponseEntity.ok(userService.updateUserByUserName(username, userDto));
    }

    @DeleteMapping("/deleteUserByUserName")
    public ResponseEntity<?> deleteUserByUserName(@RequestParam String username) {
        userService.deleteUserByUserName(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User with username " + username + " deleted successfully");
    }
}
