package no.gainmaster.user.persistence.entity;

import no.gainmaster.user.service.User;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "user")
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
public class UserEntity implements User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name="password", nullable=false, length=255)
    private String password;

    @Column(name = "salt", nullable = false, length = 255)
    private String salt;

    @Column(name = "created", nullable = false)
    private Date created;


    public UserEntity() {}

    public Long getUserId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return String.format(
            "User[id=%d, name=%s, username=%s, email=%s, password=%s, salt=%s, created=%s]",
            id, name, username, email, password, salt, created
        );
    }

}
