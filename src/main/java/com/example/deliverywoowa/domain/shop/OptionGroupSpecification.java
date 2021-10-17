package com.example.deliverywoowa.domain.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "OPTION_GROUP_SPECS")
@Getter
public class OptionGroupSpecification {
	@Id
	@GeneratedValue
	@Column(name = "OPTION_GROUP_SPEC_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EXCLUSIVE")
	private boolean exclusive;

	@Column(name = "BASIC")
	private boolean basic;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OPTION_GROUP_SPEC_ID")
	private List<OptionSpecification> optionSpecs = new ArrayList<>();

	public OptionGroupSpecification(String name, boolean exclusive, boolean basic,
		OptionSpecification... options) {
		this(null, name, exclusive, basic, Arrays.asList(options));
	}

	@Builder
	public OptionGroupSpecification(Long id, String name, boolean exclusive, boolean basic,
		List<OptionSpecification> optionSpecs) {
		this.id = id;
		this.name = name;
		this.exclusive = exclusive;
		this.basic = basic;
		this.optionSpecs.addAll(optionSpecs);
	}

	public static OptionGroupSpecification basic(String name, boolean exclusive, OptionSpecification... options) {
		return new OptionGroupSpecification(name, exclusive, true, options);
	}

	public static OptionGroupSpecification additive(String name, boolean exclusive, OptionSpecification... options) {
		return new OptionGroupSpecification(name, exclusive, false, options);
	}

	protected OptionGroupSpecification(){}
}
