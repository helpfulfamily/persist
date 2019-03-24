package army.helpful.persistha.repository;

import army.helpful.persistha.message.model.Content;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Integer> {
    @Query("SELECT c FROM Content c WHERE c.title.name = ?1 order by c.id DESC")
    List<Content> findByTitleWithAmount(String title, Pageable pageable);
}
