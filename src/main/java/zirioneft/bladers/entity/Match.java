package zirioneft.bladers.entity;


import zirioneft.bladers.controllers.PlayerController;

import javax.servlet.http.HttpSession;
import java.util.*;

public class Match {

    private int id;
    private String whoTurn;
    private String whoWinner = null;

    private Timer timer = null;
    private Integer secRemaining = 30;
    private boolean isStarted = false;

    private String p1Name, p2Name;
    private Integer p1Health, p2Health;
    private Integer p1Damage, p2Damage;

    private Player p1Entity, p2Entity;
    private HttpSession p1Session, p2Session;

    private LinkedList<String> battleLog = new LinkedList<String>();

    private Random randomDamage = new Random();

    public Match(HttpSession p1, HttpSession p2, int id) {
        this.id = id;

        this.p1Session = p1;
        this.p2Session = p2;
        try {
            this.p1Entity = (Player)p1.getAttribute("playerEntity");
            this.p2Entity = (Player)p2.getAttribute("playerEntity");

            this.p1Name = p1Entity.getName();
            this.p1Damage = p1Entity.getPlayerDamage();
            this.p1Health = p1Entity.getPlayerHealth();

            this.p2Name = p2Entity.getName();
            this.p2Damage = p2Entity.getPlayerDamage();
            this.p2Health = p2Entity.getPlayerHealth();

            this.whoTurn = (p2Entity.getPlayerRating() > p1Entity.getPlayerRating())? this.p1Name : this.p2Name;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String whoTurn() {
        return whoTurn;
    }

    public void doTurn(String name) {
        if (whoTurn.equals(name)) {
            if (p1Name.equals(whoTurn)) {
                int dmg = randomDamage.nextInt(p1Damage/2)+p1Damage/2;
                p2Health -= dmg;
                battleLog.add(String.format("[%s] hit [%s] for <b style='color:#a20303'>%d</b> damage", p1Name, p2Name, dmg));
                whoTurn = p2Name;
            } else if (p2Name.equals(whoTurn)) {
                int dmg = randomDamage.nextInt(p2Damage/2)+p2Damage/2;
                p1Health -= dmg;
                battleLog.add(String.format("[%s] hit [%s] for <b style='color:#a20303'>%d</b> damage", p2Name, p1Name, dmg));
                whoTurn = p1Name;
            }
        }

        if (p1Health <= 0) {
            battleLog.add(String.format("[%s] killed [%s]", p2Name, p1Name));
            p1Health = 0;
            end(p2Name);
        } else if (p2Health <= 0) {
            battleLog.add(String.format("[%s] killed [%s]", p1Name, p2Name));
            p2Health = 0;
            end(p1Name);
        }
    }

    public HashMap<String, String> getPlayerInfo(HttpSession session) {
        HashMap<String, String> info = new HashMap<String, String>();

        if (p1Session.equals(session)) {
            info.put("name", p1Name);
            info.put("damage", p1Damage.toString());
            info.put("health", p1Health.toString());
            info.put("healthFull", ""+p1Entity.getPlayerHealth());
            return info;
        } else if (p2Session.equals(session)) {
            info.put("name", p2Name);
            info.put("damage", p2Damage.toString());
            info.put("health", p2Health.toString());
            info.put("healthFull", ""+p2Entity.getPlayerHealth());
            return info;
        }
        return null;
    }

    public void end(String winner) {
        if (whoTurn == null)
            return;
        whoTurn = null;
        whoWinner = winner;
        synchronized (this) {
            PlayerController playerController = new PlayerController();

            if (p1Name.equals(winner)) {
                p1Entity.setPlayerRating(p1Entity.getPlayerRating()+1);
                p2Entity.setPlayerRating(p2Entity.getPlayerRating()-1);
            } else if (p2Name.equals(winner)) {
                p1Entity.setPlayerRating(p1Entity.getPlayerRating()-1);
                p2Entity.setPlayerRating(p2Entity.getPlayerRating()+1);
            }
            p1Entity.setPlayerDamage(p1Damage+1);
            p1Entity.setPlayerHealth(p1Entity.getPlayerHealth()+1);
            p2Entity.setPlayerDamage(p2Damage+1);
            p2Entity.setPlayerHealth(p2Entity.getPlayerHealth()+1);

            playerController.update(p1Session, p1Entity);
            playerController.update(p2Session, p2Entity);

            p1Session.setAttribute("playerEntity", p1Entity);
            p2Session.setAttribute("playerEntity", p2Entity);
        }
    }

    public String getWhoWinner() {
        return whoWinner;
    }

    public LinkedList<String> getBattleLog() {
        return battleLog;
    }

    public boolean containsPlayer(String name) {
        return p1Name.equals(name) || p2Name.equals(name);
    }

    public HttpSession getEnemySession(HttpSession session) {
        return (p1Session.equals(session))?p2Session:p1Session;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public Integer getSecRemaining() {
        return secRemaining;
    }

    public static Timer setMatchTimer(final Match match) {
        if (match.timer != null) {
            return match.timer;
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (match.secRemaining == 0) {
                    match.isStarted = true;
                    this.cancel();
                }
                match.secRemaining--;
            }
        }, 0, 1000);
        match.timer = timer;
        return timer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Match))
            return false;
        Match object = (Match)obj;

        return this.p1Name.equals(object.p1Name) && this.p2Name.equals(object.p2Name);
    }
}
