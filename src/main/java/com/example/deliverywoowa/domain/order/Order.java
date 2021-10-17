package com.example.deliverywoowa.domain.order;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.domain.shop.Shop;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "ORDERS")
@Getter
public class Order {

	public enum OrderStatus { ORDERED, PAYED, DELIVERED }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long id;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "SHOP_ID")
	private Long shopId;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID")
	private List<OrderLineItem> orderLineItems = new ArrayList<>();

	@Column(name = "ORDERED_TIME")
	private LocalDateTime orderedTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private OrderStatus orderStatus;

	public Order(Long userId, Long shopId, List<OrderLineItem> items) {
		this(userId, shopId, items, LocalDateTime.now(), null);
	}

	@Builder
	public Order(Long userId, Long shopId, List<OrderLineItem> items, LocalDateTime orderedTime,
		OrderStatus status) {
		this.userId = userId;
		this.shopId = shopId;
		this.orderedTime = orderedTime;
		this.orderStatus = status;
		this.orderLineItems.addAll(items);
	}

	protected Order() {
	}

	// 주문하기
	public void place(OrderValidator orderValidator){
		orderValidator.validate(this);
		ordered();
	}

	private void ordered() {
		this.orderStatus = OrderStatus.ORDERED;
	}

	public void payed() {
		this.orderStatus = OrderStatus.PAYED;
	}

	public void delivered() {
		this.orderStatus = OrderStatus.DELIVERED;
	}

	Money calculateTotalPrice() {
		return Money.sum(orderLineItems, OrderLineItem::calculatePrice);
	}

	public List<Long> getMenuIds() {
		return orderLineItems.stream().map(OrderLineItem::getMenuId).collect(toList());
	}
}
