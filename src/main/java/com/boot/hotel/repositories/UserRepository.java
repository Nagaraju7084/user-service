package com.boot.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.hotel.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
