package ifsc.edu.tpj.controller;

import ifsc.edu.tpj.model.Appeal;
import ifsc.edu.tpj.model.Discussion;
import ifsc.edu.tpj.model.User;
import ifsc.edu.tpj.service.DiscussionService;
import ifsc.edu.tpj.service.ModerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ModerationService moderationService;
    private final DiscussionService discussionService;

    @PostMapping("/users/{userId}/promote")
    public ResponseEntity<User> promoteModerator(@PathVariable Long userId, @RequestParam Long adminId) {
        return ResponseEntity.ok(moderationService.promoteModerator(userId, adminId));
    }

    @PostMapping("/users/{userId}/demote")
    public ResponseEntity<User> demoteModerator(@PathVariable Long userId, @RequestParam Long adminId) {
        return ResponseEntity.ok(moderationService.demoteModerator(userId, adminId));
    }

    @PostMapping("/users/{userId}/restore")
    public ResponseEntity<User> restoreUser(@PathVariable Long userId, @RequestParam Long adminId) {
        return ResponseEntity.ok(moderationService.restoreUser(userId, adminId));
    }

    @PostMapping("/discussions/{discussionId}/close")
    public ResponseEntity<Discussion> closeDiscussion(@PathVariable Long discussionId, @RequestParam Long adminId) {
        return ResponseEntity.ok(discussionService.closeDiscussion(discussionId, adminId));
    }

    @PostMapping("/appeals/{appealId}/review")
    public ResponseEntity<Appeal> reviewAppeal(@PathVariable Long appealId, @RequestParam Long adminId, @RequestParam boolean approved, @RequestParam String reviewNotes) {
        return ResponseEntity.ok(moderationService.reviewAppeal(appealId, adminId, approved, reviewNotes));
    }
}
