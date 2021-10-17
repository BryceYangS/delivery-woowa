package com.example.deliverywoowa.domain.order;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.deliverywoowa.domain.shop.Menu;
import com.example.deliverywoowa.domain.shop.MenuRepository;
import com.example.deliverywoowa.domain.shop.OptionGroupSpecification;
import com.example.deliverywoowa.domain.shop.Shop;
import com.example.deliverywoowa.domain.shop.ShopRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderValidator {
	private final ShopRepository shopRepository;
	private final MenuRepository menuRepository;

	public void validate(Order order) {
		validate(order, getShop(order), getMenus(order));
	}

	private void validate(Order order, Shop shop, Map<Long, Menu> menus) {
		if (order.getOrderLineItems().isEmpty()) {
			throw new IllegalStateException("주문 항목이 비어 있습니다.");
		}

		// 영업여부 확인
		if (!shop.isOpen()) {
			throw new IllegalArgumentException("가게가 영업중이 아닙니다.");
		}

		// 최소주문금액이상 확인
		if (!shop.isValidOrderAmount(order.calculateTotalPrice())) {
			throw new IllegalStateException(String.format("최소 주문 금액 %s 이상을 주문해주세요.", shop.getMinOrderAmount()));
		}

		// 주문항목 검증
		for (OrderLineItem item : order.getOrderLineItems()) {
			validateOrderLineItem(item, menus.get(item.getMenuId()));
		}

	}

	private void validateOrderLineItem(OrderLineItem item, Menu menu) {
		if (!menu.getName().equals(item.getName())) {
			throw new IllegalArgumentException("기본 상품이 변경됐습니다.");
		}

		for (OrderOptionGroup group : item.getGroups()) {
			validateOrderOptionGroup(group, menu);
		}
	}

	private void validateOrderOptionGroup(OrderOptionGroup group, Menu menu) {
		for (OptionGroupSpecification spec : menu.getOptionGroupSpecs()) {
			if (spec.isSatisfiedBy(group.convertToOptionGroup())) {
				return;
			}
		}

		throw new IllegalArgumentException("메뉴가 변경됐습니다.");
	}

	private Shop getShop(Order order) {
		return shopRepository.findById(order.getShopId()).orElseThrow(IllegalArgumentException::new);
	}

	private Map<Long, Menu> getMenus(Order order) {
		return menuRepository.findAllById(order.getMenuIds()).stream().collect(toMap(Menu::getId, identity()));
	}
}
