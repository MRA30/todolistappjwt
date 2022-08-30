package com.todolistapp.models.repos;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todolistapp.models.entity.Item;

public interface ItemRepo extends JpaRepository<Item, Long>  {
    @Query(value ="SELECT * FROM tbl_items WHERE todo_id = :todoId", nativeQuery= true)
    public Page<Item> findItemByTodo(@PathParam("todoId") Long todoId, Pageable pageable);
}

