package zirioneft.bladers.servlets;

import zirioneft.bladers.Utils;
import zirioneft.bladers.controllers.PlayerController;
import zirioneft.bladers.entity.Player;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "Menu", urlPatterns = "/menu")
public class Menu extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errors = "";
        String pass = request.getParameter("login_pass");
        String name = request.getParameter("login_name");
        String url = "/";

        PlayerController playerController = new PlayerController();

        if (!name.isEmpty() && !pass.isEmpty()){
            if (playerController.getByName(request.getParameter("login_name")) != null) {
                if(playerController.getByName(name).getPlayerPass().equals(passHash(pass.trim()))) {
                    url = "/WEB-INF/views/menu.jsp";
                    HttpSession session = request.getSession(true);
                    session.setMaxInactiveInterval(300);
                    session.setAttribute("playerName", name);
                    session.setAttribute("isLogged", true);
                    session.setAttribute("playerEntity", playerController.getByName(name));
                } else {
                    errors += "<span style=\"color: red\">Error:</span> Wrong password!";
                }
            } else {
                url = "/WEB-INF/views/menu.jsp";
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(300);
                session.setAttribute("playerName", name);
                session.setAttribute("isLogged", true);
                session.setAttribute("playerEntity", playerController.add(new Player(name, passHash(pass.trim()))));
            }
        }

        request.setAttribute("login", name);
        request.setAttribute("errors", errors);
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher((Utils.hasLoggedIn(request.getSession(false)))?"/WEB-INF/views/menu.jsp":"/").forward(request, response);
    }

    public String passHash(String password) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String salt = "aTastySaltWithoutSomeMeaning";
            String passWithSalt = password + salt;

            byte[] passBytes = passWithSalt.getBytes();
            byte[] passHash = sha256.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (byte aPassHash : passHash) {
                sb.append(Integer.toString((aPassHash & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
