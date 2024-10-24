import java.util.Date;
import java.util.Random;

public class SPN extends Entity {

    int X, Y;
    static Random random = new Random(new Date().getTime());
    public SPN(String Tag, boolean Destroyable, int x, int y) {
        super(Tag, Destroyable);
        this.X = x;
        this.Y = y;
    }
    public static void Spawn(Object[][] Game_Table) {
        while (true) {
            int x = random.nextInt(0, 10);
            int y = random.nextInt(0, 10);
            if (Game_Table[x][y] == null && !(x == 9 && y == 0) && !(x == 0 && y == 9)) {
                Game_Table[x][y] = new SPN("SPN", false, x, y);
                break;
            }
        }
    }

}
