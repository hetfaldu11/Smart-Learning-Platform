package com.fm.smartlearningplatform.payment.service;

import com.fm.smartlearningplatform.payment.dto.request.OrderPrice;
import com.fm.smartlearningplatform.payment.model.Order;
import com.fm.smartlearningplatform.payment.model.OrderItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderPricingService {

    private final DiscountService discountService;

    private final TaxService taxService;


    public OrderPrice calculate(Order order) {

        // Apply discount on every order item
        discountService.applyDiscount(order);

        BigDecimal subtotalAmount = order.getItems()
                .stream()
                .map(OrderItem::getOriginalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount = order.getItems()
                .stream()
                .map(OrderItem::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal taxableAmount = order.getItems()
                .stream()
                .map(OrderItem::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal taxAmount =
                taxService.calculateTax(taxableAmount);

        BigDecimal totalAmount =
                taxableAmount.add(taxAmount);

        return new OrderPrice(
                subtotalAmount,
                discountAmount,
                taxableAmount,
                taxAmount,
                totalAmount
        );
    }
}