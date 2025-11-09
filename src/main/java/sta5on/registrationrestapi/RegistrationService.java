package sta5on.registrationrestapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private RegistrationRepository repository;

    private final Map<Long, User> userMap;

    private final AtomicLong idCounter;

    public RegistrationService(RegistrationRepository repository) {
        this.repository = repository;
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
        var newUser = new UserEntity(
                null,
                userToCreate.username(),
                userToCreate.password(),
                LocalDateTime.now()
        );
        var isConflict = isUserConflict(newUser);

        if (isConflict) {
            throw new IllegalStateException("Cannot create new user, username is taken");
        }

        var savedUser = repository.save(newUser);

//        userMap.put(newUser.id(), newUser);
        return toDomainUser(savedUser);
    }

    public void deleteUser(Long id) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("Not found user with id: " + id);
        }
        userMap.remove(id);
    }

//    public User changeUsername(Long id, User usernameToChange) {
//        if (!userMap.containsKey(id)) {
//            throw new NoSuchElementException("Not found User with id: " + id);
//        }
//
//        var thisUser = userMap.get(id);
//        var updUser = new UserEntity(
//                thisUser.id(),
//                usernameToChange.username(),
//                thisUser.password(),
//                thisUser.regDateTime()
//        );
//
//        var isConflict = isUserConflict(updUser);
//
//        if (isConflict) {
//            throw new IllegalStateException("Cannot create new user, username is taken");
//        }
//
//        userMap.put(id, updUser);
//        return updUser;
//    }

    public User changePassword(Long id, User passwordToChange) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("Not found User with id: " + id);
        }

        var thisUser = userMap.get(id);
        var updUser = new User(
                thisUser.id(),
                thisUser.username(),
                passwordToChange.password(),
                thisUser.regDateTime()
        );
        userMap.put(id, updUser);
        return updUser;
    }

    private boolean isUserConflict(UserEntity user) {
        var allUsers = repository.findAll();
        for (UserEntity existingUser : allUsers) {
            if (user.getUsername().equals(existingUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    private User toDomainUser(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRegDateTime()
        );
    }
}
