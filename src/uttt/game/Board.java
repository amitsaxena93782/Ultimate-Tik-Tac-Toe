package uttt.game;

import uttt.UTTTFactory;
import uttt.utils.Symbol;

public class Board implements BoardInterface {
    private MarkInterface[] marks;
    private Symbol winner = Symbol.EMPTY;

    public Board () {
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
    } 

    public MarkInterface[] getMarks() {
        return marks;
    }

    public void setMarks(MarkInterface[] marks) {
        if (marks.length > 9)
            throw new IllegalArgumentException();
        else
            this.marks = marks;

    }

    public boolean setMarkAt(Symbol symbol, int markIndex) {

        if (markIndex < 0 || markIndex > 8 || symbol == null)
            throw new IllegalArgumentException();
        if (marks[markIndex].getSymbol() != Symbol.EMPTY) return false;
        marks[markIndex].setSymbol(symbol);
        return true;
    }

    public boolean isClosed() {
        // Checking for rows
        for (int i = 0; i < 3; i++) {
            if (marks[0 + (3 * i)].getSymbol() == marks[1 + (3 * i)].getSymbol() &&
                    marks[1 + (3 * i)].getSymbol() == marks[2 + (3 * i)].getSymbol() &&
                    marks[0 + (3 * i)].getSymbol() != Symbol.EMPTY) {
                winner = marks[0 + (3 * i)].getSymbol();
                return true;
            }
        }

        // Checking for column:
        for (int i = 0; i < 3; i++) {
            if (marks[0 + i].getSymbol() == marks[3 + i].getSymbol() &&
                    marks[3 + i].getSymbol() == marks[6 + i].getSymbol() &&
                    marks[0 + i].getSymbol() != Symbol.EMPTY) {
                winner = marks[0 + i].getSymbol();
                return true;
            }
        }

        // Checking for diagonals:
        if (marks[0].getSymbol() == marks[4].getSymbol() &&
                marks[4].getSymbol() == marks[8].getSymbol() &&
                marks[0].getSymbol() != Symbol.EMPTY) {
            winner = marks[0].getSymbol();
            return true;
        }

        if (marks[2].getSymbol() == marks[4].getSymbol() &&
                marks[4].getSymbol() == marks[6].getSymbol() &&
                marks[2].getSymbol() != Symbol.EMPTY) {
            winner = marks[2].getSymbol();
            return true;
        }

        for (int i = 0; i < 9; i++) {
            if (marks[i].getSymbol() == Symbol.EMPTY)
                return false;
        }
        return true;
    }

    public boolean isMovePossible(int index) {
        if (index < 0 || index > 8)
            throw new IllegalArgumentException();
        if (marks[index].getSymbol() == Symbol.EMPTY)
            return true;
        return false;
    }

    public Symbol getWinner() {
        isClosed();
        return winner;
    }

}
