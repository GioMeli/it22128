
package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Category;
import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.repository.CategoryRepository;
import gr.hua.agricoop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product getProduct(Integer productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    @Transactional
    public Product saveProduct(Product product) {
        categoryRepository.findById(product.getCategoryId()).ifPresent(category -> {
            product.setCategory(category);
        });
        productRepository.save(product);
        return product;
    }

    @Transactional
    public Product editProduct(Integer productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setDescription(updatedProduct.getDescription());
            categoryRepository.findById(updatedProduct.getCategoryId()).ifPresent(existingProduct::setCategory);
            productRepository.save(existingProduct);
        }
        return existingProduct;
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public ResponseEntity<?> addCategory(Integer productId, Integer categoryId) {
        Product product = getProduct(productId);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (product == null || category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product or Category not found");
        }
        product.setCategory(category);
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @Transactional
    public ResponseEntity<?> removeCategory(Integer productId) {
        Product product = getProduct(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        product.setCategory(null);  // Or handle it as needed
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }
}
