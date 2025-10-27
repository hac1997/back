package ifsc.edu.tpj.controller;

import ifsc.edu.tpj.dto.UserRequestDTO;
import ifsc.edu.tpj.dto.UserResponseDTO;
import ifsc.edu.tpj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000",  allowCredentials = "true")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUserAccount(
            @RequestBody UserRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                UserResponseDTO.fromEntity(userService.createUserAccount(requestDTO))
        );
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyUserEmail(
            @RequestParam String email,
            @RequestParam String code
    ) {
        boolean verified = userService.verifyUserEmail(email, code);
        if (verified) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(400).body("Invalid verification code or email.");
        }
    }
}
