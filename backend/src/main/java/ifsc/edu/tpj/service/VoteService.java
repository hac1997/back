package ifsc.edu.tpj.service;

import ifsc.edu.tpj.model.Post;
import ifsc.edu.tpj.model.User;
import ifsc.edu.tpj.model.Vote;
import ifsc.edu.tpj.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional
    public long toggleVote(Long postId) {
        User user = userService.findUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        Post post = postService.findById(postId);
        var exists = voteRepository.findByUserAndPost(user, post);

        if (exists.isPresent()) {
            voteRepository.delete(exists.get());
        } else {
            voteRepository.save(
                    Vote.builder()
                            .post(post)
                            .user(user)
                            .vote(true)
                            .build()
            );
        }
        return voteRepository.countByPostAndVoteTrue(post);
    }
}
