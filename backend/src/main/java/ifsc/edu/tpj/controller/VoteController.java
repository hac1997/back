package ifsc.edu.tpj.controller;

import ifsc.edu.tpj.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
@CrossOrigin(origins = "http://localhost:3000",  allowCredentials = "true")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{postId}/toggle")
    @PreAuthorize("isAuthenticated()")
    public long toggleVote(@PathVariable Long postId) {
        return voteService.toggleVote(postId);
    }
}
