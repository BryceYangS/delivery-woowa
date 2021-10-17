package com.example.deliverywoowa.service.order;

import static java.util.stream.Collectors.*;

import org.springframework.stereotype.Component;

import com.example.deliverywoowa.domain.order.Order;
import com.example.deliverywoowa.domain.order.OrderLineItem;
import com.example.deliverywoowa.domain.order.OrderOption;
import com.example.deliverywoowa.domain.order.OrderOptionGroup;
import com.example.deliverywoowa.domain.shop.Menu;
import com.example.deliverywoowa.domain.shop.MenuRepository;
import com.example.deliverywoowa.domain.shop.Shop;
import com.example.deliverywoowa.domain.shop.ShopRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMapper {
	private final MenuRepository menuRepository;
	private final ShopRepository shopRepository;

	public Order mapFrom(Cart cart) {
		Shop shop = shopRepository.findById(cart.getShopId()).orElseThrow(IllegalArgumentException::new);

		return new Order(
			cart.getUserId(),
			shop,
			cart.getCartLineItems()
				.stream()
				.map(this::toOrderLineItem)
				.collect(toList()));

	}

	private OrderLineItem toOrderLineItem(Cart.CartLineItem cartLineItem) {
		Menu menu = menuRepository.findById(cartLineItem.getMenuId()).orElseThrow(IllegalArgumentException::new);

		return new OrderLineItem(
			menu,
			cartLineItem.getName(),
			cartLineItem.getCount(),
			cartLineItem.getGroups()
				.stream()
				.map(this::toOrderOptionGroup)
				.collect(toList())
		);
	}

	private OrderOptionGroup toOrderOptionGroup(Cart.CartOptionGroup cartOptionGroup) {
		return new OrderOptionGroup(
			cartOptionGroup.getName(),
			cartOptionGroup.getOptions()
				.stream()
				.map(this::toOrderOption)
				.collect(toList()));
	}

	private OrderOption toOrderOption(Cart.CartOption cartOption) {
		return new OrderOption(
			cartOption.getName(),
			cartOption.getPrice());
	}

}
