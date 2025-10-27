package ifsc.edu.tpj.dto;

public record DiscussionVoteRequestDTO(
        Long discussionId,
        Long moderatorId,
        boolean inFavor,
        String justification
) {}
