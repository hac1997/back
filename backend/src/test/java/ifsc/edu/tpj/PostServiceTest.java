package ifsc.edu.tpj;

import ifsc.edu.tpj.dto.PostRequestDTO;
import ifsc.edu.tpj.dto.UserRequestDTO;
import ifsc.edu.tpj.model.Post;
import ifsc.edu.tpj.model.Tag;
import ifsc.edu.tpj.model.User;
import ifsc.edu.tpj.repository.PostRepository;
import ifsc.edu.tpj.repository.UserRepository;
import ifsc.edu.tpj.service.PostService;
import ifsc.edu.tpj.service.UserService;
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
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private PostRequestDTO testPostDTO;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();

        testUser = userService.createUserAccount(new UserRequestDTO(
                "Post Author",
                "author@example.com",
                "SecurePass123!"
        ));

        testPostDTO = new PostRequestDTO(
                "Test Post Title",
                "This is the body of the test post",
                List.of("java", "spring", "testing"),
                testUser.getUserId()
        );

        // Set up authentication for tests that require it
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(testUser.getEmail(), null)
        );
    }

    @Test
    void create_ShouldCreatePostSuccessfully() {
        Post createdPost = postService.create(testPostDTO);

        assertNotNull(createdPost);
        assertNotNull(createdPost.getPostId());
        assertEquals("Test Post Title", createdPost.getTitle());
        assertEquals("This is the body of the test post", createdPost.getBody());

        List<String> tagNames = createdPost.getTags().stream()
                .map(Tag::getName)
                .sorted()
                .toList();

        assertEquals(List.of("java", "spring", "testing").stream().sorted().toList(), tagNames);
        assertEquals(testUser.getUserId(), createdPost.getAuthor().getUserId());
        assertNotNull(createdPost.getCreatedAt());
    }


    @Test
    void findById_WithExistingPost_ShouldReturnPost() {
        Post createdPost = postService.create(testPostDTO);

        Post foundPost = postService.findById(createdPost.getPostId());

        assertNotNull(foundPost);
        assertEquals(createdPost.getPostId(), foundPost.getPostId());
        assertEquals(createdPost.getTitle(), foundPost.getTitle());
    }

    @Test
    void findById_WithNonExistingPost_ShouldThrowException() {
        assertThrows(RuntimeException.class, () -> postService.findById(999L));
    }

    @Test
    void update_ShouldUpdatePostSuccessfully() {
        Post createdPost = postService.create(testPostDTO);
        PostRequestDTO updateDTO = new PostRequestDTO(
                "Updated Title",
                "Updated body content",
                List.of("updated", "tags"),
                testUser.getUserId()
        );

        Post updatedPost = postService.update(createdPost.getPostId(), updateDTO);

        assertNotNull(updatedPost);
        assertEquals("Updated Title", updatedPost.getTitle());
        assertEquals("Updated body content", updatedPost.getBody());

        List<String> tagNames = updatedPost.getTags().stream()
                .map(Tag::getName)
                .toList();

        assertEquals(List.of("updated", "tags"), tagNames);

        assertEquals(createdPost.getPostId(), updatedPost.getPostId());
    }


    @Test
    void update_WithDifferentUser_ShouldThrowException() {
        Post createdPost = postService.create(testPostDTO);

        User anotherUser = userService.createUserAccount(new UserRequestDTO(
                "Another User",
                "another@example.com",
                "SecurePass123!"
        ));

        PostRequestDTO updateDTO = new PostRequestDTO(
                "Updated Title",
                "Updated body",
                List.of("tag"),
                anotherUser.getUserId()
        );

        // Differnt Auth User
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("another@example.com", null)
        );

        assertThrows(RuntimeException.class,
                () -> postService.update(createdPost.getPostId(), updateDTO));
    }

    @Test
    void delete_ShouldDeletePostSuccessfully() {
        Post createdPost = postService.create(testPostDTO);
        Long postId = createdPost.getPostId();

        Post deletedPost = postService.delete(postId);

        assertNotNull(deletedPost);
        assertEquals(postId, deletedPost.getPostId());
        assertThrows(RuntimeException.class, () -> postService.findById(postId));
    }
}