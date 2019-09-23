package family.helpful.persist.repository;

import family.helpful.persist.message.model.ProblemContent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemContentRepository extends JpaRepository<ProblemContent, Long> {
    @Query("SELECT c FROM ProblemContent c WHERE c.problemTitle.name = ?1 order by c.currentThankAmount DESC")
    List<ProblemContent> findByTitleWithAmount(String title, Pageable pageable);
}
