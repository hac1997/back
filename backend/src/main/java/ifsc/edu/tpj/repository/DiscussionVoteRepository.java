package ifsc.edu.tpj.repository;

import ifsc.edu.tpj.model.Discussion;
import ifsc.edu.tpj.model.DiscussionVote;
import ifsc.edu.tpj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscussionVoteRepository extends JpaRepository<DiscussionVote, Long> {
    List<DiscussionVote> findByDiscussion(Discussion discussion);
    Optional<DiscussionVote> findByDiscussionAndModerator(Discussion discussion, User moderator);
    long countByDiscussionAndInFavor(Discussion discussion, boolean inFavor);
}
