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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.deliverywoowa.domain.generic.money.Money;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "MENUS")
@Getter
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MENU_ID")
	private Long id;

	@Column(name = "FOOD_NAME")
	private String name;

	@Column(name = "FOOD_DESCRIPTION")
	private String description;

	@OneToOne
	@JoinColumn(name = "MENU_ID")
	private Shop shop;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	private List<OptionGroupSpecification> optionGroupSpecs = new ArrayList<>();

	@Builder
	public Menu(Long id, String name, String description, Shop shop, OptionGroupSpecification basic, List<OptionGroupSpecification> additives) {
		this.id = id;
		this.name = name;
		this.shop = shop;
		this.description = description;

		this.shop.addMenu(this);
		this.optionGroupSpecs.add(basic);
		this.optionGroupSpecs.addAll(additives);
	}

	protected Menu(){}

	public void validateOrder(String menuName, List<OptionGroup> optionGroups) {
		if (!this.name.equals(menuName)) {
			throw new IllegalArgumentException("기본 상품이 변경됐습니다.");
		}

		if (!isSatisfiedBy(optionGroups)) {
			throw new IllegalArgumentException("메뉴가 변경됐습니다.");
		}
	}

	private boolean isSatisfiedBy(List<OptionGroup> cartOptionGroups) {
		return cartOptionGroups.stream().anyMatch(this::isSatisfiedBy);
	}

	private boolean isSatisfiedBy(OptionGroup group) {
		return optionGroupSpecs.stream().anyMatch(spec -> spec.isSatisfiedBy(group));
	}

	public Money getBasePrice() {
		return getBasicOptionGroupSpecs().getOptionSpecs().get(0).getPrice();
	}

	private OptionGroupSpecification getBasicOptionGroupSpecs() {
		return optionGroupSpecs
			.stream()
			.filter(OptionGroupSpecification::isBasic)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
