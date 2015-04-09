package gainmaster.service.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="username", nullable=false, length=255)
    private String username;

    @Column(name="password", nullable=false, length=255)
    private String password;

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format(
            "User[id=%d, username=%s, password=%s]",
            id, username, password
        );
    }

}
