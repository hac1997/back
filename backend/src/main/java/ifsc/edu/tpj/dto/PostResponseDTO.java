package ifsc.edu.tpj.dto;

import java.time.LocalDateTime;
import java.util.List;

import ifsc.edu.tpj.model.Post;
import ifsc.edu.tpj.model.Tag;

public record PostResponseDTO (
    Long postId,
    String title,
    String body,
    List<String> tags,
    LocalDateTime createdAt,
    UserResponseDTO author
) {
    public static PostResponseDTO fromEntity(Post post){
        if (post == null) {
            return null;
        }

        return new PostResponseDTO(
          post.getPostId(),
          post.getTitle(),
          post.getBody(),
          post.getTags().stream()
                  .map(Tag::getName)
                  .toList(),
          post.getCreatedAt(),
          UserResponseDTO.fromEntity(post.getAuthor()));

    }
}
