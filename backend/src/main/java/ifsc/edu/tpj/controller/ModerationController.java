package ifsc.edu.tpj.controller;

import ifsc.edu.tpj.dto.AppealRequestDTO;
import ifsc.edu.tpj.dto.DiscussionRequestDTO;
import ifsc.edu.tpj.dto.DiscussionVoteRequestDTO;
import ifsc.edu.tpj.dto.ReportRequestDTO;
import ifsc.edu.tpj.model.*;
import ifsc.edu.tpj.model.enums.*;
import ifsc.edu.tpj.service.DiscussionService;
import ifsc.edu.tpj.service.ModerationLogService;
import ifsc.edu.tpj.service.ModerationService;
import ifsc.edu.tpj.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderation")
@RequiredArgsConstructor
public class ModerationController {

    private final ModerationService moderationService;
    private final ReportService reportService;
    private final DiscussionService discussionService;
    private final ModerationLogService logService;

    @PostMapping("/reports")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<Report> createReport(@RequestBody ReportRequestDTO dto) {
        Report report;
        if (dto.targetUserId() != null) {
            report = reportService.createUserReport(dto.reporterId(), dto.targetUserId(), dto.reportType(), dto.reason());
        } else if (dto.targetPostId() != null) {
            report = reportService.createPostReport(dto.reporterId(), dto.targetPostId(), dto.reportType(), dto.reason());
        } else if (dto.targetCommentId() != null) {
            report = reportService.createCommentReport(dto.reporterId(), dto.targetCommentId(), dto.reportType(), dto.reason());
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/reports/pending")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<List<Report>> getPendingReports() {
        return ResponseEntity.ok(reportService.getReportsByStatus(ReportStatus.PENDING));
    }

    @GetMapping("/reports/user/{userId}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<List<Report>> getReportsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.getReportsByUser(userId));
    }

    @PutMapping("/reports/{reportId}/review")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Report> reviewReport(@PathVariable Long reportId, @RequestParam Long reviewerId, @RequestParam ReportStatus status, @RequestParam String notes) {
        return ResponseEntity.ok(reportService.reviewReport(reportId, reviewerId, status, notes));
    }

    @PostMapping("/posts/{postId}/lock")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Post> lockPost(@PathVariable Long postId, @RequestParam Long moderatorId) {
        return ResponseEntity.ok(moderationService.lockPost(postId, moderatorId));
    }

    @PostMapping("/posts/{postId}/unlock")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Post> unlockPost(@PathVariable Long postId, @RequestParam Long moderatorId) {
        return ResponseEntity.ok(moderationService.unlockPost(postId, moderatorId));
    }

    @PostMapping("/comments/{commentId}/pin")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Comment> pinComment(@PathVariable Long commentId, @RequestParam Long moderatorId) {
        return ResponseEntity.ok(moderationService.pinComment(commentId, moderatorId));
    }

    @PostMapping("/comments/{commentId}/unpin")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Comment> unpinComment(@PathVariable Long commentId, @RequestParam Long moderatorId) {
        return ResponseEntity.ok(moderationService.unpinComment(commentId, moderatorId));
    }

    @PostMapping("/posts/{postId}/restore")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Post> restorePost(@PathVariable Long postId, @RequestParam Long moderatorId) {
        return ResponseEntity.ok(moderationService.restorePost(postId, moderatorId));
    }

    @PostMapping("/comments/{commentId}/restore")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Comment> restoreComment(@PathVariable Long commentId, @RequestParam Long moderatorId) {
        return ResponseEntity.ok(moderationService.restoreComment(commentId, moderatorId));
    }

    @PostMapping("/discussions")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Discussion> createDiscussion(@RequestBody DiscussionRequestDTO dto) {
        Discussion discussion = discussionService.createDiscussion(
                dto.creatorId(),
                dto.discussionType(),
                dto.description(),
                dto.reportId(),
                dto.targetUserId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(discussion);
    }

    @GetMapping("/discussions")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<List<Discussion>> getAllDiscussions() {
        return ResponseEntity.ok(discussionService.getAllDiscussions());
    }

    @GetMapping("/discussions/{discussionId}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Discussion> getDiscussion(@PathVariable Long discussionId) {
        return ResponseEntity.ok(discussionService.getDiscussionById(discussionId));
    }

    @GetMapping("/discussions/{discussionId}/votes")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<List<DiscussionVote>> getDiscussionVotes(@PathVariable Long discussionId) {
        return ResponseEntity.ok(discussionService.getVotesForDiscussion(discussionId));
    }

    @PostMapping("/discussions/{discussionId}/vote")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<DiscussionVote> voteOnDiscussion(@PathVariable Long discussionId, @RequestBody DiscussionVoteRequestDTO dto) {
        DiscussionVote vote = discussionService.voteOnDiscussion(
                discussionId,
                dto.moderatorId(),
                dto.inFavor(),
                dto.justification()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }

    @PostMapping("/appeals")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<Appeal> createAppeal(@RequestBody AppealRequestDTO dto) {
        Appeal appeal = moderationService.createAppeal(dto.userId(), dto.reason(), dto.discussionId());
        return ResponseEntity.status(HttpStatus.CREATED).body(appeal);
    }

    @GetMapping("/appeals/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Appeal>> getPendingAppeals() {
        return ResponseEntity.ok(moderationService.getPendingAppeals());
    }

    @GetMapping("/appeals/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<List<Appeal>> getAppealsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(moderationService.getAppealsByUser(userId));
    }

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ModerationLog>> getAllLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

    @GetMapping("/logs/user/{userId}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<List<ModerationLog>> getLogsByUser(@PathVariable Long userId) {
        User user = new User();
        user.setUserId(userId);
        return ResponseEntity.ok(logService.getLogsByUser(user));
    }
}
