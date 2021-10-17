package com.example.deliverywoowa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.deliverywoowa.domain.generic.money.Money;
import com.example.deliverywoowa.service.order.Cart;
import com.example.deliverywoowa.service.order.OrderService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class DeliveryWoowaApplication implements CommandLineRunner {
	private static Logger LOG = LoggerFactory.getLogger(DeliveryWoowaApplication.class);

	private final OrderService orderService;

	@Override
	public void run(String... args) throws Exception {
		Cart cart = new Cart(1L, 1L,
			new Cart.CartLineItem(1L, "삼겹살 1인세트", 2,
				new Cart.CartOptionGroup("기본",
					new Cart.CartOption("소(250g)", Money.wons(12000)))));

		orderService.placeOrder(cart);

		orderService.payOrder(1L);

		orderService.deliverOrder(1L);

	}

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(DeliveryWoowaApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}
}
