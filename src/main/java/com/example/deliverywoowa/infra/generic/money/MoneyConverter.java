package com.example.deliverywoowa.infra.generic.money;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.example.deliverywoowa.domain.generic.money.Money;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Long> {

	@Override
	public Long convertToDatabaseColumn(Money money) {
		return money.getAmount().longValue();
	}

	@Override
	public Money convertToEntityAttribute(Long amount) {
		return Money.wons(amount);
	}
}
