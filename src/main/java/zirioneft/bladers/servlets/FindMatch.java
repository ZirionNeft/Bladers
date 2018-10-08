package zirioneft.bladers.servlets;

import zirioneft.bladers.Utils;
import zirioneft.bladers.entity.Player;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "FindMatch", urlPatterns = "/find_match")
public class FindMatch extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String url = "/";

        if (Utils.hasLoggedIn(session)) {
            request.setAttribute("rating", ((Player)session.getAttribute("playerEntity")).getPlayerRating());
            url = "/WEB-INF/views/find_match.jsp";
        }

        request.getRequestDispatcher(url).forward(request, response);
    }
}
