package army.helpful.persistha.repository;


import army.helpful.persistha.message.model.ProblemTitle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemTitleRepository extends JpaRepository<ProblemTitle, Integer> {
    @Query("SELECT t FROM ProblemTitle t WHERE t.name = ?1")
    ProblemTitle findByName(String name);
    @Query("SELECT s FROM ProblemTitle s ORDER BY s.currentThankAmount DESC")
    List<ProblemTitle> getAllWithAmount(Pageable pageable);
}
