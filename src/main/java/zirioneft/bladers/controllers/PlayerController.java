package zirioneft.bladers.controllers;

import zirioneft.bladers.entity.Player;

import javax.persistence.*;
import java.util.List;

public class PlayerController {
    public EntityManager em = Persistence.createEntityManagerFactory("BLADERS").createEntityManager();

    public Player add(Player player) {
        em.getTransaction().begin();
        Player p = em.merge(player);
        em.getTransaction().commit();
        return p;
    }

    public void delete(int id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public Player get(int id) {
        return em.find(Player.class, id);
    }

    public void update(Player player) {
        em.getTransaction().begin();
        em.merge(player);
        em.getTransaction().commit();
    }

    public List<Player> getAll() {
        TypedQuery<Player> namedQuery = em.createNamedQuery("Player.getAll", Player.class);
        return namedQuery.getResultList();
    }

    public Player getByName(String name) {
        List<Player> list = em.createNamedQuery("Player.getByName", Player.class).setParameter("name", name).getResultList();
        if (list.isEmpty())
            return null;
        return list.get(0);
    }
}
