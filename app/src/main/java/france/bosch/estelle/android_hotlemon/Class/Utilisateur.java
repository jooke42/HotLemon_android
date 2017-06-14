package france.bosch.estelle.android_hotlemon.Class;

/**
 * Created by Flavinou on 09/06/2017.
 */

public class Utilisateur {
    String username, email, password;

    public Utilisateur() {}

    public Utilisateur(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Utilisateur(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
}
