package army.helpful.persistha.repository;


import army.helpful.persistha.message.model.Title;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, Integer> {
    @Query("SELECT t FROM Title t WHERE t.name = ?1")
    Title findByName(String name);
    @Query("SELECT s FROM Title s ORDER BY s.id DESC")
    List<Title> getAllWithAmount(Pageable pageable);
}
