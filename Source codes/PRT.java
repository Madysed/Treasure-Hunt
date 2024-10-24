public class PRT extends Entity {
    public PRT(String Tag, boolean Destroyable) {
        super(Tag, Destroyable);
    }
    public static void Spawn(Object[][] Game_Table) {
        Game_Table[1][1] = new PRT("PRT", false);
        Game_Table[8][8] = new PRT("PRT", false);
    }
}
