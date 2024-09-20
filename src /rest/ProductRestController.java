package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Order;
import gr.hua.agricoop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{order_id}")
    public Order getOrder(@PathVariable Integer order_id) {
        return orderService.getOrder(order_id);
    }

    @PostMapping("/new")
    public List<Order> addOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
        return orderService.getOrders();
    }

    @PutMapping("/{order_id}")
    public ResponseEntity<Order> editOrder(@PathVariable Integer order_id, @RequestBody Order order) {
        Order updatedOrder = orderService.editOrder(order_id, order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{order_id}")
    public List<Order> deleteOrder(@PathVariable Integer order_id) {
        orderService.deleteOrder(order_id);
        return orderService.getOrders();
    }
}

