
package gr.hua.agricoop.dto;

import java.util.List;

public class StoreDto {
    private String storeName;
    private String storeVat;
    private List<EmployeeDto> employees;
    private List<ProductDto> products;
    private List<StorageLocationDto> storageLocations;

    // Constructor (προαιρετικά)
    public StoreDto(String storeName, String storeVat, List<EmployeeDto> employees, List<ProductDto> products, List<StorageLocationDto> storageLocations) {
        this.storeName = storeName;
        this.storeVat = storeVat;
        this.employees = employees;
        this.products = products;
        this.storageLocations = storageLocations;
    }

    // Getters
    public String getStoreName() {
        return storeName;
    }

    public String getStoreVat() {
        return storeVat;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public List<StorageLocationDto> getStorageLocations() {
        return storageLocations;
    }

    // Setters (προαιρετικά)
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreVat(String storeVat) {
        this.storeVat = storeVat;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public void setStorageLocations(List<StorageLocationDto> storageLocations) {
        this.storageLocations = storageLocations;
    }
}
