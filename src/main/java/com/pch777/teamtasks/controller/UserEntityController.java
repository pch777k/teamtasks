package com.pch777.teamtasks.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pch777.teamtasks.controller.specification.UserWithEmail;
import com.pch777.teamtasks.controller.specification.UserWithFirstName;
import com.pch777.teamtasks.controller.specification.UserWithLastName;
import com.pch777.teamtasks.dto.UserEntityDto;
import com.pch777.teamtasks.exception.ResourceNotFoundException;
import com.pch777.teamtasks.model.Task;
import com.pch777.teamtasks.model.UserEntity;
import com.pch777.teamtasks.service.TaskService;
import com.pch777.teamtasks.service.UserEntityService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserEntityController {

	public static final Long EMPTY_ID = null;
	public static final String USER_ALREADY_EXISTS_MESSAGE = "Error: User with this email already exists";
	public static final String NOT_FOUND_USER_MESSAGE = "Not found user with id ";
	public static final String NOT_FOUND_TASK_MESSAGE = "Not found task with id ";
	private final UserEntityService userService;
	private final TaskService taskService;

	@GetMapping("/users")
	public List<UserEntity> getAllUsers(
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName, 
			@RequestParam(required = false) String email) {

		Specification<UserEntity> specification = Specification
				.where(new UserWithFirstName(firstName))
				.and(new UserWithLastName(lastName))
				.and(new UserWithEmail(email));

		return userService.getAllUsers(specification);
	}

	@GetMapping("users/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
		UserEntity user = userService
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_USER_MESSAGE + id));
		return ResponseEntity.ok(user);
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntityDto userEntityDto) {
		if (userService.findByEmail(userEntityDto.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body(USER_ALREADY_EXISTS_MESSAGE);
		}
		UserEntity user = userService.createUser(new UserEntity(EMPTY_ID, 
				userEntityDto.getFirstName(),
				userEntityDto.getLastName(), 
				userEntityDto.getEmail()));
		
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/tasks/{taskId}/users/{userId}")
	public ResponseEntity<Task> assignUserToTask(@PathVariable Long taskId, @PathVariable Long userId) throws ResourceNotFoundException {
		Task task = taskService
				.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_TASK_MESSAGE + taskId));
		UserEntity user = userService
				.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_USER_MESSAGE + userId));
		task.addUser(user);
		taskService.updateTask(task);
		return new ResponseEntity<>(task, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/tasks/{taskId}/users/{userId}")
	public ResponseEntity<HttpStatus> removeUserFromTask(@PathVariable(value = "taskId") Long taskId,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
		Task task = taskService
				.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_TASK_MESSAGE + taskId));
		UserEntity user = userService
				.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_USER_MESSAGE + userId));
		task.removeUser(user);
		taskService.updateTask(task);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("users/{id}")
	public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) throws ResourceNotFoundException {
		UserEntity user = userService
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_USER_MESSAGE + id));
		user.removeTasks();
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}

}
