package com.pch777.teamtasks.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pch777.teamtasks.controller.specification.TaskWithDeadline;
import com.pch777.teamtasks.controller.specification.TaskWithDescription;
import com.pch777.teamtasks.controller.specification.TaskWithStatus;
import com.pch777.teamtasks.controller.specification.TaskWithTitle;
import com.pch777.teamtasks.dto.DeadlineDto;
import com.pch777.teamtasks.dto.StatusDto;
import com.pch777.teamtasks.dto.TaskCreateDto;
import com.pch777.teamtasks.dto.TaskUpdateDto;
import com.pch777.teamtasks.exception.ResourceNotFoundException;
import com.pch777.teamtasks.model.Status;
import com.pch777.teamtasks.model.Task;
import com.pch777.teamtasks.service.TaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TaskController {

	public static final Long EMPTY_ID = null;
	public static final String NOT_FOUND_TASK_MESSAGE = "Not found task with id ";
	private final TaskService taskService;

	@GetMapping("/tasks")
	public List<Task> getAllTasks(@RequestParam(required = false) String title,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) Status status,
			@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate from,
			@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate to) {
		
		Specification<Task> specification = Specification.where(new TaskWithTitle(title))
				.and(new TaskWithDescription(description))
				.and(new TaskWithStatus(status))
				.and(new TaskWithDeadline(from, to));
				
		return  taskService.getAllTasks(specification);
	}

	@GetMapping("/tasks/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable("id") long id) throws ResourceNotFoundException {
		Task task = taskService
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_TASK_MESSAGE + id));
		return ResponseEntity.ok(task);
	}

	@PostMapping("/tasks")
	public ResponseEntity<Task> createTask(@Valid @RequestBody TaskCreateDto taskDto) {

		Task task = taskService
				.createTask(new Task(EMPTY_ID, 
						taskDto.getTitle(), 
						taskDto.getDescription(), 
						taskDto.getDeadline()));
		return ResponseEntity.ok(task);
	}
	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateDto taskUpdateDto) throws ResourceNotFoundException {
		Task updatedTask = taskService
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_TASK_MESSAGE + id));
		
		updatedTask.setTitle(taskUpdateDto.getTitle());
		updatedTask.setDescription(taskUpdateDto.getDescription());
		updatedTask.setStatus(taskUpdateDto.getStatus());
		updatedTask.setDeadline(taskUpdateDto.getDeadline());
		
		taskService.updateTask(updatedTask);
		return ResponseEntity.ok(updatedTask);
	}
	
	@PatchMapping("/tasks/{id}/status") 
	public ResponseEntity<Task> changeStatus(@PathVariable Long id, @RequestBody StatusDto statusDto) throws ResourceNotFoundException {
		Task task = taskService
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_TASK_MESSAGE + id));
		task.setStatus(statusDto.getStatus());
		taskService.updateTask(task);
		return ResponseEntity.ok(task);
	}
	
	@PatchMapping("/tasks/{id}/deadline") 
	public ResponseEntity<Task> changeDeadline(@PathVariable Long id, @Valid @RequestBody DeadlineDto deadlineDto) throws ResourceNotFoundException {
		Task task = taskService
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_TASK_MESSAGE + id));
		task.setDeadline(deadlineDto.getDeadline());
		taskService.updateTask(task);
		return ResponseEntity.ok(task);
	}
	
	@DeleteMapping("tasks/{id}")
	public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable Long id) throws ResourceNotFoundException {
		Task task = taskService
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_TASK_MESSAGE + id));
		task.removeUsers();
		taskService.deleteTaskById(id);
		return ResponseEntity.noContent().build();
	}

}
