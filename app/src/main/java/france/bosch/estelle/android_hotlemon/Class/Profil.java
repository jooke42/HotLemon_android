package france.bosch.estelle.android_hotlemon.Class;

/**
 * Created by ESTEL on 26/04/2017.
 */

public class Profil {

    String email, pseudo, firstname, lastname;

    public Profil(String email, String pseudo, String firstname, String lastname) {
        this.email = email;
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


}
