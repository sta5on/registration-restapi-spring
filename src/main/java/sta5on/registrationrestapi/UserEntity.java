package sta5on.registrationrestapi;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email", nullable = false)
    String email;
    @Column(name = "username", nullable = false)
    String username;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "regDateTime")
    LocalDateTime regDateTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    UserRole role;

    public UserEntity(Long id, String email, String username, String password, LocalDateTime regDateTime, UserRole role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.regDateTime = regDateTime;
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegDateTime(LocalDateTime regDateTime) {
        this.regDateTime = regDateTime;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getRegDateTime() {
        return regDateTime;
    }

    public UserRole getRole() {
        return role;
    }
}
