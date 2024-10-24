import java.util.Date;
import java.util.Random;

public class TRAP extends Entity {

    int X, Y;
    static Random random = new Random(new Date().getTime());

    public TRAP(String Tag, boolean Destroyable, int x, int y) {
        super(Tag, Destroyable);
        this.X = x;
        this.Y = y;
    }
    public static void Spawn(Object[][] Game_Table) {
        int cnt = 0, rand;
        String RandomTrap;
        while (cnt < 25) {
            rand = random.nextInt(0, 3);
            if (rand == 0)
                RandomTrap = "MST";
            else if (rand == 1)
                RandomTrap = "BMB";
            else
                RandomTrap = "TNT";
            while (true) {
                int x = random.nextInt(0, 10);
                int y = random.nextInt(0, 10);
                if (Game_Table[x][y] == null && !(x == 9 && y == 0) && !(x == 0 && y == 9)) {
                    if (RandomTrap.equals("MST") || RandomTrap.equals("BMB"))
                        Game_Table[x][y] = new TRAP(RandomTrap, true, x, y);
                    else
                        Game_Table[x][y] = new TRAP(RandomTrap, false, x, y);
                    break;
                }
            }
            cnt++;
        }
    }
}
