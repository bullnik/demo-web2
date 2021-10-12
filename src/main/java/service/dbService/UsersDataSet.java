package service.dbService;

import model.UserProfile;
import javax.persistence.*;

@Entity
@Table(name="users")
public class UsersDataSet {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;

    public UsersDataSet() {

    }

    public UsersDataSet(UserProfile userProfile) {
        this.login = userProfile.getLogin();
        this.password = userProfile.getPass();
        this.email = userProfile.getEmail();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
