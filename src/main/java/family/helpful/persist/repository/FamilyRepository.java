package family.helpful.persist.repository;

import family.helpful.persist.message.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FamilyRepository extends JpaRepository<Family, Integer> {

}
