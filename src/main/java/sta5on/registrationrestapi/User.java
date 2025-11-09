package sta5on.registrationrestapi;

import java.time.LocalDateTime;

public record User(
        Long id,
        String username,
        String password,
        LocalDateTime regDateTime
) {
}
