package com.todolistapp.models.specification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.todolistapp.models.entity.Todo;
import com.todolistapp.models.entity.Todo_;

@Component
public class TodoSpecification {
    
    public static Specification<Todo> filterByUserId(long userId){
        return ((root, criteriaQuery, criteriaBuilder) ->{
            return criteriaBuilder.equal(root.get(Todo_.USER_ID), userId);
        });
    }

    public static Specification<Todo> searchTodoName(String todoName){
        return ((root, criteriaQuery, criteriaBuilder) ->{
            return criteriaBuilder.like(root.get(Todo_.TODO), "%" + todoName + "%");
        }); 
    }

}
