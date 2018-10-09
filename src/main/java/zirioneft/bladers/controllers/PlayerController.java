package zirioneft.bladers.controllers;

import zirioneft.bladers.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PlayerController {
    private static HashMap<HttpSession, Integer> queryCounter = new HashMap<HttpSession, Integer>();
    private static HashMap<HttpSession, Integer> queryTime = new HashMap<HttpSession, Integer>();

    public EntityManager em = Persistence.createEntityManagerFactory("BLADERS").createEntityManager();

    public Player add(HttpSession session, Player player) {
        long ms = new Date().getTime();
        em.getTransaction().begin();
        Player p = em.merge(player);
        em.getTransaction().commit();

        queryTime.put(session, queryTime.get(session) + (int) (new Date().getTime() - ms));
        queryCounter.put(session, queryCounter.get(session)+1);

        return p;
    }

    public void delete(HttpSession session, int id) {
        long ms = new Date().getTime();
        em.getTransaction().begin();
        em.remove(get(session, id));
        em.getTransaction().commit();
        queryTime.put(session, queryTime.get(session) + (int) (new Date().getTime() - ms));
        queryCounter.put(session, queryCounter.get(session)+1);
    }

    public Player get(HttpSession session, int id) {
        long ms = new Date().getTime();
        Player p = em.find(Player.class, id);
        queryTime.put(session, queryTime.get(session) + (int) (new Date().getTime() - ms));
        queryCounter.put(session, queryCounter.get(session)+1);
        return p;
    }

    public void update(HttpSession session, Player player) {
        long ms = new Date().getTime();
        em.getTransaction().begin();
        em.merge(player);
        em.getTransaction().commit();
        queryTime.put(session, queryTime.get(session) + (int) (new Date().getTime() - ms));
        queryCounter.put(session, queryCounter.get(session)+1);
    }

    public List<Player> getAll(HttpSession session) {
        long ms = new Date().getTime();
        TypedQuery<Player> namedQuery = em.createNamedQuery("Player.getAll", Player.class);
        queryTime.put(session, queryTime.get(session) + (int) (new Date().getTime() - ms));
        queryCounter.put(session, queryCounter.get(session)+1);
        return namedQuery.getResultList();
    }

    public Player getByName(HttpSession session, String name) {
        long ms = new Date().getTime();
        List<Player> list = em.createNamedQuery("Player.getByName", Player.class).setParameter("name", name).getResultList();
        queryTime.put(session, queryTime.get(session) + (int) (new Date().getTime() - ms));
        queryCounter.put(session, queryCounter.get(session)+1);
        if (list.isEmpty())
            return null;
        return list.get(0);
    }

    public static Integer getQueriesMs(HttpSession session) {
        if (queryCounter.get(session) == null)
            return 0;
        if (queryCounter.get(session) == 0)
            return 0;
        return queryTime.get(session) / queryCounter.get(session);
    }

    public static Integer getQueriesCount(HttpSession session) {
        return queryCounter.get(session);
    }

    public static void resetQueriesInfo(HttpSession session) {
        queryCounter.put(session, 0);
        queryTime.put(session, 0);
    }
}
