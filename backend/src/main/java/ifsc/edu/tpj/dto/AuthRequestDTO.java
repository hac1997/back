package ifsc.edu.tpj.dto;

import ifsc.edu.tpj.util.ValidPasswd;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDTO(
        @Email @NotNull
        String email,
        @ValidPasswd @NotNull
        String passwd
) {
}
