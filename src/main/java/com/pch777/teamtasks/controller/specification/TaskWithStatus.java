package com.pch777.teamtasks.controller.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pch777.teamtasks.model.Status;
import com.pch777.teamtasks.model.Task;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskWithStatus implements Specification<Task> {

	private static final long serialVersionUID = 5802992440382391214L;
	
	private Status status;
	
	@Override
	public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (status == null) {
			return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); 
		}		
		return criteriaBuilder.equal(root.get("status"), this.status);
	}

}
