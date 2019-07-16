package family.helpful.persist.repository;

import family.helpful.persist.message.model.ChannelContent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelContentRepository extends JpaRepository<ChannelContent, Integer> {
    @Query("SELECT c FROM ChannelContent c WHERE c.channel.name = ?1 order by c.currentThankAmount DESC")
    List<ChannelContent> findByTitleWithAmount(String title, Pageable pageable);
}