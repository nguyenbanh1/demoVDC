package com.nguyen.demo.order;

import com.nguyen.demo.core.entity.Customer;
import com.nguyen.demo.core.entity.Order;
import com.nguyen.demo.core.entity.OrderDetail;
import com.nguyen.demo.core.entity.OrderStatus;
import com.nguyen.demo.core.entity.Product;
import com.nguyen.demo.core.entity.User;
import com.nguyen.demo.core.repository.OrderRepository;
import com.nguyen.demo.customer.CustomerService;
import com.nguyen.demo.error.OverProductQuantityException;
import com.nguyen.demo.product.ProductService;
import com.nguyen.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final ProductService productService;

    private final UserService userService;

    @Transactional
    public void order(OrderReq orderReq) {
        validateOrderReq(orderReq);
        User user = userService.currentLogin();
        Order order = OrderMapper.INSTANCE.of(orderReq, user);
        order.setOrderDate(Date.from(Instant.now()));
        order.setOrderStatus(OrderStatus.PENDING);
        Set<OrderDetail> orderDetails = orderReq.getOrderProducts()
                .stream()
                .map(item -> {
                    OrderDetail orderDetail = new OrderDetail();
                    Product product = productService.getById(item.getProductId());
                    orderDetail.setProduct(product);
                    orderDetail.setOrder(order);
                    orderDetail.setQuantity(item.getQuantity());
                    product.setQuantity(product.getQuantity() - item.getQuantity());
                    return orderDetail;
                }).collect(Collectors.toSet());
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
    }

    private void validateOrderReq(OrderReq orderReq) {
        orderReq.getOrderProducts().forEach(item -> {
            Product product = productService.getById(item.getProductId());
            if (product.getQuantity() < item.getQuantity()) {
                throw new OverProductQuantityException("Over quantity of product with name = " + product.getName());
            }
        });
    }
}
