package com.example.deliverywoowa.service.delivery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.deliverywoowa.domain.delivery.Delivery;
import com.example.deliverywoowa.domain.delivery.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {
	private DeliveryRepository deliveryRepository;

	@Transactional
	public void deliverOrder(Long deliveryId) {
		Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(IllegalArgumentException::new);
		delivery.getOrder().delivered();
		delivery.completed();
	}
}
