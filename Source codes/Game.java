import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Game implements Gameboard {
    Random random = new Random(new Date().getTime());
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLACK = "\033[1;90m";
    public static final String PURPLE = "\033[0;95m";
    public static final String WHITE_BACKGROUND = "\033[0;107m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    Scanner input = new Scanner(System.in);
    String Game_Mode, Option;
    boolean Exit = false, Back = false, Jump = false;
    boolean Check = false;
    int Turn = 0;
    Player PL1, PL2, PL;
    GameInfo Info;

    static void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
    }

    static void refresh() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        new Game();
    }

    Game() {

        String Menu_Option;
        while (true) {
            refresh();
            System.out.println("|--------------------------------|");
            System.out.println("|--- Welcome to TreasureHunt! ---|");
            System.out.println("|--------------------------------|");
            System.out.println("| 1- New game   | 5- 4-Players   |");
            System.out.println("| 2- Load       | 6- Game guide  |");
            System.out.println("| 3- SALAM      | 7- Logs        |");
            System.out.println("| 4- Exit       | 8- Uninstall   |");
            System.out.println("|--------------------------------|");
            System.out.print("Enter your choice : ");
            Menu_Option = input.nextLine();
            switch (Menu_Option) {
                case "1":
                    New_game();
                    break;
                case "2":
                    Load();
                    break;
                case "3":
                    System.out.println("SALAM!");
                    delay();
                    break;
                case "4":
                    Exit();
                    break;
                case "5":
                    Four_Players();
                    break;
                case "6":
                    Game_Guide();
                    break;
                case "7":
                    Game_Logs();
                    break;
                case "8":
                    Uninstall_Game();
                    break;
                default:
                    System.out.println("Enter a valid number!");
                    delay();
            }
        }

    }

    private void New_game() {

        Turn = 0;
        Clear();
        String F_name, S_name;
        SecondLabel:
        while (true) {
            refresh();
            System.out.println("Select game mode : (0:Back) ");
            System.out.println("1- Normal");
            System.out.println("2- DarkMode");
            Game_Mode = input.nextLine();
            if (Game_Mode.equals("0"))
                return;
            if (Game_Mode.equals("1"))
                Game_Mode = "NORM";
            else if (Game_Mode.equals("2"))
                Game_Mode = "DARK";
            else {
                System.out.println("Enter a valid number!");
                delay();
                continue;
            }
            FirstLabel:
            while (true) {
                refresh();
                System.out.println("Enter Player 1 username : (0:Back) ");
                F_name = input.nextLine();
                if (F_name.equals("0"))
                    continue SecondLabel;
                if (Pattern.compile("\\s").matcher(F_name).find() || (F_name.length() < 3 || F_name.length() > 8)) {
                    System.out.println("Enter an username without space and contains at least 3 chars and up to 8 chars!");
                    delay();
                    continue;
                }
                while (true) {
                    refresh();
                    System.out.println("Enter Player 2 username : (0:Back) ");
                    S_name = input.nextLine();
                    if (S_name.equals("0"))
                        continue FirstLabel;
                    if (Pattern.compile("\\s").matcher(S_name).find() || (S_name.length() < 3 || S_name.length() > 8)) {
                        System.out.println("Enter an username without space and contains at least 3 chars and up to 8 chars!");
                        delay();
                        continue;
                    }
                    break;
                }
                break;
            }
            break;
        }
        refresh();
        PL1 = new Player(F_name, 9, 0, "PL1", false, 0, 5, 6, 3, 3, "NULL");
        PL2 = new Player(S_name, 0, 9, "PL2", false, 0, 5, 6, 3, 3, "Game has just started!");
        System.out.println("[ " + PL1.getUsername() + " ]" + " and " + "[ " + PL2.getUsername() + " ]" + " welcome to TreasureHunt!");
        delay();
        GameTable_Generator();
        Uninstall_Game();
        SaveLogs();
        Turn++;
        Runner();

    }

    private void Runner() {
        while (true) {
            refresh();
            if (PL1.getHp() <= 0) {
                System.out.println("----------------------------------");
                System.out.println("----- PL1 died. PL2 Victory. -----");
                System.out.println("----------------------------------");
                Uninstall_Game();
                delay();
                break;
            }
            if (PL1.getScore() >= 100) {
                System.out.println("------------------------------------------------");
                System.out.println("----- PL1 reached +100 scores. PL1 Victory -----");
                System.out.println("------------------------------------------------");
                Uninstall_Game();
                delay();
                break;
            }
            if (PL2.getHp() <= 0) {
                System.out.println("----------------------------------");
                System.out.println("----- PL2 died. PL1 Victory. -----");
                System.out.println("----------------------------------");
                Uninstall_Game();
                delay();
                break;
            }
            if (PL2.getScore() >= 100) {
                System.out.println("------------------------------------------------");
                System.out.println("----- PL2 reached +100 scores. PL2 Victory -----");
                System.out.println("------------------------------------------------");
                Uninstall_Game();
                delay();
                break;
            }
            if (Turn % 2 == 1) {
                PL = PL1;
                Play();
                if (Exit) {
                    Exit = false;
                    return;
                }
                SaveLogs();
                Turn++;
            } else if (Turn % 2 == 0) {
                PL = PL2;
                Play();
                if (Exit) {
                    Exit = false;
                    return;
                }
                SaveLogs();
                Turn++;
            }
        }
    }

    public void GameTable_Generator() {
        GameTable[0][9] = PL2;
        GameTable[9][0] = PL1;
        PRT.Spawn(GameTable);
        TRS.Spawn(GameTable);
        SPN.Spawn(GameTable);
        WALL.Spawn(GameTable);
        TRAP.Spawn(GameTable);
    }

    private void Print() {
        refresh();
        String LastLine1 = null, LastLine2 = null, LastLine3 = null, LastLine4 = null;
        try {
            File Logs = new File("Logs.txt");
            Scanner FileReader = new Scanner(Logs);
            String[] tmp;
            while (FileReader.hasNextLine()) {
                tmp = FileReader.nextLine().split("-");
                if (tmp[0].equals(Integer.toString(Turn - 1)))
                    LastLine1 = tmp[1];
                if (tmp[0].equals(Integer.toString(Turn - 2)))
                    LastLine2 = tmp[1];
                if (tmp[0].equals(Integer.toString(Turn - 3)))
                    LastLine3 = tmp[1];
                if (tmp[0].equals(Integer.toString(Turn - 4)))
                    LastLine4 = tmp[1];
            }
            FileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        if (Turn % 2 == 1) {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(" * " + PL2.getLastAction() + " *");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        } else if (Turn % 2 == 0) {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(" * " + PL1.getLastAction() + " *");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        }
        System.out.println("╔═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╗");
        for (int i = 0; i < GameTable.length; ++i) {
            System.out.print("║");
            for (int j = 0; j < GameTable[i].length; j++) {
                if (Game_Mode.equals("DARK")) {
                    if ((i < PL.getX() - 4 || i > PL.getX() + 4) || (j > PL.getY() + 4 || j < PL.getY() - 4)) {
                        System.out.print(" " + WHITE_BACKGROUND + "   " + RESET + " ║");
                        continue;
                    }
                }
                if (GameTable[i][j] == null) {
                    System.out.print("     ║");
                    continue;
                }
                if (GameTable[i][j] instanceof Player)
                    System.out.print(" " + WHITE_BACKGROUND + BLACK + GameTable[i][j].toString() + RESET + " ");
                else if (GameTable[i][j] instanceof TRS)
                    System.out.print(" " + GREEN + "TRS" + RESET + " ");
                else if (GameTable[i][j] instanceof PRT)
                    System.out.print(" " + BLUE_BACKGROUND + "PRT" + RESET + " ");
                else if (GameTable[i][j] instanceof SPN)
                    System.out.print(" " + PURPLE + "SPN" + RESET + " ");
                else if (GameTable[i][j] instanceof WALL)
                    System.out.print(" " + YELLOW + GameTable[i][j].toString() + RESET + " ");
                else if (GameTable[i][j] instanceof TRAP)
                    System.out.print(" " + RED + GameTable[i][j].toString() + RESET + " ");
                System.out.print("║");
            }
            if (i == 0)
                System.out.print("                        PL1 HP : " + PL1.getHp() + " | " + "PL2 HP : " + PL2.getHp());
            if (i == 1)
                System.out.print("                     PL1 score : " + PL1.getScore() + " | " + "PL2 score : " + PL2.getScore());
            if (i == 2)
                System.out.print("  PL1 Abilities -> Destruction : " + PL1.getDestroy() + " | " + "Long Jump : " + PL1.getLongJump() + " | " + "Spawn Trap : " + PL1.getSpawnTrap());
            if (i == 3)
                System.out.print("  PL2 Abilities -> Destruction : " + PL2.getDestroy() + " | " + "Long Jump : " + PL2.getLongJump() + " | " + "Spawn Trap : " + PL2.getSpawnTrap());
            if (i == 4)
                System.out.print("--------------------------------------------------------------------");
            if (i == 5)
                System.out.print("  Logs : ");
            if (i == 6 && LastLine4 != null)
                System.out.print(" " + LastLine4);
            if (i == 7 && LastLine3 != null)
                System.out.print(" " + LastLine3);
            if (i == 8 && LastLine2 != null)
                System.out.print(" " + LastLine2);
            if (i == 9 && LastLine1 != null)
                System.out.print(" " + LastLine1);
            System.out.println();
            if (i < GameTable.length - 1) {
                System.out.println("╠═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╣");
            } else
                System.out.println("╚═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╝");
        }
        if (Turn % 2 == 1)
            System.out.println("----------------------------------------- Turn: " + Turn + " , " + PL1 + "'s Turn , Choose an action -----------------------------------------------");
        else
            System.out.println("----------------------------------------- Turn: " + Turn + " , " + PL2 + "'s Turn , Choose an action -----------------------------------------------");
        System.out.println("          W: UP | A: Left | S: Down | D: Right | D: Destruction | J: Long jump | T: Spawn trap | [0] : Save and Exit");
        System.out.println("         in order to use your abilities first Enter the Direction key then the key representing the ability (e.g., DT)");
        System.out.print("Entry : ");
    }

    private void Play() {
        while (true) {
            Print();
            Back = false;
            try {
                Option = input.nextLine();
                if (Option.equals("0")) {
                    Save();
                    Exit = true;
                    return;
                }
                if (Option.length() > 2) {
                    System.out.println("Enter a valid input!");
                    delay();
                    continue;
                }
                switch (Option.charAt(0)) {
                    case 'A', 'a':
                        Move(PL.getX(), PL.getY() - 1);
                        if (Back)
                            continue;
                        return;
                    case 'W', 'w':
                        Move(PL.getX() - 1, PL.getY());
                        if (Back)
                            continue;
                        return;
                    case 'D', 'd':
                        Move(PL.getX(), PL.getY() + 1);
                        if (Back)
                            continue;
                        return;
                    case 'S', 's':
                        Move(PL.getX() + 1, PL.getY());
                        if (Back)
                            continue;
                        return;
                    default:
                        System.out.println("Enter a valid input!");
                        delay();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a valid input!");
                delay();
            }
        }
    }

    private void Move(int x, int y) {

        Jump = false;
        try {
            if (Option.charAt(1) != 'D' && Option.charAt(1) != 'd' && Option.charAt(1) != 'J' && Option.charAt(1) != 'j' && Option.charAt(1) != 'T' && Option.charAt(1) != 't') {
                System.out.println("Enter a valid input!");
                Back = true;
                delay();
                return;
            }
        } catch (IndexOutOfBoundsException e1) {
            try {
                Moving(x, y);
                return;
            } catch (ArrayIndexOutOfBoundsException e2) {
                System.out.println("Out of array!");
                Back = true;
                delay();
                return;
            }
        }
        if (Option.charAt(1) == 'D' || Option.charAt(1) == 'd') {
            try {
                if (PL.getDestroy() == 0) {
                    System.out.println("You dont have Destruction ability!");
                    delay();
                    return;
                }
                if (GameTable[x][y] == null) {
                    System.out.println(PL + " destroyed nothing!");
                    PL.setLastAction(PL + " lost Destruction ability on [" + x + "," + y + "]");
                    delay();
                    return;
                } else if (GameTable[x][y] instanceof WALL && ((WALL) GameTable[x][y]).isDestroyable())
                    PL.setLastAction(PL + " destroyed an breakable wall on [" + x + "," + y + "]");
                else if (GameTable[x][y] instanceof TRAP && ((TRAP) GameTable[x][y]).isDestroyable()) {
                    if (GameTable[x][y].toString().equals("BMB"))
                        PL.setLastAction(PL + " destroyed a BMB on [" + x + "," + y + "]");
                    else
                        PL.setLastAction(PL + " destroyed a MST on [" + x + "," + y + "]");
                } else {
                    System.out.println("You cant Destroy this item!");
                    PL.setLastAction(PL + " lost Destruction ability on [" + x + "," + y + "]");
                    delay();
                    return;
                }
                GameTable[x][y] = null;
                PL.setDestroy(PL.getDestroy() - 1);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("You cant Destroy anything of out of array!");
                PL.setLastAction(PL + " lost Destruction ability cause out of bound Destruction");
                delay();
            }
        } else if (Option.charAt(1) == 'T' || Option.charAt(1) == 't') {
            try {
                int rand;
                if (PL.getSpawnTrap() == 0) {
                    System.out.println("You dont have SpawnTrap ability!");
                    delay();
                    return;
                }
                rand = random.nextInt(0, 3);
                if (GameTable[x][y] == null) {
                    if (rand == 0) {
                        GameTable[x][y] = new TRAP("MST", true, x, y);
                        PL.setLastAction(PL + " spawned a MST on [" + x + "," + y + "]");
                    } else if (rand == 1) {
                        GameTable[x][y] = new TRAP("BMB", true, x, y);
                        PL.setLastAction(PL + " spawned a BMB on [" + x + "," + y + "]");
                    } else if (rand == 2) {
                        GameTable[x][y] = new TRAP("TNT", false, x, y);
                        PL.setLastAction(PL + " spawned a TNT on [" + x + "," + y + "]");
                    }
                } else if (GameTable[x][y] instanceof Player) {
                    Player tmpPL;
                    if (PL == PL1)
                        tmpPL = PL2;
                    else
                        tmpPL = PL1;
                    if (rand == 0) {
                        tmpPL.setHp(tmpPL.getHp() - 1);
                        tmpPL.setScore(tmpPL.getScore() - 5);
                        PL.setLastAction(PL + " spawned a MST on [" + x + "," + y + "] and " + tmpPL + " returned to starting point");
                    } else if (rand == 1) {
                        tmpPL.setHp(tmpPL.getHp() - 2);
                        tmpPL.setScore(tmpPL.getScore() - 10);
                        PL.setLastAction(PL + " spawned a BMB on [" + x + "," + y + "] and " + tmpPL + " returned to starting point");
                    } else if (rand == 2) {
                        tmpPL.setHp(tmpPL.getHp() - 3);
                        tmpPL.setScore(tmpPL.getScore() - 15);
                        PL.setLastAction(PL + " spawned a TNT on [" + x + "," + y + "] and " + tmpPL + " returned to starting point");
                    }
                    GameTable[x][y] = null;
                    if (tmpPL == PL1) {
                        tmpPL.setX(9);
                        tmpPL.setY(0);
                        GameTable[9][0] = tmpPL;
                    } else {
                        tmpPL.setX(0);
                        tmpPL.setY(9);
                        GameTable[0][9] = tmpPL;
                    }
                } else {
                    System.out.println("You cant spawn trap here!");
                    PL.setLastAction(PL + " lost SpawnTrap ability on [" + x + "," + y + "]");
                    delay();
                }
                PL.setSpawnTrap(PL.getSpawnTrap() - 1);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("You cant spawn out of array!");
                PL.setLastAction(PL + " lost SpawnTrap ability to spawn trap out of array");
                PL.setSpawnTrap(PL.getSpawnTrap() - 1);
                delay();
            }
        } else if (Option.charAt(1) == 'J' || Option.charAt(1) == 'j') {
            if (PL.getLongJump() == 0) {
                System.out.println("You dont have LongJump ability!");
                delay();
                return;
            }
            Jump = true;
            if (Option.charAt(0) == 'a' || Option.charAt(0) == 'A')
                --y;
            else if (Option.charAt(0) == 'w' || Option.charAt(0) == 'W')
                --x;
            else if (Option.charAt(0) == 'd' || Option.charAt(0) == 'D')
                ++y;
            else if (Option.charAt(0) == 's' || Option.charAt(0) == 'S')
                ++x;
            try {
                Moving(x, y);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("You cant jump to out of array!");
                PL.setLastAction(PL + " jump to out of array and lost 1 LongJump ability");
                delay();
            }
            PL.setLongJump(PL.getLongJump() - 1);
        }

    }

    private void Moving(int x, int y) {
        int tp = 0;
        if ((x == 9 && y == 0) || (x == 0 && y == 9)) {
            System.out.println("You cant go to the starting point!");
            if (Jump) {
                PL.setLastAction(PL + " lost 1 LongJump ability to back to starting point");
                delay();
                return;
            }
            Back = true;
            delay();
            return;
        }
        if (GameTable[x][y] == null) {
            GameTable[x][y] = PL;
            GameTable[PL.getX()][PL.getY()] = null;
            PL.setLastAction(PL + " moved from [" + PL.getX() + "," + PL.getY() + "] to [" + x + "," + y + "]");
            PL.setX(x);
            PL.setY(y);
        } else if (GameTable[x][y] instanceof TRS) {
            GameTable[x][y] = PL;
            GameTable[PL.getX()][PL.getY()] = null;
            PL.setLastAction(PL + " moved from [" + PL.getX() + "," + PL.getY() + "] to [" + x + "," + y + "]" + " and picked up treasure and obtained +10 Scores");
            PL.setX(x);
            PL.setY(y);
            PL.setScore(PL.getScore() + 10);
            TRS.Spawn(GameTable);
        } else if (GameTable[x][y] instanceof WALL) {
            if (Jump) {
                System.out.println("You cant move to this coordinate!");
                PL.setLastAction(PL + " lost 1 LongJump ability for" + " moving from [" + PL.getX() + "," + PL.getY() + "] to [" + x + "," + y + "]");
                delay();
                return;
            }
            if (((WALL) GameTable[x][y]).isDestroyable())
                System.out.println("You can move to this coordinate but have not selected the destruction ability!");
            else
                System.out.println("You cant move to this coordinate!");
            Back = true;
            delay();
        } else if (GameTable[x][y] instanceof TRAP) {
            if (GameTable[x][y].toString().equals("MST")) {
                PL.setLastAction(PL + " moved from [" + PL.getX() + "," + PL.getY() + "] to [" + x + "," + y + "]" + " and lost 1 HP and 5 score");
                PL.setHp(PL.getHp() - 1);
                PL.setScore(PL.getScore() - 5);
            } else if (GameTable[x][y].toString().equals("BMB")) {
                PL.setLastAction(PL + " moved from [" + PL.getX() + "," + PL.getY() + "] to [" + x + "," + y + "]" + " and lost 2 HP and 10 score");
                PL.setHp(PL.getHp() - 2);
                PL.setScore(PL.getScore() - 10);
            } else if (GameTable[x][y].toString().equals("TNT")) {
                PL.setLastAction(PL + " moved from [" + PL.getX() + "," + PL.getY() + "] to [" + x + "," + y + "]" + " and lost 3 HP and 15 score");
                PL.setHp(PL.getHp() - 3);
                PL.setScore(PL.getScore() - 15);
            }
            GameTable[x][y] = PL;
            GameTable[PL.getX()][PL.getY()] = null;
            PL.setX(x);
            PL.setY(y);
        } else if (GameTable[x][y] instanceof PRT) {
            if (x == 1 && y == 1)
                tp = 8;
            else if (x == 8 && y == 8)
                tp = 1;
            if (GameTable[tp + 1][tp] == null) {
                PL.setLastAction(PL + " teleported from [" + PL.getX() + "," + PL.getY() + "] to [" + (tp + 1) + "," + tp + "]");
                GameTable[tp + 1][tp] = PL;
                GameTable[PL.getX()][PL.getY()] = null;
                PL.setX(tp + 1);
                PL.setY(tp);
            } else if (GameTable[tp][tp + 1] == null) {
                PL.setLastAction(PL + " teleported from [" + PL.getX() + "," + PL.getY() + "] to [" + tp + "," + (tp + 1) + "]");
                GameTable[tp][tp + 1] = PL;
                GameTable[PL.getX()][PL.getY()] = null;
                PL.setX(tp);
                PL.setY(tp + 1);
            } else if (GameTable[tp - 1][tp] == null) {
                PL.setLastAction(PL + " teleported from [" + PL.getX() + "," + PL.getY() + "] to [" + (tp - 1) + "," + tp + "]");
                GameTable[tp - 1][tp] = PL;
                GameTable[PL.getX()][PL.getY()] = null;
                PL.setX(tp - 1);
                PL.setY(tp);
            } else if (GameTable[tp][tp - 1] == null) {
                PL.setLastAction(PL + " teleported from [" + PL.getX() + "," + PL.getY() + "] to [" + tp + "," + (tp - 1) + "]");
                GameTable[tp][tp - 1] = PL;
                GameTable[PL.getX()][PL.getY()] = null;
                PL.setX(tp);
                PL.setY(tp - 1);
            } else {
                if (Jump) {
                    System.out.println("You cant use portal!");
                    PL.setLastAction(PL + " lost 1 LongJump ability for" + " moving from [" + PL.getX() + "," + PL.getY() + "] to [" + x + "," + y + "] to use PRT");
                    delay();
                    return;
                }
                System.out.println("You cant use portal!");
                Back = true;
                delay();
            }
        } else if (GameTable[x][y] instanceof SPN) {
            int randOption = random.nextInt(0, 5);
            if (randOption == 0) {
                int randAbility = random.nextInt(0, 3);
                if (randAbility == 0) {
                    PL.setLastAction(PL + " got an extra SpawnTrap");
                    PL.setSpawnTrap(PL.getSpawnTrap() + 1);
                } else if (randAbility == 1) {
                    PL.setLastAction(PL + " got an extra Destruction");
                    PL.setDestroy(PL.getDestroy() + 1);
                } else if (randAbility == 2) {
                    PL.setLastAction(PL + " got an extra Long Jump");
                    PL.setLongJump(PL.getLongJump() + 1);
                }
            } else if (randOption == 1) {
                if (PL == PL1) {
                    GameTable[9][0] = PL;
                    GameTable[PL.getX()][PL.getY()] = null;
                    PL.setX(9);
                    PL.setY(0);
                } else if (PL == PL2) {
                    GameTable[0][9] = PL;
                    GameTable[PL.getX()][PL.getY()] = null;
                    PL.setX(0);
                    PL.setY(9);
                }
                PL.setLastAction(PL + " returned to starting point");
                GameTable[x][y] = null;
                SPN.Spawn(GameTable);
                return;
            } else if (randOption == 2) {
                if (PL == PL1) {
                    PL2.setLastAction(PL2 + " returned to starting point");
                    GameTable[0][9] = PL2;
                    GameTable[PL2.getX()][PL2.getY()] = null;
                    PL2.setX(0);
                    PL2.setY(9);
                } else if (PL == PL2) {
                    PL1.setLastAction(PL1 + " returned to starting point");
                    GameTable[9][0] = PL1;
                    GameTable[PL1.getX()][PL1.getY()] = null;
                    PL1.setX(9);
                    PL1.setY(0);
                }
            } else if (randOption == 3) {
                int cnt = 0;
                while (cnt < 3) {
                    while (true) {
                        int a = random.nextInt(0, 10);
                        int b = random.nextInt(0, 10);
                        if (GameTable[a][b] == null && !(a == 9 && b == 0) && !(a == 0 && b == 9)) {
                            GameTable[a][b] = new TRAP("TNT", false, a, b);
                            break;
                        }
                    }
                    cnt++;
                }
                PL.setLastAction("Three TNTs were spawned randomly");
            }
            if (randOption == 4) {
                int rand, trap = 0;
                ArrayList<String> coords = new ArrayList<>();
                for (int i = 0; i < 10; i++)
                    for (int j = 0; j < 10; j++)
                        if (GameTable[i][j] instanceof TRAP)
                            coords.add(i + " " + j);
                if (coords.size() < 3) {
                    for (int i = 0; i < 10; i++)
                        for (int j = 0; j < 10; j++)
                            if (GameTable[i][j] instanceof TRAP)
                                GameTable[i][j] = null;
                    PL.setLastAction("All traps were destroyed");
                    return;
                }
                while (trap < 3) {
                    String[] tmp;
                    rand = random.nextInt(0, coords.size());
                    tmp = coords.get(rand).split(" ");
                    GameTable[Integer.parseInt(tmp[0])][Integer.parseInt(tmp[1])] = null;
                    coords.remove(rand);
                    trap++;
                }
                PL.setLastAction("Three traps were destroyed randomly");
            }
            GameTable[x][y] = PL;
            GameTable[PL.getX()][PL.getY()] = null;
            PL.setX(x);
            PL.setY(y);
            SPN.Spawn(GameTable);
        } else if (GameTable[x][y] instanceof Player) {
            System.out.println("You cant move to your opponent's point!");
            delay();
            if (Jump) {
                PL.setLastAction(PL + " lost 1 LongJump ability for" + " moving from [" + PL.getX() + "," + PL.getY() + "] to opponent point");
                return;
            }
            Back = true;
        }
    }

    private void Save() {
        try {
            File File = new File("GameBoard.txt");
            FileWriter FileWriter = new FileWriter(File);
            Check = File.createNewFile();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (GameTable[i][j] == null) {
                        FileWriter.write("null" + "\n");
                        continue;
                    }
                    if (GameTable[i][j] instanceof Player) {
                        FileWriter.write("PLN" + "\n");
                        continue;
                    }
                    FileWriter.write(GameTable[i][j].toString() + "\n");
                    if (i + j == 18)
                        FileWriter.write("\n");
                }
            }
            FileWriter.close();
        } catch (IOException ignored) {
        }

        Info = new GameInfo(PL1, PL2, Game_Mode, Turn);
        try {
            File File = new File("GameInfo.txt");
            Check = File.createNewFile();
            FileOutputStream FileOut = new FileOutputStream(File);
            ObjectOutputStream ObjectOut = new ObjectOutputStream(FileOut);
            ObjectOut.writeObject(Info);
            FileOut.close();
            ObjectOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void Load() {
        Clear();
        try {
            File File = new File("GameBoard.txt");
            if (File.exists()) {
                Scanner FileReader = new Scanner(File);
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        String TAG = FileReader.nextLine();
                        switch (TAG) {
                            case "TRS":
                                GameTable[i][j] = new TRS("TRS", false, i, j);
                                break;
                            case "SPN":
                                GameTable[i][j] = new SPN("SPN", false, i, j);
                                break;
                            case "MST", "BMB":
                                GameTable[i][j] = new TRAP(TAG, true, i, j);
                                break;
                            case "TNT":
                                GameTable[i][j] = new TRAP("TNT", false, i, j);
                                break;
                            case "UWL":
                                GameTable[i][j] = new WALL(TAG, false, i, j);
                                break;
                            case "BWL":
                                GameTable[i][j] = new WALL(TAG, true, i, j);
                                break;
                            case "PLN":
                                continue;
                            case "PRT":
                                GameTable[i][j] = new PRT("PRT", false);
                                break;
                            case "null":
                                GameTable[i][j] = null;
                                break;
                        }
                    }
                }
                FileReader.close();
            } else {
                System.out.println("First start a new game!");
                delay();
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            File File = new File("GameInfo.txt");
            if (File.exists()) {
                FileInputStream FileIn = new FileInputStream(File);
                ObjectInputStream ObjectIn = new ObjectInputStream(FileIn);
                Info = (GameInfo) ObjectIn.readObject();
                ObjectIn.close();
                FileIn.close();
                PL1 = Info.getPl1();
                PL1.setTag("PL1");
                PL1.setDestroyable(false);
                GameTable[PL1.getX()][PL1.getY()] = PL1;
                PL2 = Info.getPl2();
                PL2.setTag("PL2");
                PL2.setDestroyable(false);
                GameTable[PL2.getX()][PL2.getY()] = PL2;
                Turn = Info.getTurn();
                Game_Mode = Info.getGame_Mode();
            } else {
                System.out.println("First start a new game!");
                delay();
                return;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Runner();
    }

    private void SaveLogs() {
        try {
            File Logs = new File("Logs.txt");
            Check = Logs.createNewFile();
            FileWriter FileWriter = new FileWriter(Logs, true);
            if (Turn == 0) {
                FileWriter.write("Game has just started!" + "\n");
                FileWriter.close();
                return;
            }
            FileWriter.append(String.valueOf(Turn)).append("- ").append(PL.getLastAction()).append("\n");
            FileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void Clear() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                GameTable[i][j] = null;
    }

    private void Exit() {
        String Exit_Option;
        while (true) {
            refresh();
            System.out.println("Are you sure?");
            System.out.println("1- Yes");
            System.out.println("2- No");
            System.out.print("Enter your choice : ");
            Exit_Option = input.nextLine();
            if (Exit_Option.equals("1"))
                System.exit(0);
            else if (Exit_Option.equals("2"))
                break;
            else
                System.out.println("Enter a valid number!");
            delay();
        }
    }

    private void Four_Players() {
    }

    private void Game_Guide() {
        refresh();
        System.out.println("|--------- Game guide ---------|");
        System.out.println("| [MST]  -->  Mouse Trap       |");
        System.out.println("| [BMB]  -->  Bomb             |");
        System.out.println("| [TNT]  -->  A type of bomb   |");
        System.out.println("| [BWL]  -->  breakable wall   |");
        System.out.println("| [UWL]  -->  unbreakable wall |");
        System.out.println("| [TRS]  -->  Treasure         |");
        System.out.println("| [PRT]  -->  Portal           |");
        System.out.println("|------------------------------|");
        System.out.println("Enter sth to continue...");
        input.nextLine();
    }

    private void Game_Logs() {
        refresh();
        try {
            File Logs = new File("Logs.txt");
            Scanner FileReader = new Scanner(Logs);
            System.out.println("Logs : ");
            while (FileReader.hasNextLine())
                System.out.println(FileReader.nextLine());
            FileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("First start a new game!");
            delay();
            return;
        }
        System.out.println("Enter to continue...");
        input.nextLine();
    }

    private void Uninstall_Game() {
        refresh();
        File GameBoard = new File("GameBoard.txt");
        File GameInfo = new File("GameInfo.txt");
        File Logs = new File("Logs.txt");
        Check = GameBoard.delete();
        Check = GameInfo.delete();
        Check = Logs.delete();
    }

}