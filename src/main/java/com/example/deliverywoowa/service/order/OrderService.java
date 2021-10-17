package com.example.deliverywoowa.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.deliverywoowa.domain.delivery.Delivery;
import com.example.deliverywoowa.domain.delivery.DeliveryRepository;
import com.example.deliverywoowa.domain.order.Order;
import com.example.deliverywoowa.domain.order.OrderRepository;
import com.example.deliverywoowa.domain.order.OrderValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final DeliveryRepository deliveryRepository;
	private final OrderMapper orderMapper;
	private final OrderValidator orderValidator;

	@Transactional
	public void placeOrder(Cart cart) {
		Order order = orderMapper.mapFrom(cart);
		order.place(orderValidator);
		orderRepository.save(order);
	}

	@Transactional
	public void payOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
		order.payed();

		Delivery delivery = Delivery.started(order);
		deliveryRepository.save(delivery);
	}

}
