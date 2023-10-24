package com.boot.hotel.dto;

import java.util.List;

import com.boot.hotel.pojo.Rating;

import lombok.Data;

@Data
public class UserDto {

	private String userId;
	private String userName;
	private String email;
	private String about;
	
	private List<Rating> ratings;
	
}
