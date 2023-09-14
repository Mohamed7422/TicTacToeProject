package tictactoeclientapplication.data;

public class Move {
    String row,col,symbol;

    public Move(String row, String col, String symbol) {
        this.row = row;
        this.col = col;
        this.symbol = symbol;
    }

    public String getRow() {
        return row;
    }

    public String getCol() {
        return col;
    }

    public String getSymbol() {
        return symbol;
    }
}
