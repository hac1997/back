package ifsc.edu.tpj.repository;

import ifsc.edu.tpj.model.Post;
import ifsc.edu.tpj.model.User;
import ifsc.edu.tpj.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserAndPost(User user, Post post);

    long countByPostAndVoteTrue(Post post); // upvote counting
}
