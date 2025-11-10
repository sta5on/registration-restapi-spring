package sta5on.registrationrestapi;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(
            @PathVariable("id") Long id
    ) {
        log.info("Called method getUserByID, id: {}", id);
        return ResponseEntity.ok().body(registrationService.getUserByID(id));
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Called method getAllUsers");
        return registrationService.getAllUsers();
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(
            @PathVariable("username") String username
    ) {
        log.info("Called methos getUserByUsername, with username = {}", username);
        return ResponseEntity.ok().body(registrationService.getUserByUsername(username));
    }

    @PostMapping
    public User createUser(
            @RequestBody @Valid User userToCreate
    ) {
        log.info("Called method createUser");
        return registrationService.createUser(userToCreate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long id
    ) {
        log.info("Called method deleteUser, id: {}", id);
        registrationService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change/username/{id}")
    public ResponseEntity<User> changeUsername(
            @PathVariable("id") Long id,
            @RequestBody @Valid User usernameToChange
    ) {
        log.info("Called method changeUsername, with User ID: {}", id);
        var updated = registrationService.changeUsername(id, usernameToChange);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/change/role/{id}/{role}")
    public ResponseEntity<User> changeRole(
            @PathVariable("id") Long id,
            @PathVariable("role") String role,
            @RequestBody String isAdmin
    ) {
        log.info("Called method changeRole, with User ID: {}, role: {}", id, role);
        var updated = registrationService.changeRole(id, role, isAdmin);
        return ResponseEntity.ok(updated);
    }


    @PutMapping("/change/password/{id}")
    public ResponseEntity<User> changePassword(
            @PathVariable("id") Long id,
            @RequestBody @Valid User passwordToChange
    ) {
        log.info("Called method changePassword, with User ID: {}", id);
        var updated = registrationService.changePassword(id, passwordToChange);
        return ResponseEntity.ok(updated);
    }
}
