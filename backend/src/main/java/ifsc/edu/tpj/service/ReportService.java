package ifsc.edu.tpj.service;

import ifsc.edu.tpj.model.*;
import ifsc.edu.tpj.model.enums.ReportStatus;
import ifsc.edu.tpj.model.enums.ReportType;
import ifsc.edu.tpj.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MailService mailService;

    @Transactional
    public Report createUserReport(Long reporterId, Long reportedUserId, ReportType type, String reason) {
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("Reporter not found"));
        User reportedUser = userRepository.findById(reportedUserId)
                .orElseThrow(() -> new RuntimeException("Reported user not found"));

        Report report = Report.builder()
                .reportType(type)
                .status(ReportStatus.PENDING)
                .reason(reason)
                .reporter(reporter)
                .reportedUser(reportedUser)
                .build();

        report = reportRepository.save(report);

        mailService.sendMailMessage(
                "Warning - Report Received",
                "You have received a report for " + type + ". Reason: " + reason,
                reportedUser.getEmail()
        );

        return report;
    }

    @Transactional
    public Report createPostReport(Long reporterId, Long postId, ReportType type, String reason) {
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("Reporter not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Report report = Report.builder()
                .reportType(type)
                .status(ReportStatus.PENDING)
                .reason(reason)
                .reporter(reporter)
                .reportedPost(post)
                .reportedUser(post.getAuthor())
                .build();

        return reportRepository.save(report);
    }

    @Transactional
    public Report createCommentReport(Long reporterId, Long commentId, ReportType type, String reason) {
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("Reporter not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Report report = Report.builder()
                .reportType(type)
                .status(ReportStatus.PENDING)
                .reason(reason)
                .reporter(reporter)
                .reportedComment(comment)
                .reportedUser(comment.getAuthor())
                .build();

        return reportRepository.save(report);
    }

    @Transactional
    public Report reviewReport(Long reportId, Long reviewerId, ReportStatus newStatus, String reviewNotes) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        report.setStatus(newStatus);
        report.setReviewedBy(reviewer);
        report.setReviewNotes(reviewNotes);
        report.setReviewedAt(LocalDateTime.now());

        return reportRepository.save(report);
    }

    public List<Report> getReportsByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    public List<Report> getReportsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reportRepository.findByReportedUser(user);
    }

    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
}
