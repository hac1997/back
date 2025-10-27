package ifsc.edu.tpj.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votes",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "post_id"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    private boolean vote; // true = upvote, false = downvote


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
