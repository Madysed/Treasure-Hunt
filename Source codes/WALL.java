import java.util.Date;
import java.util.Random;

public class WALL extends Entity {

    int X, Y;
    static Random random = new Random(new Date().getTime());

    public WALL(String Tag, boolean Destroyable, int x, int y) {
        super(Tag, Destroyable);
        this.X = x;
        this.Y = y;
    }

    public static void Spawn(Object[][] Game_Table) {
        int cnt = 0, rand;
        String RandomWall;
        while (cnt < 20) {
            rand = random.nextInt(0, 2);
            if (rand == 0)
                RandomWall = "BWL";
            else
                RandomWall = "UWL";
            while (true) {
                int x = random.nextInt(0, 10);
                int y = random.nextInt(0, 10);
                if (Game_Table[x][y] == null && !(x == 9 && y == 0) && !(x == 0 && y == 9)) {
                    if (RandomWall.equals("BWL"))
                        Game_Table[x][y] = new WALL(RandomWall, true, x, y);
                    else
                        Game_Table[x][y] = new WALL(RandomWall, false, x, y);
                    break;
                }
            }
            cnt++;
        }
    }

}
