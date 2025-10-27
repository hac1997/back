package ifsc.edu.tpj.dto;

import ifsc.edu.tpj.model.User;

public record UserResponseDTO(
    Long userId,
    String username,
    String email
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
            user.getUserId(),
            user.getName(),
            user.getEmail()
        );
    }
}
