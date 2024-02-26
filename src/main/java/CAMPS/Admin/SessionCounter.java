package CAMPS.Admin;

import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.http.HttpSessionEvent;

public class SessionCounter implements HttpSessionListener {

    private static int activeSessions = 0;

    public void sessionCreated(HttpSessionEvent se) {
        activeSessions++;
    }
    public void sessionDestroyed(HttpSessionEvent se) {

        if (activeSessions > 0) {
            activeSessions--;
        }
    }
    public static int getActiveSessions() {
        return activeSessions;
    }

}
