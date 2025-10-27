package ifsc.edu.tpj.model;

import ifsc.edu.tpj.model.enums.DiscussionStatus;
import ifsc.edu.tpj.model.enums.DiscussionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discussions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discussionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscussionType discussionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscussionStatus status;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report relatedReport;

    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @ManyToOne
    @JoinColumn(name = "target_post_id")
    private Post targetPost;

    @ManyToOne
    @JoinColumn(name = "target_comment_id")
    private Comment targetComment;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscussionVote> votes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    @Column(columnDefinition = "TEXT")
    private String resolution;
}
