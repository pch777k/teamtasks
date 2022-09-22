package com.pch777.teamtasks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pch777.teamtasks.model.UserEntity;
import com.pch777.teamtasks.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserEntityService {

	private final UserEntityRepository userRepository;
	
	public List<UserEntity> getAllUsers(Specification<UserEntity> specification) {
		return userRepository.findAll(specification);
	}
	
	@Transactional
	public UserEntity createUser(UserEntity userEntity) {
		return userRepository.save(userEntity);	
	}
	
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	public Optional<UserEntity> findById(Long userId) {
		return userRepository.findById(userId);
	}
	
	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
