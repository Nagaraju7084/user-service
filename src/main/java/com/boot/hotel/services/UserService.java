package com.boot.hotel.services;

import java.util.List;

import com.boot.hotel.dto.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, String userId);
	List<UserDto> getAllUsers();
	UserDto getUserById(String userId);
	void deleteUserById(String userId);
	
}
