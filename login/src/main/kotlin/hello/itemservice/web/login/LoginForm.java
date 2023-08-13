package hello.itemservice.web.login;

import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginForm loginForm)) return false;

        if (!Objects.equals(loginId, loginForm.loginId)) return false;
        return Objects.equals(password, loginForm.password);
    }

    @Override
    public int hashCode() {
        int result = loginId != null ? loginId.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
