package com.example.deliverywoowa.domain.shop;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.domain.generic.money.Ratio;

import lombok.Builder;
import lombok.Getter;

@Table(name = "SHOPS")
@Entity
@Getter
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SHOP_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "OPEN")
	private boolean open;

	@Column(name = "MIN_ORDER_AMOUNT")
	private Money minOrderAmount;

	@Column(name = "COMMISSION_RATE")
	private Ratio commissionRate;

	@Column(name = "COMMISSION")
	private Money commission = Money.ZERO;

	// [조회 전용 양뱡향 연관관계]
	/**
	 * MenuBoard 생성을 위한 연관관계
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	private List<Menu> menus = new ArrayList<>();

	public Shop(String name, boolean open, Money minOrderAmount) {
		this(name, open, minOrderAmount, Ratio.valueOf(0), Money.ZERO);
	}

	public Shop(String name, boolean open, Money minOrderAmount, Ratio commissionRate, Money commission) {
		this(null, name, open, minOrderAmount, commissionRate, commission);
	}

	@Builder
	public Shop(Long id, String name, boolean open, Money minOrderAmount, Ratio commissionRate, Money commission) {
		this.id = id;
		this.name = name;
		this.open = open;
		this.minOrderAmount = minOrderAmount;
		this.commissionRate = commissionRate;
		this.commission = commission;
	}

	protected Shop() {}

	public void addMenu(Menu menu) {
		menus.add(menu);
	}

	public boolean isValidOrderAmount(Money amount) {
		return amount.isGreaterThanOrEqual(minOrderAmount);
	}

	public void open() {
		this.open = true;
	}

	public void close() {
		this.open = false;
	}

	public void modifyCommissionRate(Ratio commissionRate) {
		this.commissionRate = commissionRate;
	}

	public void billCommissionFee(Money price) {
		commission = commission.plus(commissionRate.of(price));
	}
}