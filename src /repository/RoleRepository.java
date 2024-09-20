
package gr.hua.serviceapp.repository;

import gr.hua.serviceapp.entity.SubscriptionType;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Hidden
public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Integer> {

    Optional<SubscriptionType> findByName(String name);
}
