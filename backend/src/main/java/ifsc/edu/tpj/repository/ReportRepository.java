package ifsc.edu.tpj.repository;

import ifsc.edu.tpj.model.Report;
import ifsc.edu.tpj.model.User;
import ifsc.edu.tpj.model.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStatus(ReportStatus status);
    List<Report> findByReportedUser(User user);
    List<Report> findByReporter(User user);
}
