package com.example.deliverywoowa.domain.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.shop.Menu;

import lombok.Getter;

@Entity
@Table(name = "ORDER_LINE_ITEMS")
@Getter
public class OrderLineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_LINE_ITEM_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "MENU_ID")
	private Menu menu;

	@Column(name = "FOOD_NAME")
	private String name;

	@Column(name = "FOOD_COUNT")
	private int count;


}
