package com.boot.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.hotel.dto.UserDto;
import com.boot.hotel.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(userService.createUser(userDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
		return new ResponseEntity<UserDto>(userService.getUserById(userId), HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return new ResponseEntity<List<UserDto>>(userService.getAllUsers(), HttpStatus.OK);
	} 
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDto> updateUserById(@RequestBody UserDto userDto, @PathVariable String userId){
		return new ResponseEntity<UserDto>(userService.updateUser(userDto, userId), HttpStatus.OK);
	}
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable String userId) {
		userService.deleteUserById(userId);
		return ResponseEntity.ok("User deleted successfully...");
	}

}
