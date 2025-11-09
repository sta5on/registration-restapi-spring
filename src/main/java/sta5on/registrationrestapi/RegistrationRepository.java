package sta5on.registrationrestapi;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<UserEntity, Long> {
@Transactional
@Modifying
@Query("""
    update UserEntity u
    set u.role = :role
    where u.id = :id
""")
    void setRole(@Param("id") Long id, @Param("role") UserRole role);

    @Query("select u from UserEntity u where u.username = :username")
    Optional<User> findByUsername(String username);
}
