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

    public RegistrationService(RegistrationRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        List<UserEntity> allEntities = repository.findAll();

        List<User> allUsers = allEntities.stream().map(this::toDomainUser).toList();

        return allUsers;
    }

    public User getUserByID(
            Long id
    ) {
        var userEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found user with id = " + id));

        return toDomainUser(userEntity);
    }


    public User getUserByUsername(String username) {
        var userEntity = repository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Not found user with id = " + username));

        return userEntity;
    }

    public User createUser(User userToCreate) {
        if (userToCreate.id() != null) {
            throw new IllegalArgumentException("User id must be empty");
        }
        var newUser = new UserEntity(
                null,
                userToCreate.username(),
                userToCreate.password(),
                LocalDateTime.now(),
                UserRole.DEFAULT
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
        var userEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found user with id = " + id));

        repository.setRole(id, UserRole.DELETED);
    }

    public User changeUsername(Long id, User usernameToChange) {
        var userEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found user with id = " + id));

        var updUser = new UserEntity(
                userEntity.getId(),
                usernameToChange.username(),
                userEntity.getPassword(),
                userEntity.getRegDateTime(),
                userEntity.getRole()
        );

        var isConflict = isUserConflict(updUser);

        if (isConflict) {
            throw new IllegalStateException("Cannot create new user, username is taken");
        }

        var updatedUser = repository.save(updUser);
        return toDomainUser(updatedUser);
    }

    public User changePassword(Long id, User passwordToChange) {
        var userEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found user with id = " + id));

        var updUser = new UserEntity(
                userEntity.getId(),
                userEntity.getUsername(),
                passwordToChange.password(),
                userEntity.getRegDateTime(),
                userEntity.getRole()
        );

        var savedUser = repository.save(updUser);
        return toDomainUser(savedUser);
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
                entity.getRegDateTime(),
                entity.getRole()
        );
    }
}
