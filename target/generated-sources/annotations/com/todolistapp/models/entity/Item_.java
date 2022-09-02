package com.todolistapp.models.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Item.class)
public abstract class Item_ {

	public static volatile SingularAttribute<Item, Image> image;
	public static volatile SingularAttribute<Item, String> item;
	public static volatile SingularAttribute<Item, Boolean> cek;
	public static volatile SingularAttribute<Item, Long> id;
	public static volatile SingularAttribute<Item, Long> todoId;

	public static final String IMAGE = "image";
	public static final String ITEM = "item";
	public static final String CEK = "cek";
	public static final String ID = "id";
	public static final String TODO_ID = "todoId";

}

