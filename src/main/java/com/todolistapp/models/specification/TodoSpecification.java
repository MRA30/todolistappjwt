package com.todolistapp.models.specification;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.todolistapp.models.entity.Todo;
import com.todolistapp.models.entity.Todo_;
import com.todolistapp.models.repos.TodoRepo;

@Component
public class TodoSpecification {

    @Autowired
    private TodoRepo todoRepo;
    
    
    public Specification<Todo> filterByUserId(long userId){
        return ((root, criteriaQuery, criteriaBuilder) ->{
            return criteriaBuilder.equal(root.get(Todo_.USER_ID), userId);
        });
    }

    public Specification<Todo> searchTodoName(String todoName){
        return ((root, criteriaQuery, criteriaBuilder) ->{
            return criteriaBuilder.like(root.get(Todo_.TODO), "%" + todoName + "%");
        }); 
    }

    public Specification<List<Todo>> filterAndSearchTodo(long userId, String todoName){
        return ((root, criteriaQuery, criteriaBuilder) ->{
            return (Predicate) todoRepo.findAll((filterByUserId(userId).and(searchTodoName(todoName))));
        });
    }
}
