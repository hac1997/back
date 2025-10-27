package ifsc.edu.tpj.dto;

import java.time.LocalDateTime;

import ifsc.edu.tpj.model.Comment;

public record CommentResponseDTO(
    Long commentId,
    String body,
    UserResponseDTO user,
    LocalDateTime createdAt
) {
    public static CommentResponseDTO fromEntity(Comment comment){
        if (comment == null) {
            return null;
        }

        return new CommentResponseDTO(
            comment.getCommentId(),
            comment.getBody(),
            UserResponseDTO.fromEntity(comment.getAuthor()),
            comment.getCreatedAt()
        );
    }
}