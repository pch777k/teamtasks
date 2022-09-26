package com.pch777.teamtasks;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pch777.teamtasks.controller.TaskController;
import com.pch777.teamtasks.model.Task;
import com.pch777.teamtasks.service.TaskService;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

	@MockBean
	private TaskService taskService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldCreateTask() throws Exception {
		Task task = new Task(1L, "Title 1", "Description 1", LocalDate.now().plusDays(14));

		mockMvc.perform(
				post("/tasks").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(task)))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldReturnTask() throws Exception {
		long id = 1L;
		Task task = new Task(id, "Title 1", "Description 1 ", LocalDate.now().plusDays(14));

		when(taskService.findById(id)).thenReturn(Optional.of(task));
		mockMvc.perform(get("/tasks/{id}", id))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.title").value(task.getTitle()))
				.andExpect(jsonPath("$.description").value(task.getDescription()))
				.andExpect(jsonPath("$.status").value(task.getStatus().toString()))
				.andExpect(jsonPath("$.deadline").value(task.getDeadline().toString()))
				.andDo(print());
	}

}
