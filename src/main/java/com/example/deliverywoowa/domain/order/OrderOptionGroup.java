package com.example.deliverywoowa.domain.order;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.domain.shop.OptionGroup;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "ORDER_OPTION_GROUPS")
@Getter
public class OrderOptionGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_OPTION_GROUP_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@ElementCollection
	@CollectionTable(name = "ORDER_OPTIONS", joinColumns = @JoinColumn(name = "ORDER_OPTION_GROUP_ID"))
	private List<OrderOption> orderOptions;

	public OrderOptionGroup(String name, List<OrderOption> options) {
		this(null, name, options);
	}

	@Builder
	public OrderOptionGroup(Long id, String name, List<OrderOption> options) {
		this.id = id;
		this.name = name;
		this.orderOptions = options;
	}

	protected OrderOptionGroup(){}

	public Money calculatePrice() {
		return Money.sum(orderOptions, OrderOption::getPrice);
	}

	public OptionGroup convertToOptionGroup() {
		return new OptionGroup(name, orderOptions.stream().map(OrderOption::convertToOption).collect(toList()));
	}
}
