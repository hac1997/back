package ifsc.edu.tpj;

import ifsc.edu.tpj.dto.UserRequestDTO;
import ifsc.edu.tpj.model.User;
import ifsc.edu.tpj.repository.UserRepository;
import ifsc.edu.tpj.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private ifsc.edu.tpj.service.MailService mailService;

    private UserRequestDTO testUserDTO;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUserDTO = new UserRequestDTO(
                "Test User",
                "test@example.com",
                "SecurePass123!"
        );
    }

    @Test
    void createUserAccount_ShouldCreateUserAndSendVerificationEmail() {
        User createdUser = userService.createUserAccount(testUserDTO);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getUserId());
        assertEquals("Test User", createdUser.getName());
        assertEquals("test@example.com", createdUser.getEmail());
        assertNotNull(createdUser.getPassword());
        assertFalse(createdUser.isEmailVerified());
        assertNotNull(createdUser.getEmailVerificationCode());
        assertEquals(6, createdUser.getEmailVerificationCode().length());
    }

    @Test
    void verifyUserEmail_WithValidCode_ShouldVerifyEmail() {
        User createdUser = userService.createUserAccount(testUserDTO);
        String verificationCode = createdUser.getEmailVerificationCode();

        Boolean result = userService.verifyUserEmail("test@example.com", verificationCode);

        assertTrue(result);

        User verifiedUser = userService.findUserByEmail("test@example.com");
        assertTrue(verifiedUser.isEmailVerified());
        assertNull(verifiedUser.getEmailVerificationCode());
    }

    @Test
    void verifyUserEmail_WithInvalidCode_ShouldReturnFalse() {
        userService.createUserAccount(testUserDTO);

        Boolean result = userService.verifyUserEmail("test@example.com", "INVALID");

        assertFalse(result);

        User user = userService.findUserByEmail("test@example.com");
        assertFalse(user.isEmailVerified());
        assertNotNull(user.getEmailVerificationCode());
    }

    @Test
    void findUserById_WithExistingUser_ShouldReturnUser() {
        User createdUser = userService.createUserAccount(testUserDTO);

        User foundUser = userService.findUserById(createdUser.getUserId());

        assertNotNull(foundUser);
        assertEquals(createdUser.getUserId(), foundUser.getUserId());
        assertEquals(createdUser.getName(), foundUser.getName());
    }

    @Test
    void findUserById_WithNonExistingUser_ShouldThrowException() {
        assertThrows(RuntimeException.class, () -> userService.findUserById(999L));
    }

    @Test
    void findUserByEmail_WithExistingUser_ShouldReturnUser() {
        userService.createUserAccount(testUserDTO);

        User foundUser = userService.findUserByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void findUserByEmail_WithNonExistingUser_ShouldThrowException() {
        assertThrows(RuntimeException.class, () -> userService.findUserByEmail("nonexisting@example.com"));
    }
}