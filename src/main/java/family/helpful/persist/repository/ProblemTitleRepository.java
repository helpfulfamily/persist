package family.helpful.persist.repository;


import family.helpful.persist.message.model.ProblemTitle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemTitleRepository extends JpaRepository<ProblemTitle, Long> {
    @Query("SELECT t FROM ProblemTitle t WHERE t.name = ?1")
    ProblemTitle findByName(String name);
    @Query("SELECT s FROM ProblemTitle s ORDER BY s.currentThankAmount DESC")
    List<ProblemTitle> getAllWithAmount(Pageable pageable);
    @Query("SELECT s FROM ProblemTitle s  where ?1 = ANY (Select channels.name FROM s.channels channels)  ORDER BY s.currentThankAmount DESC")
     List<ProblemTitle> getAllWithAmountAndChannel(Pageable pageable, String channel);
}
