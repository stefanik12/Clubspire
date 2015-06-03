package cz.inspire.clubspire_02.POJO;

/**
 * Created by michal on 6/3/15.
 */
public class NewUser {
    private String name;
    private String surname;
    private String email;
    private boolean termsAccepted = true;
    private String login;
    private String password;
    private String passwordAgain;

    public String getName() {
        return name;
    }

    public NewUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public NewUser setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public NewUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public NewUser setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public NewUser setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public NewUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public NewUser setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
        return this;
    }
}
