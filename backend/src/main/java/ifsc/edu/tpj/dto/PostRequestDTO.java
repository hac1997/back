package ifsc.edu.tpj.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PostRequestDTO(
        @NotBlank @NotNull
        String title,
        @NotBlank @NotNull
        String body,
        @NotNull
        List<String> tags,
        @NotNull
        Long userId
) {
}
