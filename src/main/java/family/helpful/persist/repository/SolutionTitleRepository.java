package family.helpful.persist.repository;



import family.helpful.persist.message.model.SolutionTitle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolutionTitleRepository extends JpaRepository<SolutionTitle, Integer> {
    @Query("SELECT t FROM SolutionTitle t WHERE t.name = ?1")
    SolutionTitle findByName(String name);
    @Query("SELECT s FROM SolutionTitle s ORDER BY s.currentThankAmount DESC")
    List<SolutionTitle> getAllWithAmount(Pageable pageable);
}
