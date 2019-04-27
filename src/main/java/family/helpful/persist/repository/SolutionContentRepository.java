package family.helpful.persist.repository;


import family.helpful.persist.message.model.SolutionContent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolutionContentRepository extends JpaRepository<SolutionContent, Integer> {
    @Query("SELECT c FROM SolutionContent c WHERE c.solutionTitle.name = ?1 order by c.currentThankAmount DESC")
    List<SolutionContent> findByTitleWithAmount(String title, Pageable pageable);
}
