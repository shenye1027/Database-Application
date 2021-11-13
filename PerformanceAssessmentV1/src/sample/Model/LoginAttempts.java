package sample.Model;

import java.time.LocalDateTime;

public class LoginAttempts {
    private int userID;
    private LocalDateTime loginDateTime;
    private boolean attemptResult;

    public LoginAttempts(int userID, LocalDateTime loginDateTime, boolean attemptResult) {
        this.userID = userID;
        this.loginDateTime = loginDateTime;
        this.attemptResult = attemptResult;
    }

    public int getUserID() {
        return userID;
    }

    public LocalDateTime getLoginDateTime() {
        return loginDateTime;
    }

    public boolean isAttemptResult() {
        return attemptResult;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setLoginDateTime(LocalDateTime loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public void setAttemptResult(boolean attemptResult) {
        this.attemptResult = attemptResult;
    }

    @Override
    public String toString() {
        return "LoginAttempts{" +
                "userID=" + userID +
                ", loginDateTime=" + loginDateTime +
                ", attemptResult=" + attemptResult +
                '}';
    }
}
