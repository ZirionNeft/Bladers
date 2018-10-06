package zirioneft.bladers;

import javax.servlet.http.HttpSession;

public class Utils {
    public static boolean hasLoggedIn(HttpSession session) {
        try {
            return (Boolean)session.getAttribute("isLogged");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }
}
