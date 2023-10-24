package com.boot.hotel.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.UserDto;
import com.boot.hotel.entities.User;
import com.boot.hotel.exception.UserNotFoundException;
import com.boot.hotel.repositories.UserRepository;
import com.boot.hotel.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImp implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {	
		//generate unique userid
		String randomUserId = UUID.randomUUID().toString();
		userDto.setUserId(randomUserId);
		return entityToDto(userRepository.save(dtoToEntity(userDto)));
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User dbUser = userRepository.findById(userId).orElseThrow(
				()-> new UserNotFoundException("User with given id is not found on server" +userId)
				);
		//BeanUtils.copyProperties(userDto, dbUser);
		dbUser.setUserName(userDto.getUserName());
		dbUser.setEmail(userDto.getEmail());
		dbUser.setAbout(userDto.getAbout());
		User updatedUser = userRepository.save(dbUser);
		return entityToDto(updatedUser);
	}

	@Override
	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(
				user-> entityToDto(user)).collect(Collectors.toList());
	}

	@Override
	public UserDto getUserById(String userId) {
			return entityToDto(userRepository.findById(userId).orElseThrow(
					() -> new UserNotFoundException("User with given id is not found on server" +userId)
					));
	}

	@Override
	public void deleteUserById(String userId) {
		userRepository.deleteById(userId);
	}
	
	private User dtoToEntity(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		return user;
	}
	
	private UserDto entityToDto(User user) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

}
