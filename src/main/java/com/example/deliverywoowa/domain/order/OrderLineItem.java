package com.example.deliverywoowa.domain.order;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.domain.shop.Menu;
import com.example.deliverywoowa.domain.shop.OptionGroup;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "ORDER_LINE_ITEMS")
@Getter
public class OrderLineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_LINE_ITEM_ID")
	private Long id;

	@Column(name = "MENU_ID")
	private Long menuId;

	@Column(name = "FOOD_NAME")
	private String name;

	@Column(name = "FOOD_COUNT")
	private int count;

	@OneToMany
	@JoinColumn(name = "ORDER_LINE_ITEM_ID")
	private List<OrderOptionGroup> groups = new ArrayList<>();

	public OrderLineItem(Menu menu, String name, int count, List<OrderOptionGroup> groups) {
		this(null, menu, name, count, groups);
	}

	@Builder
	public OrderLineItem(Long id, Long menuId, String name, int count, List<OrderOptionGroup> groups) {
		this.id = id;
		this.menuId = menuId;
		this.name = name;
		this.count = count;
		this.groups.addAll(groups);
	}

	protected OrderLineItem(){}

	public Money calculatePrice() {
		return Money.sum(groups, OrderOptionGroup::calculatePrice).times(count);
	}

	private List<OptionGroup> convertToOptionGroups() {
		return groups.stream().map(OrderOptionGroup::convertToOptionGroup).collect(toList());
	}
}
