package ifsc.edu.tpj.repository;

import ifsc.edu.tpj.model.ModerationLog;
import ifsc.edu.tpj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModerationLogRepository extends JpaRepository<ModerationLog, Long> {
    List<ModerationLog> findByTargetUser(User user);
    List<ModerationLog> findByModerator(User moderator);
}
