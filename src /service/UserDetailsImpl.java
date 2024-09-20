
package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Product;

import java.util.Objects;

public class ProductDetailsImpl {

    private Integer id;
    private String name;
    private String category;
    private Double price;

    public ProductDetailsImpl(Integer id, String name, String category, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public static ProductDetailsImpl build(Product product) {
        return new ProductDetailsImpl(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetailsImpl that = (ProductDetailsImpl) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
