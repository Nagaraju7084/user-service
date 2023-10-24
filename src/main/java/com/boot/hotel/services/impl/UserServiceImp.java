package com.boot.hotel.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.boot.hotel.dto.UserDto;
import com.boot.hotel.entities.User;
import com.boot.hotel.exception.UserNotFoundException;
import com.boot.hotel.external.services.HotelService;
import com.boot.hotel.pojo.Hotel;
import com.boot.hotel.pojo.Rating;
import com.boot.hotel.repositories.UserRepository;
import com.boot.hotel.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImp implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
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
				()-> new UserNotFoundException("User","id",userId)
				);
		BeanUtils.copyProperties(userDto, dbUser);
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
		//get user from db with the help of repository
		User user = userRepository.findById(userId).orElseThrow(
				()-> new UserNotFoundException("User", "id", userId));
		
		//fetch rating of the above user from RATING-SERVICE
		//http://localhost:8083/api/users/e23624a2-f17c-4657-a2bf-91cd870d1992
		Rating[] ratingsByUserId = restTemplate.getForObject("http://RATING-SERVICE/api/users/"+user.getUserId(), Rating[].class);
		logger.info("{} "+ratingsByUserId);
		
		List<Rating> ratingList = Arrays.stream(ratingsByUserId).map(rating -> {
			//api call to HOTEL SERVICE to get the hotel
			//http://HOTEL-SERVICE/api/hotel/a7b1be95-7f92-4fcc-b912-5ea44c5eef3c
			//ResponseEntity<Hotel> hotelsDetails = restTemplate.getForEntity("http://HOTEL-SERVICE/api/hotel/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			//logger.info("response status code: {} ", hotelsDetails.getStatusCode());
			//set the hotel to rating
			rating.setHotel(hotel);
			//return the rating
			return rating;
		}).collect(Collectors.toList());
		
		
		user.setRatings(ratingList);
		return entityToDto(user);
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
