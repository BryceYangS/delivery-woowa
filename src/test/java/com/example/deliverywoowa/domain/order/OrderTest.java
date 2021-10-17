package com.example.deliverywoowa.domain.order;

import static com.example.deliverywoowa.domain.Fixtures.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.domain.generic.money.Ratio;
import com.example.deliverywoowa.domain.shop.Shop;

public class OrderTest {
	@Test(expected = IllegalArgumentException.class)
	public void 가게_미영업시_주문실패() {
		Order order = anOrder().shop(aShop().open(false).build()).build();

		order.place();
	}

	@Test
	public void 결제완료() {
		Order order = anOrder().status(Order.OrderStatus.ORDERED).build();

		order.payed();

		assertThat(order.getOrderStatus(), is(Order.OrderStatus.PAYED));
	}


	@Test
	public void 배송완료() {
		Shop shop = aShop()
			.commissionRate(Ratio.valueOf(0.02))
			.commission(Money.ZERO)
			.build();

		Order order = anOrder()
			.shop(shop)
			.status(Order.OrderStatus.PAYED)
			.items(Arrays.asList(
				anOrderLineItem()
					.count(1)
					.groups(Arrays.asList(
						anOrderOptionGroup()
							.options(Arrays.asList(anOrderOption().price(Money.wons(10000)).build())).build()))
					.build()))
			.build();

		order.delivered();

		assertThat(order.getOrderStatus(), is(Order.OrderStatus.DELIVERED));
		assertThat(shop.getCommission(), is(Money.wons(200)));
	}

}