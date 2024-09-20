package gr.hua.agricoop.service;

import gr.hua.agricoop.dto.CooperativeDto;
import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.repository.CooperativeRepository;
import gr.hua.agricoop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CooperativeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CooperativeRepository cooperativeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CultivationLocationService cultivationLocationService;

    @Transactional
    public List<Cooperative> findAllCooperatives() {
        return cooperativeRepository.findAll();
    }

    @Transactional
    public Cooperative findCooperativeById(Integer cooperativeId) {
        return cooperativeRepository.findById(cooperativeId)
                .orElseThrow(() -> new IllegalArgumentException("Cooperative not found"));
    }

    @Transactional
    public List<Cooperative> findCooperativesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getCooperatives();
    }

    @Transactional
    public Cooperative createCooperative(Cooperative cooperative) {
        return cooperativeRepository.save(cooperative);
    }

    @Transactional
    public Cooperative updateCooperative(Integer cooperativeId, CooperativeDto updatedCooperativeDto) {
        Cooperative existingCooperative = findCooperativeById(cooperativeId);
        
        List<User> farmers = updatedCooperativeDto.getFarmers().stream()
                .map(farmerDto -> userService.getUser(farmerDto.getId()))
                .collect(Collectors.toList());
        
        List<Product> products = updatedCooperativeDto.getProducts().stream()
                .map(productDto -> productService.getProduct(productDto.getId()))
                .collect(Collectors.toList());
        
        List<CultivationLocation> cultivationLocations = updatedCooperativeDto.getCultivationLocations().stream()
                .map(locationDto -> cultivationLocationService.getCultivationLocation(locationDto.getId()))
                .collect(Collectors.toList());
        
        existingCooperative.setName(updatedCooperativeDto.getName());
        existingCooperative.setVat(updatedCooperativeDto.getVat());
        existingCooperative.setFarmers(farmers);
        existingCooperative.setProducts(products);
        existingCooperative.setCultivationLocations(cultivationLocations);
        
        return cooperativeRepository.save(existingCooperative);
    }

    @Transactional
    public void removeCooperative(Integer cooperativeId) {
        cooperativeRepository.deleteById(cooperativeId);
    }

    @Transactional
    public List<Cooperative> findUnprocessedApplications() {
        return cooperativeRepository.findAll().stream()
                .filter(cooperative -> "processing".equalsIgnoreCase(cooperative.getStatus()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Cooperative> findProcessedApplications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getApplications();
    }

    @Transactional
    public String evaluateApplication(Integer cooperativeId) {
        Cooperative cooperative = findCooperativeById(cooperativeId);
        return "{\"evaluationResult\": \"" + cooperative.evaluate() + "\"}";
    }

    @Transactional
    public List<Cooperative> approveCooperative(Integer cooperativeId, Long userId, String notes) {
        Cooperative cooperative = findCooperativeById(cooperativeId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        cooperative.setEmployee(user);
        cooperative.setNotes(notes);
        cooperative.setStatus("approved");
        cooperativeRepository.save(cooperative);
        user.approveApplication(cooperative);
        userRepository.save(user);
        
        return findProcessedApplications(userId);
    }

    @Transactional
    public List<Cooperative> rejectCooperative(Integer cooperativeId, Long userId, String notes) {
        Cooperative cooperative = findCooperativeById(cooperativeId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        cooperative.setEmployee(user);
        cooperative.setNotes(notes);
        cooperative.setStatus("rejected");
        cooperativeRepository.save(cooperative);
        user.rejectApplication(cooperative);
        userRepository.save(user);
        
        return findProcessedApplications(userId);
    }

    @Transactional
    public List<User> linkFarmerToCooperative(Integer cooperativeId, Long userId) {
        Cooperative cooperative = findCooperativeById(cooperativeId);
        User user = userService.getUser(userId);
        cooperative.addFarmer(user);
        user.addCooperative(cooperative);
        userService.saveUser(user);
        cooperativeRepository.save(cooperative);
        
        return cooperative.getFarmers();
    }

    @Transactional
    public List<Product> linkProductToCooperative(Integer cooperativeId, Integer productId) {
        Cooperative cooperative = findCooperativeById(cooperativeId);
        Product product = productService.getProduct(productId);
        cooperative.addProduct(product);
        product.addCooperative(cooperative);
        productService.saveProduct(product);
        cooperativeRepository.save(cooperative);
        
        return cooperative.getProducts();
    }

    @Transactional
    public List<CultivationLocation> linkLocationToCooperative(Integer cooperativeId, Integer cultivationLocationId) {
        Cooperative cooperative = findCooperativeById(cooperativeId);
        CultivationLocation cultivationLocation = cultivationLocationService.getCultivationLocation(cultivationLocationId);
        cooperative.addCultivationLocation(cultivationLocation);
        cultivationLocation.addCooperative(cooperative);
        cultivationLocationService.saveCultivationLocation(cultivationLocation);
        cooperativeRepository.save(cooperative);
        
        return cooperative.getCultivationLocations();
    }
}

