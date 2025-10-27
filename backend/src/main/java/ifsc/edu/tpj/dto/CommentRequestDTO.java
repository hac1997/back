package ifsc.edu.tpj.dto;

public record CommentRequestDTO(
        String body,
        Long postId,
        Long userId
) {
}