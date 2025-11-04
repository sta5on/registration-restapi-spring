package sta5on.registrationrestapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    public List<User> getAllUsers() {
        if (userMap.isEmpty()) {
            throw new NoSuchElementException("Users list is empty");
        }
        return userMap.values().stream().toList();
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

    public User createUser(User userToCreate) {
        if (userToCreate.id() != null) {
            throw new IllegalArgumentException("User id must be empty");
        }
        var newUser = new User(
                idCounter.incrementAndGet(),
                userToCreate.username(),
                userToCreate.password(),
                LocalDate.now()
        );
        userMap.put(newUser.id(), newUser);
        return newUser;
    }

    public void deleteUser(Long id) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("Not found user with id: " + id);
        }
        userMap.remove(id);
    }

    public User changeUsername(Long id, User usernameToChange) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("Not found User with id: " + id);
        }

        var thisUser = userMap.get(id);
        var updUser = new User(
                thisUser.id(),
                usernameToChange.username(),
                thisUser.password(),
                thisUser.regTime()
        );
                userMap.put(id, updUser);
        return updUser;
    }

    public User changePassword(Long id, User passwordToChange) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("Not found User with id: " + id);
        }

        var thisUser = userMap.get(id);
        var updUser = new User(
                thisUser.id(),
                thisUser.username(),
                passwordToChange.password(),
                thisUser.regTime()
        );
        userMap.put(id, updUser);
        return updUser;
    }
}
