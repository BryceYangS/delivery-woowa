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

	public Menu(Long id, String name, String description, Shop shop, OptionGroupSpecification basic, List<OptionGroupSpecification> optionGroupSpecs) {
		this.id = id;
		this.name = name;
		this.shop = shop;
		this.description = description;

		this.shop.addMenu(this);
		this.optionGroupSpecs.add(basic);
		this.optionGroupSpecs.addAll(optionGroupSpecs);
	}

	protected Menu(){}
}
