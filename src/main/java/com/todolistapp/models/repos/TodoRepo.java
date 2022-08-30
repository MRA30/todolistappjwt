package com.todolistapp.models.repos;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.todolistapp.models.entity.Todo;

public interface TodoRepo extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo>{
    
    @Query(value ="SELECT * FROM tbl_todo WHERE user_id = :userId", nativeQuery = true)
    public List<Todo> findTodoByUserId(@PathParam("userId") long userId);

    public Page<Todo> findAll(Specification<Todo> and, Pageable pageable);

}
