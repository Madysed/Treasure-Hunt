import java.io.Serializable;

public class Player extends Entity implements Serializable {
    private final String Username;
    private String LastAction;
    private int Score, Hp, X, Y;
    private int LongJump, Destroy, SpawnTrap;
    public Player(String name, int X, int Y, String Tag, boolean Destroyable, int Score, int Hp, int LongJump, int Destroy, int SpawnTrap, String LastAction) {
        super(Tag, Destroyable);
        this.X = X;
        this.Y = Y;
        this.Hp = Hp;
        this.Score = Score;
        this.Username = name;
        this.Destroy = Destroy;
        this.LongJump = LongJump;
        this.SpawnTrap = SpawnTrap;
        this.LastAction = LastAction;
    }
    public String getUsername() {
        return Username;
    }

    public void setScore(int score) {
        this.Score = Math.max(score, 0);
    }

    public int getScore() {
        return Score;
    }

    public void setHp(int hp) {
        this.Hp = hp;
    }

    public int getHp() {
        return Hp;
    }

    public void setLongJump(int longJump) {
        LongJump = longJump;
    }

    public int getLongJump() {
        return LongJump;
    }

    public void setDestroy(int destroy) {
        Destroy = destroy;
    }

    public int getDestroy() {
        return Destroy;
    }

    public void setSpawnTrap(int spawnTrap) {
        SpawnTrap = spawnTrap;
    }

    public int getSpawnTrap() {
        return SpawnTrap;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getX() {
        return X;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public int getY() {
        return Y;
    }

    public void setLastAction(String lastAction) {
        this.LastAction = lastAction;
    }

    public String getLastAction() {
        return LastAction;
    }
}


