package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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
            productRepository.save(existingProduct);
        }
        return existingProduct;
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public List<Product> getAvailableProducts() {
        List<Product> products = productRepository.findAll();
        products.removeIf(product -> !product.isAvailable());
        return products;
    }
}

