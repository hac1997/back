package ifsc.edu.tpj.service;

import ifsc.edu.tpj.model.*;
import ifsc.edu.tpj.model.enums.ModerationAction;
import ifsc.edu.tpj.repository.ModerationLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModerationLogService {

    private final ModerationLogRepository logRepository;

    @Transactional
    public ModerationLog logAction(ModerationAction action, User moderator, User targetUser, Post targetPost, Comment targetComment, Discussion relatedDiscussion, String notes) {
        ModerationLog log = ModerationLog.builder()
                .action(action)
                .moderator(moderator)
                .targetUser(targetUser)
                .targetPost(targetPost)
                .targetComment(targetComment)
                .relatedDiscussion(relatedDiscussion)
                .notes(notes)
                .build();

        return logRepository.save(log);
    }

    @Transactional
    public ModerationLog logLockPost(User moderator, Post post) {
        return logAction(ModerationAction.LOCK_POST, moderator, null, post, null, null, "Post locked");
    }

    @Transactional
    public ModerationLog logUnlockPost(User moderator, Post post) {
        return logAction(ModerationAction.UNLOCK_POST, moderator, null, post, null, null, "Post unlocked");
    }

    @Transactional
    public ModerationLog logPinComment(User moderator, Comment comment) {
        return logAction(ModerationAction.PIN_COMMENT, moderator, null, null, comment, null, "Comment pinned");
    }

    @Transactional
    public ModerationLog logUnpinComment(User moderator, Comment comment) {
        return logAction(ModerationAction.UNPIN_COMMENT, moderator, null, null, comment, null, "Comment unpinned");
    }

    @Transactional
    public ModerationLog logDeletePost(User moderator, Post post) {
        return logAction(ModerationAction.DELETE_POST, moderator, post.getAuthor(), post, null, null, "Post deleted");
    }

    @Transactional
    public ModerationLog logDeleteComment(User moderator, Comment comment) {
        return logAction(ModerationAction.DELETE_COMMENT, moderator, comment.getAuthor(), null, comment, null, "Comment deleted");
    }

    @Transactional
    public ModerationLog logRestorePost(User moderator, Post post) {
        return logAction(ModerationAction.RESTORE_POST, moderator, post.getAuthor(), post, null, null, "Post restored");
    }

    @Transactional
    public ModerationLog logRestoreComment(User moderator, Comment comment) {
        return logAction(ModerationAction.RESTORE_COMMENT, moderator, comment.getAuthor(), null, comment, null, "Comment restored");
    }

    @Transactional
    public ModerationLog logBanUser(User moderator, User targetUser, Discussion discussion) {
        return logAction(ModerationAction.BAN_USER, moderator, targetUser, null, null, discussion, "User banned");
    }

    @Transactional
    public ModerationLog logUnbanUser(User admin, User targetUser) {
        return logAction(ModerationAction.UNBAN_USER, admin, targetUser, null, null, null, "User unbanned");
    }

    @Transactional
    public ModerationLog logSuspendUser(User moderator, User targetUser, Discussion discussion) {
        return logAction(ModerationAction.SUSPEND_USER, moderator, targetUser, null, null, discussion, "User suspended");
    }

    @Transactional
    public ModerationLog logUnsuspendUser(User admin, User targetUser) {
        return logAction(ModerationAction.UNSUSPEND_USER, admin, targetUser, null, null, null, "User unsuspended");
    }

    @Transactional
    public ModerationLog logWarnUser(User moderator, User targetUser, String reason) {
        return logAction(ModerationAction.WARN_USER, moderator, targetUser, null, null, null, "Warning: " + reason);
    }

    @Transactional
    public ModerationLog logPromoteModerator(User admin, User targetUser) {
        return logAction(ModerationAction.PROMOTE_MODERATOR, admin, targetUser, null, null, null, "User promoted to moderator");
    }

    @Transactional
    public ModerationLog logDemoteModerator(User admin, User targetUser) {
        return logAction(ModerationAction.DEMOTE_MODERATOR, admin, targetUser, null, null, null, "User demoted from moderator");
    }

    public List<ModerationLog> getLogsByUser(User user) {
        return logRepository.findByTargetUser(user);
    }

    public List<ModerationLog> getLogsByModerator(User moderator) {
        return logRepository.findByModerator(moderator);
    }

    public List<ModerationLog> getAllLogs() {
        return logRepository.findAll();
    }
}
