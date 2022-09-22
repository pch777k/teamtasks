package com.pch777.teamtasks.controller.specification;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pch777.teamtasks.model.Task;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskWithDeadline implements Specification<Task> {
	
	private static final long serialVersionUID = -4520748720938449976L;
	private static final String DEADLINE = "deadline";
	private LocalDate from;
	private LocalDate to;
	
	@Override
	public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		if (from == null && to == null) {
			return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); 
		} else if (from != null && to == null) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get(DEADLINE), from); 
		} else if (from == null) {
			return criteriaBuilder.lessThanOrEqualTo(root.get(DEADLINE), to); 
		}
		
		return criteriaBuilder.between(root.get(DEADLINE), from, to); 
	}

}