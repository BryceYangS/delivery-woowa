package com.example.deliverywoowa.domain.generic.money;

public class Ratio {
	private double rate;

	Ratio(double rate) {
		this.rate = rate;
	}

	Ratio() {
	}

	public static Ratio valueOf(double rate) {
		return new Ratio(rate);
	}

	public Money of(Money price) {
		return price.times(rate);
	}

	public double getRate() {
		return rate;
	}
}
