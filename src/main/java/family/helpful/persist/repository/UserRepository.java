package family.helpful.persist.repository;

 import family.helpful.persist.message.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String name);
    @Query("SELECT u FROM User u  where ?1 = ANY (Select channels.name FROM u.channels channels)  ORDER BY u.currentThankAmount DESC")
    List<User> getAllWithAmountAndChannel(Pageable pageable, String channel);
}
