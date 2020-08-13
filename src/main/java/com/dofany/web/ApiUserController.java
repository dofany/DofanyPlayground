package com.dofany.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dofany.domain.User;
import com.dofany.domain.UserRepository;

@RestController
@RequestMapping("api/users")
public class ApiUserController {
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/{userId}")
	public User show(@PathVariable Long userId) {
		return userRepository.findById(userId).get();
	}
}
