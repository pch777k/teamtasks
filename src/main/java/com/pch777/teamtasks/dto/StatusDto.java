package com.pch777.teamtasks.dto;

import javax.validation.constraints.NotNull;

import com.pch777.teamtasks.model.Status;

import lombok.Getter;

@Getter
public class StatusDto {

	@NotNull
	private Status status;
}
