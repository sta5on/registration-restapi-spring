package sta5on.registrationrestapi;

import java.time.LocalDate;

public record User(
        Long id,
        String username,
        String password,
        LocalDate regTime
) {
}
