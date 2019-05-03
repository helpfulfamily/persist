package family.helpful.persist.repository;



import family.helpful.persist.message.model.Channel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("SELECT c FROM Channel c WHERE c.name = ?1")
    Channel findByName(String name);

    @Query("SELECT c FROM Channel  c ORDER BY c.currentThankAmount DESC")
    List<Channel> getAllWithAmount(Pageable pageable);

}
