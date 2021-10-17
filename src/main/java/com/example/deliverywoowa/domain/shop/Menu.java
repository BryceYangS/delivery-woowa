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

	@Column(name = "SHOP_ID")
	private Long shopId;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	private List<OptionGroupSpecification> optionGroupSpecs = new ArrayList<>();

	@Builder
	public Menu(Long id, String name, String description, Long shopId, OptionGroupSpecification basic, List<OptionGroupSpecification> additives) {
		this.id = id;
		this.name = name;
		this.shopId = shopId;
		this.description = description;

		this.shop.addMenu(this);
		this.optionGroupSpecs.add(basic);
		this.optionGroupSpecs.addAll(additives);
	}

	protected Menu(){}

	public void validateOrder(String menuName, List<OptionGroup> optionGroups) {
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
