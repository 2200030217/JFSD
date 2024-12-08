package com.example.SdpProject.repository;

import com.example.SdpProject.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

	



	public interface UserRepository extends JpaRepository<User, Long> {
	    boolean existsByUsername(String username);
	    boolean existsByEmail(String email);
	    Optional<User> findByEmail(String email);
	}



