package com.fm.smartlearningplatform.payment.service;

import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.payment.dto.request.CreateOrderRequest;
import com.fm.smartlearningplatform.payment.dto.response.OrderResponse;
import com.fm.smartlearningplatform.payment.generator.OrderNumberGenerator;
import com.fm.smartlearningplatform.payment.repository.OrderRepository;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final OrderRepository orderRepository;

    private final OrderNumberGenerator orderNumberGenerator;

    public OrderResponse createOrder(
            Long userId,
            CreateOrderRequest request
    ) {

        return null;

    }

}