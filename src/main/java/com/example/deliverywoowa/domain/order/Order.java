package com.example.deliverywoowa.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.domain.shop.Shop;

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

	@ManyToOne
	@JoinColumn(name = "SHOP_ID")
	private Shop shop;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID")
	private List<OrderLineItem> orderLineItems = new ArrayList<>();

	@Column(name = "ORDERED_TIME")
	private LocalDateTime orderedTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private OrderStatus orderStatus;

	public Order(Long userId, Shop shop, List<OrderLineItem> orderLineItems, LocalDateTime orderedTime,
		OrderStatus orderStatus) {
		this.userId = userId;
		this.shop = shop;
		this.orderedTime = orderedTime;
		this.orderStatus = orderStatus;
		this.orderLineItems.addAll(orderLineItems);
	}

	protected Order() {
	}

	public void place(){
		validate();
		ordered();
	}

	private void validate() {
		if (orderLineItems.isEmpty()) {
			throw new IllegalStateException("주문 항목이 비어 있습니다.");
		}

		if (!shop.isOpen()) {
			throw new IllegalArgumentException("가게가 영업중이 아닙니다.");
		}

		if (!shop.isValidOrderAmount(calculateTotalPrice())) {
			throw new IllegalStateException(String.format("최소 주문 금액 %s 이상을 주문해주세요.", shop.getMinOrderAmount()));
		}

		for (OrderLineItem orderLineItem : orderLineItems) {
			orderLineItem.validate();
		}
	}

	private void ordered() {
		this.orderStatus = OrderStatus.ORDERED;
	}

	private Money calculateTotalPrice() {
		return Money.sum(orderLineItems, OrderLineItem::calculatePrice);
	}

}