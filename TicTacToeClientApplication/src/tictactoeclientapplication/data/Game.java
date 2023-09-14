package tictactoeclientapplication.data;

import java.util.ArrayList;

public class Game {
    ArrayList<Move> moves;
    String player,opponent,date,winnigSymbol;
    boolean isRecorded;

    public Game(ArrayList<Move> moves, String player, String opponent, String date,boolean isRecorded,String winnigSymbol) {
        this.moves = moves;
        this.player = player;
        this.opponent = opponent;
        this.date = date;
        this.isRecorded = isRecorded;
        this.winnigSymbol= winnigSymbol;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public String getPlayer() {
        return player;
    }

    public String getOpponent() {
        return opponent;
    }

    public String getDate() {
        return date;
    }

    public boolean isIsRecorded() {
        return isRecorded;
    }

    public String getWinnigSymbol() {
        return winnigSymbol;
    }
}
