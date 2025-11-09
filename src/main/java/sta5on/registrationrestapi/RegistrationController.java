package sta5on.registrationrestapi;

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
        log.info("Called method getUserByID, id: " + id);
        try {
            return ResponseEntity.ok().body(registrationService.getUserByID(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Called method getAllUsers");
        return registrationService.getAllUsers();
    }

    @PostMapping
    public User createUser(
            @RequestBody User userToCreate
    ) {
        log.info("Called method createUser");
        return registrationService.createUser(userToCreate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long id
    ) {
        log.info("Called method deleteUser, id: " + id);
        try {
            registrationService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();
        }
    }
//
//    @PutMapping("/change/username/{id}")
//    public ResponseEntity<User> changeUsername(
//            @PathVariable("id") Long id,
//            @RequestBody User usernameToChange
//    ) {
//        log.info("Called method changeUsername, with User ID: " + id);
//        var updated = registrationService.changeUsername(id, usernameToChange);
//        return ResponseEntity.ok(updated);
//    }


    @PutMapping("/change/password/{id}")
    public ResponseEntity<User> changePassword(
            @PathVariable("id") Long id,
            @RequestBody User passwordToChange
    ) {
        log.info("Called method changePassword, with User ID: " + id);
        var updated = registrationService.changePassword(id, passwordToChange);
        return ResponseEntity.ok(updated);
    }

}
