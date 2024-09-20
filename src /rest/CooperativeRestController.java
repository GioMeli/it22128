package gr.hua.agricoop.rest;

import gr.hua.agricoop.dto.ProductDto;
import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping("")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{product_id}")
    public Product getProduct(@PathVariable Integer product_id) {
        return productService.getProduct(product_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/new")
    public List<Product> addProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return productService.getProducts();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/{product_id}")
    public ResponseEntity<Product> editProduct(@PathVariable Integer product_id, @RequestBody ProductDto productDto) {
        Product updatedProduct = productService.editProduct(product_id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/{product_id}")
    public List<Product> deleteProduct(@PathVariable Integer product_id) {
        productService.deleteProduct(product_id);
        return productService.getProducts();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{product_id}/users")
    public List<User> getProductUsers(@PathVariable Integer product_id) {
        return productService.getProduct(product_id).getUsers();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{product_id}/user/{user_id}")
    public List<User> addUser(@PathVariable Integer product_id, @PathVariable Long user_id) {
        return productService.addUser(product_id, user_id);
    }
}

