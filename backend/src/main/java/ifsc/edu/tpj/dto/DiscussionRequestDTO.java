package ifsc.edu.tpj.dto;

import ifsc.edu.tpj.model.enums.DiscussionType;

public record DiscussionRequestDTO(
        Long creatorId,
        DiscussionType discussionType,
        String description,
        Long reportId,
        Long targetUserId,
        Long targetPostId,
        Long targetCommentId
) {}
