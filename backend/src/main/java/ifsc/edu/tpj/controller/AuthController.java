package ifsc.edu.tpj.controller;

import ifsc.edu.tpj.dto.AuthRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000",  allowCredentials = "true")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.passwd()
                )
        );

        return authentication.isAuthenticated() ? "Login successful" : "Invalid credentials";
    }
}
