package com.pch777.teamtasks.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskCreateDto {

	@NotBlank(message = "Title may not be blank")
	@Size(max=70, message = "Title cannot be longer than 70 characters")
	private String title;
	
	@NotBlank(message = "Description may not be blank")
	@Size(max=250, message = "Description cannot be longer than 255 characters")
	private String description;
	
	@Future(message = "The deadline date should be from the future")
	private LocalDate deadline;
	
}
