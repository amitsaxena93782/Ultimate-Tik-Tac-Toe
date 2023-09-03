package uttt.game;

import uttt.UTTTFactory;
import uttt.utils.Symbol;
import uttt.utils.Move;

public class Simulator implements SimulatorInterface {
    private BoardInterface[] boards;
    private Symbol currentPlayerSymbol = Symbol.CROSS;
    private int nextMove = -1;
    private Symbol winner = Symbol.EMPTY;

    public Simulator() {
        boards = new BoardInterface[9];
        for (int i = 0; i < 9; i++) {
            boards[i] = UTTTFactory.createBoard();
        }
    }

    public BoardInterface[] getBoards() {
        return boards;
    }

    public void setBoards(BoardInterface[] boards) {
        if (boards.length > 9)
            throw new IllegalArgumentException();
        this.boards = boards;
    }

    public void setCurrentPlayerSymbol(Symbol symbol) {
        if (symbol == null)
            throw new IllegalArgumentException();
        currentPlayerSymbol = symbol;
    }

    public Symbol getCurrentPlayerSymbol() {
        return currentPlayerSymbol;
    }

    public boolean setMarkAt(Symbol symbol, int boardIndex, int markIndex) {
        if (boardIndex < 0 || markIndex < 0 || boardIndex > 8 || markIndex > 8 ||
                (nextMove != -1 && boardIndex != nextMove) || symbol != currentPlayerSymbol || symbol == null)
            throw new IllegalArgumentException();

        if (boards[boardIndex].getMarks()[markIndex].getSymbol() != Symbol.EMPTY)
            return false;

        nextMove = markIndex;
        boards[boardIndex].getMarks()[markIndex].setSymbol(symbol);
        return true;
    }

    public int getIndexNextBoard() {
        return nextMove;
    }

    public void setIndexNextBoard(int index) {
        // if (index == nextMove && nextMove == -1)
        //     return;
        if (index < -1 || index > 8)
            throw new IllegalArgumentException();
        nextMove = index;
    }

    public boolean isGameOver() {
        // Checking for rows:
        for (int i = 0; i < 3; i++) {
            if (boards[(3 * i)].getWinner() == boards[1 + (3 * i)].getWinner() &&
                    boards[1 + (3 * i)].getWinner() == boards[2 + (3 * i)].getWinner() &&
                    boards[2 + (3 * i)].getWinner() != Symbol.EMPTY) {
                winner = boards[(3 * i)].getWinner();
                return true;
            }
        }
        // Checking for columns:
        for (int i = 0; i < 3; i++) {
            if (boards[i].getWinner() == boards[3 + i].getWinner() &&
                    boards[3 + i].getWinner() == boards[6 + i].getWinner() &&
                    boards[i].getWinner() != Symbol.EMPTY) {
                winner = boards[i].getWinner();
                return true;
            }
        }

        // Checking for diagonals:
        if (boards[0].getWinner() == boards[4].getWinner() &&
                boards[4].getWinner() == boards[8].getWinner() &&
                boards[0].getWinner() != Symbol.EMPTY) {
            winner = boards[0].getWinner();
            return true;
        }
        if (boards[2].getWinner() == boards[4].getWinner() &&
                boards[4].getWinner() == boards[6].getWinner() &&
                boards[2].getWinner() != Symbol.EMPTY) {
            winner = boards[0].getWinner();
            return true;
        }

        // Now checking for empty boards:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (boards[i].getMarks()[j].getSymbol() == Symbol.EMPTY)
                    return false;
            }
        }
        return true;
    }

    public boolean isMovePossible(int boardIndex) {
        if (boardIndex < 0 || boardIndex > 8)
            throw new IllegalArgumentException();
        if (nextMove != -1 && nextMove != boardIndex)
            return false;
        return !boards[boardIndex].isClosed();
    }

    public void run(PlayerInterface playerOne, PlayerInterface playerTwo, UserInterface ui)
            throws IllegalArgumentException {
        // throw new UnsupportedOperationException("Unimplemented method 'run'");
        // Do nothing
        if (playerOne == null || playerOne == null || ui == null)
        throw new IllegalArgumentException();

        while (!isGameOver()) {
            PlayerInterface currentPlayer = getCurrentPlayerSymbol() == Symbol.CROSS ? playerOne : playerTwo;
            Move move = currentPlayer.getPlayerMove(this, ui);

            int boardIndex = move.getBoardIndex();
            int markIndex = move.getMarkIndex();

            if (isMovePossible(boardIndex, markIndex)) {
                setMarkAt(getCurrentPlayerSymbol(), boardIndex, markIndex);                                                                                  
                Symbol nextSymbol = getCurrentPlayerSymbol() == Symbol.CIRCLE ? Symbol.CROSS : Symbol.CIRCLE;
                setCurrentPlayerSymbol(nextSymbol);
                if (boards[markIndex].isClosed())
                    setIndexNextBoard(-1);
                else setIndexNextBoard(markIndex);
                ui.updateScreen(this);  
            }
            else if (ui != null) ui.updateScreen(this);
        }
        if (ui != null) ui.showGameOverScreen(getWinner());
    }

    public boolean isMovePossible(int boardIndex, int markIndex) {
        if (boardIndex < 0 || boardIndex > 8 || markIndex < 0 || markIndex > 8)
            throw new IllegalArgumentException();
        if (boards[boardIndex].isClosed()) return false;
        if (nextMove == -1)
        return boards[boardIndex].getMarks()[markIndex].getSymbol() == Symbol.EMPTY;

        if (nextMove != boardIndex) return false;
        return boards[boardIndex].getMarks()[markIndex].getSymbol() == Symbol.EMPTY;
    }

    public Symbol getWinner() {
        isGameOver();
        return winner;
    }
}
