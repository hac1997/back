package ifsc.edu.tpj.repository;

import ifsc.edu.tpj.model.Appeal;
import ifsc.edu.tpj.model.User;
import ifsc.edu.tpj.model.enums.AppealStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {
    List<Appeal> findByUser(User user);
    List<Appeal> findByStatus(AppealStatus status);
}
