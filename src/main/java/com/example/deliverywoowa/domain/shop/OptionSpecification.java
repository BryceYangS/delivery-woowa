package com.example.deliverywoowa.domain.shop;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.infra.generic.money.MoneyConverter;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "OPTION_SPECS")
@Getter
public class OptionSpecification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPTION_SPEC_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PRICE")
	@Convert(converter = MoneyConverter.class)
	private Money price;

	public OptionSpecification(String name, Money price) {
		this(null, name, price);
	}

	@Builder
	public OptionSpecification(Long id, String name, Money price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	protected OptionSpecification(){}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OptionSpecification that = (OptionSpecification)o;
		return Objects.equals(name, that.getName()) && Objects.equals(price, that.getPrice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, price);
	}
}
