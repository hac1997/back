package ifsc.edu.tpj.repository;

import ifsc.edu.tpj.model.Discussion;
import ifsc.edu.tpj.model.enums.DiscussionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    List<Discussion> findByStatus(DiscussionStatus status);
}
