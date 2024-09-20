package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Order;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.service.OrderService;
import gr.hua.agricoop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{order_id}")
    public Order getOrder(@PathVariable Long order_id) {
        return orderService.getOrder(order_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/user/{user_id}")
    public List<Order> getUserOrders(@PathVariable Long user_id) {
        return orderService.getUserOrders(user_id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public List<Order> addOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
        return orderService.getOrders();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{order_id}")
    public ResponseEntity<Order> editOrder(@PathVariable Long order_id, @RequestBody Order order) {
        Order updatedOrder = orderService.editOrder(order_id, order);
        return ResponseEntity.ok(updatedOrder);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{order_id}")
    public List<Order> deleteOrder(@PathVariable Long order_id) {
        orderService.deleteOrder(order_id);
        return orderService.getOrders();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{order_id}/details")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long order_id) {
        return ResponseEntity.ok(orderService.getOrderDetails(order_id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @PostMapping("/{order_id}/approve")
    public List<Order> approveOrder(@PathVariable Long order_id, @RequestBody Map<String, String> requestBody) {
        return orderService.approveOrder(order_id, requestBody.get("notes"));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @PostMapping("/{order_id}/reject")
    public List<Order> rejectOrder(@PathVariable Long order_id, @RequestBody Map<String, String> requestBody) {
        return orderService.rejectOrder(order_id, requestBody.get("notes"));
    }
}

