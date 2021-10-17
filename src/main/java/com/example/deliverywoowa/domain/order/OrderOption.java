package com.example.deliverywoowa.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.domain.shop.Option;

import lombok.Builder;
import lombok.Getter;

@Embeddable
@Getter
public class OrderOption {
	@Column(name = "NAME")
	private String name;

	@Column(name = "PRICE")
	private Money price;

	@Builder
	public OrderOption(String name, Money price) {
		this.name = name;
		this.price = price;
	}

	protected OrderOption(){}

	public Option convertToOption() {
		return new Option(name, price);
	}
}
