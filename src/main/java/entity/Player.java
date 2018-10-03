package entity;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;

@NamedQueries({
        @NamedQuery(name="Player.getAll", query = "SELECT p FROM Player p"),
        @NamedQuery(name="Player.getByName", query = "SELECT p FROM Player p WHERE p.name = :name")
})
@Table(name="players")
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Имя игрока
    @Column(name = "name", length = 32)
    private String name;

    // Пароль
    @Column(name = "pass")
    private String pass;

    // Рейтинг
    @Column(name = "rating"/*, columnDefinition = "int(11) default 0"*/)
    private int rating = 0;

    // Количество жизней
    @Column(name = "health"/*, columnDefinition = "int(11) default 100"*/)
    private int health = 100;

    // Наносимый урон
    @Column(name = "damage"/*, columnDefinition = "int(11) default 10"*/)
    private int damage = 10;

    public Player() {}

    public Player(String playerName, String playerPass) {
        this.name = playerName;
        this.pass = playerPass;
    }

    public static Player fromRequestParameters(HttpServletRequest request) {
        return new Player(
            request.getParameter("login_name"),
            request.getParameter("login_pass")
        );
    }

    public int getPlayerRating() {
        return rating;
    }

    public int getPlayerDamage() {
        return damage;
    }

    public int getPlayerHealth() {
        return health;
    }

    public int getPlayerId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlayerPass() {
        return pass;
    }

    public void setPlayerDamage(int playerDamage) {
        this.damage = playerDamage;
    }

    public void setPlayerHealth(int playerHealth) {
        this.health = playerHealth;
    }

    public void setPlayerRating(int playerRating) {
        this.rating = playerRating;
    }
}
