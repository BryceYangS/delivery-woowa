package com.example.deliverywoowa.domain.shop;

import com.example.deliverywoowa.domain.generic.money.Money;

import lombok.Builder;
import lombok.Data;

@Data
public class Option {
	private String name;
	private Money price;

	@Builder
	public Option(String name, Money price) {
		this.name = name;
		this.price = price;
	}
}
