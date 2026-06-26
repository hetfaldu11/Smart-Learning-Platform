package com.fm.smartlearningplatform.payment.repository;

import com.fm.smartlearningplatform.payment.model.Order;
import com.fm.smartlearningplatform.payment.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByStatus(OrderStatus status);

    List<Order> findAllByStatusAndExpiresAtBefore(
            OrderStatus status,
            LocalDateTime time
    );

}