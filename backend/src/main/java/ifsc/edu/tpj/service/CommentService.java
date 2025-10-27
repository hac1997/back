package ifsc.edu.tpj.service;

import ifsc.edu.tpj.dto.CommentRequestDTO;
import ifsc.edu.tpj.model.Comment;
import ifsc.edu.tpj.model.Post;
import ifsc.edu.tpj.repository.CommentRepository;
import ifsc.edu.tpj.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostRepository postRepository;

    @Transactional
    public Comment create(Long postId, CommentRequestDTO dto){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not found: " + postId));

        if(post.isLocked()){
            throw new RuntimeException("Post is locked and cannot receive new comments");
        }

        return commentRepository.save(Comment.builder()
                        .author(userService.findUserById(dto.userId()))
                        .body(dto.body())
                        .post(post)
                        .visible(true)
                        .build());
    }


    public List<Comment> findByPost(Long postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    public Comment findById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
    }

    @Transactional
    public Comment update(Long id, CommentRequestDTO dto){
        Comment comment = findById(id);

        this.isAuthor(comment.getAuthor().getEmail());
        if (!comment.getAuthor().getUserId().equals(dto.userId())) {
            throw new RuntimeException("Somente o autor pode editar esse comentário");
        }
        
        comment.setBody(dto.body());
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public Comment delete(Long id){
        Comment comment = findById(id);
        this.isAuthor(comment.getAuthor().getEmail());
        
        commentRepository.delete(comment);
        return comment;
    }

    protected void isAuthor(String email) {
        if (!SecurityContextHolder.getContext()
                .getAuthentication().getName().equals(email)
        ) {
            throw new RuntimeException("YOU MUST BE AUTHOR TO INTERACT WITH THIS");
        }
    }
}
