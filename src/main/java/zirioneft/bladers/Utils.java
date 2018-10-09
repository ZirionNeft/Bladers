package zirioneft.bladers;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Utils {

    private static HashMap<HttpSession, Long> startTime = new HashMap<HttpSession, Long>();

    public static boolean hasLoggedIn(HttpSession session) {
        try {
            return (Boolean)session.getAttribute("isLogged");
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static void startTime(HttpSession session) {
        startTime.put(session, new Date().getTime());
    }

    public static int getTime(HttpSession session) {
        return (int)(new Date().getTime() - startTime.remove(session));
    }

}
