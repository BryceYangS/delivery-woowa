package com.example.deliverywoowa.domain.shop;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class OptionGroup {
	private String name;
	private List<Option> options;

	@Builder
	public OptionGroup(String name, List<Option> options) {
		this.name = name;
		this.options = options;
	}
}
