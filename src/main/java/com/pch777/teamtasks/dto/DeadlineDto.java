package com.pch777.teamtasks.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;

import lombok.Getter;

@Getter
public class DeadlineDto {
	
	@Future
	private LocalDate deadline;
	
}
