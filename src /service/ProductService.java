package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Order;
import gr.hua.agricoop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @Transactional
    public List<Order> getOrdersWithoutProduct() {
        List<Order> orders = orderRepository.findAll();
        orders.removeIf(order -> !order.getProducts().isEmpty());
        return orders;
    }

    @Transactional
    public Order saveOrder(Order order) {
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public Order editOrder(Integer orderId, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(orderId).orElse(null);
        if (existingOrder != null) {
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
            existingOrder.setCustomer(updatedOrder.getCustomer());
            orderRepository.save(existingOrder);
        }
        return existingOrder;
    }

    @Transactional
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
}

