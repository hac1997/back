package ifsc.edu.tpj.dto;

public record AppealRequestDTO(
        Long userId,
        String reason,
        Long discussionId
) {}
