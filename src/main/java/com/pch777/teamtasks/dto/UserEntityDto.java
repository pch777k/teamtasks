package com.pch777.teamtasks.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserEntityDto {

	@NotBlank(message = "FirstName may not be blank")
	private String firstName;
	
	@NotBlank(message = "LastName may not be blank")
	private String lastName;
	
	@Email(message = "Please provide a valid email address")
	private String email;
}
