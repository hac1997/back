package ifsc.edu.tpj.service;

import ifsc.edu.tpj.dto.PostRequestDTO;
import ifsc.edu.tpj.dto.PostSearchDTO;
import ifsc.edu.tpj.model.Comment;
import ifsc.edu.tpj.model.Post;
import ifsc.edu.tpj.model.PostSorting;
import ifsc.edu.tpj.model.Tag;
import ifsc.edu.tpj.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final TagService tagService;

    @Transactional
    public Post create(PostRequestDTO requestDTO) {
        var author = userService.findUserById(requestDTO.userId());
        return postRepository.save(Post.builder()
                        .title(requestDTO.title())
                        .tags(tagService.sanitizeTag(requestDTO.tags()))
                        .body(requestDTO.body())
                        .author(author)
                .build());
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado" + id));
    }

    @Transactional
    public Post update(Long id, PostRequestDTO dto){
        Post post = findById(id);

        this.isAuthor(post.getAuthor().getEmail());

        post.setBody(dto.body());
        post.setTitle(dto.title());
        post.setTags(tagService.sanitizeTag(dto.tags()));
        
        return postRepository.save(post);
    
    }

    // futuramente, quando implementarmos as ferramentas de moderação,
    // devemos incluir a possibilidade de deletar ou
    // tirar fora de visualização um comentário
    @Transactional
    public Post delete(Long id){
        Post post = findById(id);
        this.isAuthor(post.getAuthor().getEmail());

        postRepository.delete(post);
        return post;
    }

    @Transactional
    public Post pinCorrectAnswer(Long commentId, Long postId) {
        Comment comment = commentService.findById(commentId);
        Post post = this.findById(postId);
        post.setAnswerId(commentId);

        this.isAuthor(post.getAuthor().getEmail());
        if (!comment.getPost().equals(post)) {
            throw new RuntimeException("YOU CANNOT INTERACT WITH OTHER POST`S COMMENTS");
        }

        return postRepository.save(post);
    }

    protected void isAuthor(String email) {
        if (!SecurityContextHolder.getContext()
                .getAuthentication().getName().equals(email)
        ) {
            throw new RuntimeException("YOU MUST BE AUTHOR TO INTERACT WITH THIS");
        }
    }

    



    public List<Post> searchPostsAdvanced(PostSearchDTO filters) {
        PostSorting sorting = PostSorting.MOST_VOTED;
        if (filters.sorting() != null) {
            sorting = filters.sorting();
        }

        if (filters.createdAfter() != null && filters.createdBefore() != null
                && filters.createdAfter().isAfter(filters.createdBefore())) {
            throw new IllegalArgumentException("createdAfter CANNOT BE AFTER createdBefore PARAMS");
        }

        return postRepository.searchAdvanced(
                filters.keyword(),
                filters.tags(),
                filters.createdAfter() != null ? filters.createdAfter().atStartOfDay() : null,
                filters.createdBefore() != null ? filters.createdBefore().atTime(23, 59, 59) : null,
                filters.minComments(),
                filters.maxComments(),
                sorting,
                (long) filters.tags().size()
        );
    }
}
