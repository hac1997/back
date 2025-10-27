package ifsc.edu.tpj.service;

import ifsc.edu.tpj.model.*;
import ifsc.edu.tpj.model.enums.DiscussionStatus;
import ifsc.edu.tpj.model.enums.DiscussionType;
import ifsc.edu.tpj.model.enums.UserStatus;
import ifsc.edu.tpj.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final DiscussionVoteRepository voteRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ModerationLogService moderationLogService;

    @Transactional
    public Discussion createDiscussion(Long creatorId, DiscussionType type, String description, Long reportId, Long targetUserId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        if (!creator.getRoles().contains("MODERATOR") && !creator.getRoles().contains("ADMIN")) {
            throw new RuntimeException("Only moderators can create discussions");
        }

        Discussion.DiscussionBuilder builder = Discussion.builder()
                .discussionType(type)
                .status(DiscussionStatus.OPEN)
                .description(description)
                .createdBy(creator);

        if (reportId != null) {
            Report report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Report not found"));
            builder.relatedReport(report);
        }

        if (targetUserId != null) {
            User targetUser = userRepository.findById(targetUserId)
                    .orElseThrow(() -> new RuntimeException("Target user not found"));
            builder.targetUser(targetUser);
        }

        return discussionRepository.save(builder.build());
    }

    @Transactional
    public DiscussionVote voteOnDiscussion(Long discussionId, Long moderatorId, boolean inFavor, String justification) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));
        User moderator = userRepository.findById(moderatorId)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        if (!moderator.getRoles().contains("MODERATOR") && !moderator.getRoles().contains("ADMIN")) {
            throw new RuntimeException("Only moderators can vote");
        }

        if (discussion.getStatus() != DiscussionStatus.OPEN && discussion.getStatus() != DiscussionStatus.VOTING) {
            throw new RuntimeException("Discussion is not open for voting");
        }

        if (voteRepository.findByDiscussionAndModerator(discussion, moderator).isPresent()) {
            throw new RuntimeException("Moderator has already voted");
        }

        if (discussion.getStatus() == DiscussionStatus.OPEN) {
            discussion.setStatus(DiscussionStatus.VOTING);
            discussionRepository.save(discussion);
        }

        DiscussionVote vote = DiscussionVote.builder()
                .discussion(discussion)
                .moderator(moderator)
                .inFavor(inFavor)
                .justification(justification)
                .build();

        return voteRepository.save(vote);
    }

    @Transactional
    public Discussion closeDiscussion(Long discussionId, Long adminId) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getRoles().contains("ADMIN")) {
            throw new RuntimeException("Only admins can close discussions");
        }

        long votesInFavor = voteRepository.countByDiscussionAndInFavor(discussion, true);
        long votesAgainst = voteRepository.countByDiscussionAndInFavor(discussion, false);

        boolean approved = votesInFavor > votesAgainst;
        discussion.setStatus(approved ? DiscussionStatus.APPROVED : DiscussionStatus.REJECTED);
        discussion.setClosedAt(LocalDateTime.now());
        discussion.setResolution("Votes: " + votesInFavor + " in favor, " + votesAgainst + " against");

        if (approved) {
            executeDiscussionAction(discussion);
        }

        return discussionRepository.save(discussion);
    }

    private void executeDiscussionAction(Discussion discussion) {
        if (discussion.getTargetUser() == null) {
            return;
        }

        User targetUser = discussion.getTargetUser();

        switch (discussion.getDiscussionType()) {
            case BAN_USER:
                targetUser.setStatus(UserStatus.BANNED);
                userRepository.save(targetUser);
                moderationLogService.logBanUser(discussion.getCreatedBy(), targetUser, discussion);
                break;
            case SUSPEND_USER:
                targetUser.setStatus(UserStatus.SUSPENDED);
                targetUser.setSuspendedUntil(LocalDateTime.now().plusDays(30));
                targetUser.setSuspensionReason(discussion.getDescription());
                userRepository.save(targetUser);
                moderationLogService.logSuspendUser(discussion.getCreatedBy(), targetUser, discussion);
                break;
            case RESTORE_CONTENT:
                if (discussion.getTargetPost() != null) {
                    discussion.getTargetPost().setVisible(true);
                } else if (discussion.getTargetComment() != null) {
                    discussion.getTargetComment().setVisible(true);
                }
                break;
            default:
                break;
        }
    }

    public List<Discussion> getDiscussionsByStatus(DiscussionStatus status) {
        return discussionRepository.findByStatus(status);
    }

    public Discussion getDiscussionById(Long discussionId) {
        return discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));
    }

    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    public List<DiscussionVote> getVotesForDiscussion(Long discussionId) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));
        return voteRepository.findByDiscussion(discussion);
    }
}
