package family.helpful.persist.repository;

import family.helpful.persist.message.model.DialogContent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DialogContentRepository extends JpaRepository<DialogContent, Long> {
    @Query("SELECT d FROM DialogContent d WHERE (d.sender.username= ?1 AND d.receiver.username=?2) " +
            "OR (d.sender.username= ?2 AND d.receiver.username=?1) order by d.createDate DESC")
    List<DialogContent> findContentsBySenderAndReceiver(String senderID, String receiverID, Pageable pageable);
}
