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

    @Column(name = "username", nullable = false)
    String username;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "regDateTime")
    LocalDateTime regDateTime;

    public UserEntity() {
    }

    public UserEntity(Long id, String username, String password, LocalDateTime regDateTime) {
        this.regDateTime = regDateTime;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
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
}
