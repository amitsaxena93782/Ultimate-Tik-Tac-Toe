package uttt.game;

import uttt.utils.Symbol;

public class Mark implements MarkInterface {
    private Symbol symbol;
    private int markindex;

    public Mark (Symbol symbol, int markIndex) {
        this.symbol = symbol;
        this.markindex = markIndex;
    }

    public Symbol getSymbol() {
        return symbol;
    }
    public int getPosition() {
        return markindex;
    }
    
    public void setSymbol(Symbol symbol) {
        if (symbol == null) throw new IllegalArgumentException();
        this.symbol = symbol;
    }
}
