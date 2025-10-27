package ifsc.edu.tpj.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifsc.edu.tpj.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_PostId(Long postId);
}
