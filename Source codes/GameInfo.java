import java.io.Serializable;

public class GameInfo implements Serializable {

    Player PL1, PL2;
    String Game_Mode;
    int Turn;

    public GameInfo(Player pl1, Player pl2, String game_Mode, int turn) {
        this.PL1 = pl1;
        this.PL2 = pl2;
        Game_Mode = game_Mode;
        Turn = turn;
    }
    public Player getPl1() {
        return PL1;
    }
    public Player getPl2() {
        return PL2;
    }
    public String getGame_Mode() {
        return Game_Mode;
    }
    public int getTurn() {
        return Turn;
    }


}
