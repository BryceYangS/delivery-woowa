package com.example.deliverywoowa.service.shop;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.deliverywoowa.domain.shop.Shop;
import com.example.deliverywoowa.domain.shop.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {
	private final ShopRepository shopRepository;

	@Transactional(readOnly = true)
	public MenuBoard getMenuBoard(Long shopId) {
		Shop shop = shopRepository.findById(shopId).orElseThrow(IllegalArgumentException::new);
		return new MenuBoard(shop);
	}
}
