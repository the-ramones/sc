package sc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Paul Kulitski
 */
@Entity
@Table(name = "rememberme")
public class Token {

    private String username;
    private transient String token;

    public Token() {
    }

    public Token(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Id
    @Column(name = "username", length = 40)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "token", length = 40)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" + "username=" + username + ", token=" + token + '}';
    }
}
