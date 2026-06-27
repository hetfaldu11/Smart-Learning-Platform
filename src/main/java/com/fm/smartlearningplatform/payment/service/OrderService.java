package com.fm.smartlearningplatform.payment.service;

import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CoursePricing;
import com.fm.smartlearningplatform.course.repository.CoursePricingRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.course.service.CourseService;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.payment.dto.request.CreateOrderRequest;
import com.fm.smartlearningplatform.payment.dto.request.OrderPrice;
import com.fm.smartlearningplatform.payment.dto.response.OrderResponse;
import com.fm.smartlearningplatform.payment.generator.OrderNumberGenerator;
import com.fm.smartlearningplatform.payment.mapper.OrderMapper;
import com.fm.smartlearningplatform.payment.model.Order;
import com.fm.smartlearningplatform.payment.model.OrderItem;
import com.fm.smartlearningplatform.payment.model.enums.CurrencyCode;
import com.fm.smartlearningplatform.payment.model.enums.OrderStatus;
import com.fm.smartlearningplatform.payment.repository.OrderRepository;
import com.fm.smartlearningplatform.payment.validator.OrderValidator;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final OrderRepository orderRepository;

    private final OrderPricingService orderPricingService;

    private final UserService userService;

    private final CourseService courseService;

    private final CoursePricingRepository coursePricingRepository;

    private final OrderNumberGenerator orderNumberGenerator;
    private final OrderMapper orderMapper;
    private final OrderValidator orderValidator;

    @Transactional
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {

        // 1. Load user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 2. Create Order
        Order order = Order.builder().orderNumber(orderNumberGenerator.generate()).user(user).status(OrderStatus.CREATED).currency(CurrencyCode.INR).expiresAt(LocalDateTime.now().plusMinutes(15)).build();


        // 3. Create Order Items
        for (Long courseId : request.courseIds()) {

            CoursePricing pricing = coursePricingRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course pricing not found"));

            Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found : " + courseId));
            BigDecimal originalPrice = pricing.getPrice();

            BigDecimal finalPrice = pricing.getDiscountPrice();

            BigDecimal discountAmount = originalPrice.subtract(finalPrice);

            OrderItem item = OrderItem.builder().course(course).originalPrice(originalPrice).discountAmount(discountAmount).finalPrice(finalPrice).build();

            order.addItem(item);

        }
        // 4. Calculate Price
        OrderPrice orderPrice = orderPricingService.calculate(order);

        // 5. Apply Price
        order.applyPrice(orderPrice);

        // 6. Save
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(order);

    }


}