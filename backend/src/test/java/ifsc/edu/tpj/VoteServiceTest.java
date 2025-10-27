package ifsc.edu.tpj;

import ifsc.edu.tpj.dto.PostRequestDTO;
import ifsc.edu.tpj.dto.UserRequestDTO;
import ifsc.edu.tpj.model.Post;
import ifsc.edu.tpj.model.User;
import ifsc.edu.tpj.repository.PostRepository;
import ifsc.edu.tpj.repository.UserRepository;
import ifsc.edu.tpj.repository.VoteRepository;
import ifsc.edu.tpj.service.PostService;
import ifsc.edu.tpj.service.UserService;
import ifsc.edu.tpj.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Post testPost;

    @BeforeEach
    void setUp() {
        voteRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();

        testUser = userService.createUserAccount(new UserRequestDTO(
                "Test User",
                "voter@example.com",
                "SecurePass123!"
        ));

        testPost = postService.create(new PostRequestDTO(
                "Test Post",
                "Post content",
                List.of("test"),
                testUser.getUserId()
        ));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(testUser.getEmail(), null)
        );
    }

    @Test
    void toggleVote_ShouldAddVoteWhenNotExists() {
        long voteCount = voteService.toggleVote(testPost.getPostId());

        assertEquals(1, voteCount);
        assertTrue(voteRepository.findByUserAndPost(testUser, testPost).isPresent());
    }

    @Test
    void toggleVote_ShouldRemoveVoteWhenExists() {
        voteService.toggleVote(testPost.getPostId());

        long voteCount = voteService.toggleVote(testPost.getPostId());

        assertEquals(0, voteCount);
        assertFalse(voteRepository.findByUserAndPost(testUser, testPost).isPresent());
    }

    @Test
    void toggleVote_ShouldReturnCorrectVoteCount() {
        User anotherUser = userService.createUserAccount(new UserRequestDTO(
                "Another User",
                "another@example.com",
                "SecurePass123!"
        ));

        voteService.toggleVote(testPost.getPostId());

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(anotherUser.getEmail(), null)
        );
        long voteCount = voteService.toggleVote(testPost.getPostId());

        assertEquals(2, voteCount);
    }
}