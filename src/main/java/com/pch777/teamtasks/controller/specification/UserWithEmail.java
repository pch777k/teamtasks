package com.pch777.teamtasks.controller.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pch777.teamtasks.model.UserEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserWithEmail implements Specification<UserEntity> {

	private static final long serialVersionUID = -6142127674926498987L;
	
	private String email;

	public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (email == null) {
			return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
		}		
		return criteriaBuilder.like(root.get("email"), "%" + this.email + "%");
	}
}
