package com.todolistapp.models.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Todo.class)
public abstract class Todo_ {

	public static volatile SingularAttribute<Todo, String> todo;
	public static volatile SingularAttribute<Todo, Image> image;
	public static volatile SingularAttribute<Todo, Long> id;
	public static volatile SingularAttribute<Todo, Long> userId;
	public static volatile ListAttribute<Todo, Item> items;

	public static final String TODO = "todo";
	public static final String IMAGE = "image";
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String ITEMS = "items";

}

