package com.pch777.teamtasks.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pch777.teamtasks.model.Status;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskUpdateDto {

	@NotBlank(message = "Title may not be blank")
	@Size(max=70, message = "Title cannot be longer than 70 characters")
	private String title;
	
	@NotBlank(message = "Description may not be blank")
	@Size(max=250, message = "Description cannot be longer than 255 characters")
	private String description;
	
	@NotNull(message = "Status must not be null, please provide a correct status (TO_DO, DOING, DONE)")
	private Status status;
	
	@Future(message = "The deadline date should be from the future")
	private LocalDate deadline;
	
}
