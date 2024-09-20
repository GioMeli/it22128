package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsServiceImpl {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductDetailsImpl loadProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product Not Found with id: " + productId));

        return ProductDetailsImpl.build(product);
    }
}

