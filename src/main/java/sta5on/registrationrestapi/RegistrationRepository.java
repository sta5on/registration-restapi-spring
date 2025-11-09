package sta5on.registrationrestapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<UserEntity, Long> {
//
}
