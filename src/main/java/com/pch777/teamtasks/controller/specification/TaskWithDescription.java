package com.pch777.teamtasks.controller.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pch777.teamtasks.model.Task;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskWithDescription implements Specification<Task> {

	private static final long serialVersionUID = -1019856454732050179L;
	
	private String description;
	
	@Override
	public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (description == null) {
			return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); 
		}		
		return criteriaBuilder.like(root.get("description"), "%" + this.description + "%");
	}

}
