package family.helpful.persist.repository;

import family.helpful.persist.message.model.DialogContent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DialogContentRepository extends JpaRepository<DialogContent, Integer> {
    @Query("SELECT d FROM DialogContent d WHERE (d.sender.id= ?1 AND d.receiver.id=?2) " +
            "OR (d.sender.id= ?2 AND d.receiver.id=?1) order by d.createDate DESC")
    List<DialogContent> findContentsBySenderAndReceiver(Long senderID, Long receiverID, Pageable pageable);
}
