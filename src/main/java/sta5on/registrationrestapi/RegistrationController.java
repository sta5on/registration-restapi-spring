package sta5on.registrationrestapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
