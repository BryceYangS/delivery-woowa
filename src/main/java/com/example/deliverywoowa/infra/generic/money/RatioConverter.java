package com.example.deliverywoowa.infra.generic.money;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.example.deliverywoowa.domain.generic.money.Ratio;

@Converter(autoApply = true)
public class RatioConverter implements AttributeConverter<Ratio, Double> {
    @Override
    public Double convertToDatabaseColumn(Ratio ratio) {
        return ratio.getRate();
    }

    @Override
    public Ratio convertToEntityAttribute(Double rate) {
        return Ratio.valueOf(rate);
    }
}
