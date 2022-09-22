package com.pch777.teamtasks.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	@Enumerated(EnumType.STRING)
	private Status status = Status.TO_DO;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate deadline;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable
	@JsonIgnoreProperties("tasks")
	private Set<UserEntity> users = new HashSet<>();

	public void addUser(UserEntity user) {
		users.add(user);
		user.getTasks().add(this);
	}

	public void removeUser(UserEntity user) {
		users.remove(user);
		user.getTasks().remove(this);
	}

	public void removeUsers() {
		Task self = this;
		users.forEach(user -> user.getTasks().remove(self));
		users.clear();
	}

	public Task(Long id, String title, String description, LocalDate deadline) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = deadline;
	}
	
	

}
