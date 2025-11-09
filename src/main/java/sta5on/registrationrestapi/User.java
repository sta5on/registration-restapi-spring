package sta5on.registrationrestapi;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record User(
        @Null
        Long id,
        @NotNull
        String username,
        @NotNull
        String password,
        LocalDateTime regDateTime,
        UserRole role
) {
}
