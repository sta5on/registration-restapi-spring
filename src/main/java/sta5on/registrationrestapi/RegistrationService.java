package sta5on.registrationrestapi;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RegistrationService {

    private final Map<Long, User> userMap;

    private final AtomicLong idCounter;

    public RegistrationService() {
        userMap = new HashMap<>();
        idCounter = new AtomicLong();
    }

    public User getUserByID(
            Long id
    ) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("Not found reservation with id = " + id);
        } else {
            return userMap.get(id);
        }
    }
}
