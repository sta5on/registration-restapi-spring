package sta5on.registrationrestapi;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record User(
        @Null
        Long id,
        @NotNull
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9._]{3,32}$",
                message = "Username: only Latin letters, digits, dot, underscore; length 3-32")
        String username,
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9]{8,64}$",
                message = "Password: only Latin letters and digits; length 8-64")
        String password,
        LocalDateTime regDateTime,
        UserRole role
) {
}
