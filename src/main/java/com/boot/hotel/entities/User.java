package com.boot.hotel.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.boot.hotel.pojo.Rating;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	private String userId;
	private String userName;
	private String email;
	private String about;
	
	@Transient
	private List<Rating> ratings = new ArrayList<Rating>();
	

}
