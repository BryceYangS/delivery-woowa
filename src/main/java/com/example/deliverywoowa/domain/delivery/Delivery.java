package com.example.deliverywoowa.domain.delivery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.order.Order;

import lombok.Getter;

@Entity
@Table(name = "DELIVERIES")
@Getter
public class Delivery {
	enum DeliveryStatus { DELIVERING, DELIVERED;}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DELIVERY_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private DeliveryStatus deliveryStatus;

	public static Delivery started(Order order) {
		return new Delivery(order, DeliveryStatus.DELIVERING);
	}

	public Delivery(Order order, DeliveryStatus deliveryStatus) {
		this.order = order;
		this.deliveryStatus = deliveryStatus;
	}

	protected Delivery(){}

	public void completed() {
		this.deliveryStatus = DeliveryStatus.DELIVERED;
	}
}
