
package gr.hua.agricoop.repository;

import gr.hua.agricoop.entity.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface UserRepository extends JpaRepository<User, Long> {
}
