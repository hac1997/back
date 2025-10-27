package ifsc.edu.tpj.service;

import ifsc.edu.tpj.model.*;
import ifsc.edu.tpj.model.enums.UserStatus;
import ifsc.edu.tpj.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModerationService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AppealRepository appealRepository;
    private final ModerationLogService moderationLogService;
    private final MailService mailService;

    @Transactional
    public Post lockPost(Long postId, Long moderatorId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User moderator = userRepository.findById(moderatorId)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        validateModeratorOrAuthor(moderator, post.getAuthor());

        post.setLocked(true);
        postRepository.save(post);

        moderationLogService.logLockPost(moderator, post);
        return post;
    }

    @Transactional
    public Post unlockPost(Long postId, Long moderatorId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User moderator = userRepository.findById(moderatorId)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        validateModeratorOrAuthor(moderator, post.getAuthor());

        post.setLocked(false);
        postRepository.save(post);

        moderationLogService.logUnlockPost(moderator, post);
        return post;
    }

    @Transactional
    public Comment pinComment(Long commentId, Long moderatorId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User moderator = userRepository.findById(moderatorId)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        validateModeratorOrAuthor(moderator, comment.getPost().getAuthor());

        comment.setPinned(true);
        commentRepository.save(comment);

        moderationLogService.logPinComment(moderator, comment);
        return comment;
    }

    @Transactional
    public Comment unpinComment(Long commentId, Long moderatorId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User moderator = userRepository.findById(moderatorId)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        validateModeratorOrAuthor(moderator, comment.getPost().getAuthor());

        comment.setPinned(false);
        commentRepository.save(comment);

        moderationLogService.logUnpinComment(moderator, comment);
        return comment;
    }

    @Transactional
    public Post restorePost(Long postId, Long moderatorId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User moderator = userRepository.findById(moderatorId)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        validateModerator(moderator);

        post.setVisible(true);
        postRepository.save(post);

        moderationLogService.logRestorePost(moderator, post);
        return post;
    }

    @Transactional
    public Comment restoreComment(Long commentId, Long moderatorId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User moderator = userRepository.findById(moderatorId)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        validateModerator(moderator);

        comment.setVisible(true);
        commentRepository.save(comment);

        moderationLogService.logRestoreComment(moderator, comment);
        return comment;
    }

    @Transactional
    public User restoreUser(Long userId, Long adminId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        validateAdmin(admin);

        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedUntil(null);
        user.setSuspensionReason(null);
        userRepository.save(user);

        moderationLogService.logUnbanUser(admin, user);
        return user;
    }

    @Transactional
    public User promoteModerator(Long userId, Long adminId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        validateAdmin(admin);

        if (!user.getRoles().contains("MODERATOR")) {
            user.getRoles().add("MODERATOR");
            userRepository.save(user);
            moderationLogService.logPromoteModerator(admin, user);
        }

        return user;
    }

    @Transactional
    public User demoteModerator(Long userId, Long adminId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        validateAdmin(admin);

        user.getRoles().remove("MODERATOR");
        userRepository.save(user);

        moderationLogService.logDemoteModerator(admin, user);
        return user;
    }

    @Transactional
    public Appeal createAppeal(Long userId, String reason, Long discussionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Appeal appeal = Appeal.builder()
                .user(user)
                .status(ifsc.edu.tpj.model.enums.AppealStatus.PENDING)
                .reason(reason)
                .build();

        return appealRepository.save(appeal);
    }

    @Transactional
    public Appeal reviewAppeal(Long appealId, Long adminId, boolean approved, String reviewNotes) {
        Appeal appeal = appealRepository.findById(appealId)
                .orElseThrow(() -> new RuntimeException("Appeal not found"));
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        validateAdmin(admin);

        appeal.setStatus(approved ? ifsc.edu.tpj.model.enums.AppealStatus.APPROVED : ifsc.edu.tpj.model.enums.AppealStatus.REJECTED);
        appeal.setReviewedBy(admin);
        appeal.setReviewNotes(reviewNotes);
        appeal.setReviewedAt(LocalDateTime.now());

        if (approved) {
            User user = appeal.getUser();
            user.setStatus(UserStatus.ACTIVE);
            user.setSuspendedUntil(null);
            user.setSuspensionReason(null);
            userRepository.save(user);

            moderationLogService.logUnbanUser(admin, user);
        }

        return appealRepository.save(appeal);
    }

    public List<Appeal> getPendingAppeals() {
        return appealRepository.findByStatus(ifsc.edu.tpj.model.enums.AppealStatus.PENDING);
    }

    public List<Appeal> getAppealsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return appealRepository.findByUser(user);
    }

    private void validateModerator(User user) {
        if (!user.getRoles().contains("MODERATOR") && !user.getRoles().contains("ADMIN")) {
            throw new RuntimeException("User must be a moderator or admin");
        }
    }

    private void validateAdmin(User user) {
        if (!user.getRoles().contains("ADMIN")) {
            throw new RuntimeException("User must be an admin");
        }
    }

    private void validateModeratorOrAuthor(User moderator, User author) {
        if (!moderator.getRoles().contains("MODERATOR") && !moderator.getRoles().contains("ADMIN") && !moderator.getUserId().equals(author.getUserId())) {
            throw new RuntimeException("User must be a moderator, admin, or the post author");
        }
    }
}
