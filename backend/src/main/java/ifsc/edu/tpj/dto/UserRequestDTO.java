package ifsc.edu.tpj.dto;

import ifsc.edu.tpj.util.ValidPasswd;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
        @NotNull @NotBlank
        String name,
        @NotNull @Email
        String email,
        @NotNull @ValidPasswd
        String passwd
) {
}
