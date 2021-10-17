package com.example.deliverywoowa.domain;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.domain.generic.money.Ratio;
import com.example.deliverywoowa.domain.order.Order;
import com.example.deliverywoowa.domain.order.OrderLineItem;
import com.example.deliverywoowa.domain.order.OrderOption;
import com.example.deliverywoowa.domain.order.OrderOptionGroup;
import com.example.deliverywoowa.domain.shop.Menu;
import com.example.deliverywoowa.domain.shop.Option;
import com.example.deliverywoowa.domain.shop.OptionGroup;
import com.example.deliverywoowa.domain.shop.OptionGroupSpecification;
import com.example.deliverywoowa.domain.shop.OptionSpecification;
import com.example.deliverywoowa.domain.shop.Shop;

public class Fixtures {
	public static Shop.ShopBuilder aShop() {
		return Shop.builder()
			.name("오겹돼지")
			.commissionRate(Ratio.valueOf(0.01))
			.open(true)
			.minOrderAmount(Money.wons(13000))
			.commission(Money.ZERO);
	}

	public static Menu.MenuBuilder aMenu() {
		return Menu.builder()
			.shop(aShop().build())
			.name("삼겹살 1인세트")
			.description("삼겹살 + 야채세트 + 김치찌개")
			.basic(anOptionGroupSpec()
				.name("기본")
				.options(Arrays.asList(anOptionSpec().name("소(250g)").price(Money.wons(12000)).build()))
				.build())
			.additives(Arrays.asList(
				anOptionGroupSpec()
					.basic(false)
					.name("맛선택")
					.options(Arrays.asList(anOptionSpec().name("매콤 맛").price(Money.wons(1000)).build()))
					.build()));
	}

	public static OptionGroupSpecification.OptionGroupSpecificationBuilder anOptionGroupSpec() {
		return OptionGroupSpecification.builder()
			.basic(true)
			.exclusive(true)
			.name("기본")
			.options(Arrays.asList(anOptionSpec().build()));
	}

	public static OptionSpecification.OptionSpecificationBuilder anOptionSpec() {
		return OptionSpecification.builder()
			.name("소(250g)")
			.price(Money.wons(12000));
	}

	public static OptionGroup.OptionGroupBuilder anOptionGroup() {
		return OptionGroup.builder()
			.name("기본")
			.options(Arrays.asList(anOption().build()));
	}

	public static Option.OptionBuilder anOption() {
		return Option.builder()
			.name("소(250g)")
			.price(Money.wons(12000));
	}

	public static Order.OrderBuilder anOrder() {
		return Order.builder()
			.userId(1L)
			.shop(aShop().build())
			.status(Order.OrderStatus.ORDERED)
			.orderedTime(LocalDateTime.of(2020, 1, 1, 12, 0))
			.items(Arrays.asList(anOrderLineItem().build()));
	}

	public static OrderLineItem.OrderLineItemBuilder anOrderLineItem() {
		return OrderLineItem.builder()
			.menu(aMenu().build())
			.name("삼겹살 1인세트")
			.count(1)
			.groups(Arrays.asList(
				anOrderOptionGroup()
					.name("기본")
					.options(Arrays.asList(anOrderOption().name("소(250g)").price(Money.wons(12000)).build()))
					.build(),
				anOrderOptionGroup()
					.name("맛선택")
					.options(Arrays.asList(anOrderOption().name("매콤 맛").price(Money.wons(1000)).build()))
					.build()));
	}

	public static OrderOptionGroup.OrderOptionGroupBuilder anOrderOptionGroup() {
		return OrderOptionGroup.builder()
			.name("기본")
			.options(Arrays.asList(anOrderOption().build()));
	}

	public static OrderOption.OrderOptionBuilder anOrderOption() {
		return OrderOption.builder()
			.name("소(250g)")
			.price(Money.wons(12000));
	}
}
