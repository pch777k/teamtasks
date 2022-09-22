package com.pch777.teamtasks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pch777.teamtasks.model.Task;
import com.pch777.teamtasks.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskService {

	private final TaskRepository taskRepository;

	public List<Task> getAllTasks(Specification<Task> specification) {
		return taskRepository.findAll(specification);
	}
	
	public Optional<Task> findById(long id) {
		return taskRepository.findById(id);
	}

	@Transactional
	public Task createTask(Task task) {
		return taskRepository.save(task);
	}

	public Task updateTask(Task task) {
		return taskRepository.save(task);
	}

	public void deleteTaskById(Long id) {
		taskRepository.deleteById(id);
	}
}
