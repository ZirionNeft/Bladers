package zirioneft.bladers.servlets;

import zirioneft.bladers.Utils;
import zirioneft.bladers.entity.Match;
import zirioneft.bladers.entity.Player;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static zirioneft.bladers.entity.Match.setMatchTimer;

@WebServlet(name = "MatchMaker", urlPatterns = "/match")
public class MatchMaker extends HttpServlet {
    private static ConcurrentLinkedQueue<HttpSession> queue = new ConcurrentLinkedQueue<HttpSession>();
    private static ConcurrentHashMap<Match, Timer> timers = new ConcurrentHashMap<Match, Timer>();
    private static Integer matchIdCounter = 0;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String url = request.getContextPath();

        if (Utils.hasLoggedIn(session)) {
            Match m = (Match) session.getAttribute("match");
            if (m.whoTurn().equals(session.getAttribute("playerName").toString())) {
                m.doTurn(session.getAttribute("playerName").toString());
            }
        }

        //request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (Utils.hasLoggedIn(session)) {
            for(Match m : Collections.list(timers.keys())) {
                if (m.containsPlayer(session.getAttribute("playerName").toString())) {
                    session.setAttribute("match", m);
                    request.getRequestDispatcher("/WEB-INF/views/match.jsp").forward(request, response);
                    return;
                }
            }

            if (session.getAttribute("match") == null) {
                if (!queue.contains(session))
                    queue.add(session);
                Match match = createMatchFromQueue(session);
                if (match != null) {
                    timers.put(match, setMatchTimer(match));
                    matchIdCounter++;
                    request.getRequestDispatcher("/WEB-INF/views/match.jsp").forward(request, response);
                    return;
                }
            }
        }

        response.setIntHeader("refresh", 3);
        request.getRequestDispatcher("/WEB-INF/views/queue.jsp").forward(request, response);
    }

    private static Match createMatchFromQueue(HttpSession player) {
        synchronized (Match.class) {
            Match match = null;
            HttpSession p2 = queue.peek();

            System.out.println(Thread.currentThread().getId() + " " + queue);

            if (p2 != null) {
                if (!p2.equals(player)) {
                    queue.remove(player);
                    queue.remove(p2);

                    match = new Match(player, p2, matchIdCounter);

                    player.setAttribute("match", match);
                    p2.setAttribute("match", match);
                }
            }
            return match;
        }
    }
}
