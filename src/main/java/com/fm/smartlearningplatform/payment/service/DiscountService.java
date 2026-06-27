package com.fm.smartlearningplatform.payment.service;



import com.fm.smartlearningplatform.payment.model.Order;
import com.fm.smartlearningplatform.payment.model.OrderItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountService {


    public void applyDiscount(Order order) {

        for (OrderItem item : order.getItems()) {

            BigDecimal discount = BigDecimal.ZERO;

            item.setDiscountAmount(discount);

            item.setFinalPrice(
                    item.getOriginalPrice().subtract(discount)
            );
        }
    }
}
