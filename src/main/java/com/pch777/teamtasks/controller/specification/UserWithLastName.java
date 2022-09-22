package com.pch777.teamtasks.controller.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pch777.teamtasks.model.UserEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserWithLastName implements Specification<UserEntity> {

	private static final long serialVersionUID = 3384222894098725327L;
	
	private String lastName;

	public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (lastName == null) {
			return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); 
		}		
		return criteriaBuilder.like(root.get("lastName"), "%" + this.lastName + "%");
	}
}
