package com.digdes.simple.dao;

import com.digdes.simple.dto.EmployeeDTO;
import com.digdes.simple.model.EmployeeModel;
import com.digdes.simple.model.EmployeeStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<EmployeeModel> getFilteres (EmployeeDTO employeeDTO) {
        return ((root, query, criteriaBuilder ) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(employeeDTO.getId()))
                predicates.add(criteriaBuilder.equal(root.get("id"), employeeDTO.getId()));
            if(!ObjectUtils.isEmpty(employeeDTO.getUid()))
                predicates.add(criteriaBuilder.equal(root.get("uid"), employeeDTO.getUid()));
            if(!ObjectUtils.isEmpty(employeeDTO.getFirstname()))
                predicates.add(criteriaBuilder.equal(root.get("firstName"), employeeDTO.getFirstname()));
            if(!ObjectUtils.isEmpty(employeeDTO.getLastname()))
                predicates.add(criteriaBuilder.equal(root.get("lastName"), employeeDTO.getLastname()));
            if(!ObjectUtils.isEmpty(employeeDTO.getSurname()))
                predicates.add(criteriaBuilder.equal(root.get("surName"), employeeDTO.getSurname()));
            if(!ObjectUtils.isEmpty(employeeDTO.getPosition()))
                predicates.add(criteriaBuilder.equal(root.get("position"), employeeDTO.getPosition()));
            if(!ObjectUtils.isEmpty(employeeDTO.getAccount()))
                predicates.add(criteriaBuilder.equal(root.get("account"), employeeDTO.getAccount()));
            if(!ObjectUtils.isEmpty(employeeDTO.getEmail()))
                predicates.add(criteriaBuilder.equal(root.get("eMail"), employeeDTO.getEmail()));
            if(!ObjectUtils.isEmpty(employeeDTO.getStatus()))
                predicates.add(criteriaBuilder.equal(root.get("status"),
                        employeeDTO.getStatus().equals("ACTIVE") ? EmployeeStatus.ACTIVE : EmployeeStatus.DELETED));
            if (CollectionUtils.isEmpty(predicates))
                return query.where().getRestriction();
            else
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });
    }
}
