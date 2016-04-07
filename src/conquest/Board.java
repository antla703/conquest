package conquest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Point;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents a board
 */
public class Board
{
    private SquareType[][] squares;
    private List<BoardListener> listenerList;
    private boolean gameOver = false;
    //private CollisionHandler collisionHandler = new DefaultCollisionHandler();
    private int width;
    private int height;

    public Board(int width, int height) {
	this.width = width;
	this.height = height;
        squares = new SquareType[width][height];
        listenerList = new ArrayList<BoardListener>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
		squares[i][j] = SquareType.EMPTY;
                }
            }
	this.notifyListeners();
    }

    public int getWidth() {
        return this.squares.length;
    }

    public int getHeight() {
        return this.squares[0].length;
    }

    protected void setSquareType(int x, int y, SquareType st) {
        this.squares[x][y] = st;
        this.notifyListeners();
    }

    public SquareType getSquareType(int x, int y) {
        return this.squares[x][y];
    }

    /**public void resetCollisionHandler(){
        this.collisionHandler = new DefaultCollisionHandler();
    }**/

    public void addBoardListener(BoardListener bl) {
        this.listenerList.add(bl);
    }

    private void notifyListeners() {
        for (BoardListener boardListener : listenerList) {
            boardListener.boardChanged();
        }
    }

    private final Action doOneStep = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            tick();
        }
    };

    //private final Timer clockTimer = new Timer(TICK_DELAY, doOneStep);

    private void tick() {
	if (this.gameOver) {
	    this.clearBoard();
	    String message = " Continue? ";
	    String title = " Tetris ";
	    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
	    if (reply == JOptionPane.NO_OPTION) { System.exit(0); }
	    this.gameOver = false;
	}

	this.notifyListeners();
    }

    private void deleteRow(int row) {
        for (int i = row; i > 0; i--) {
            for (int column = 0; column < this.getWidth(); column++) {
                this.setSquareType(column, i, this.getSquareType(column, i - 1));
            }
        }

        for (int column = 0; column < this.getWidth(); column++) {
            this.setSquareType(column, 0, SquareType.EMPTY);
        }
    }

    protected boolean isSquareEmpty(int x, int y) {
        if (this.getSquareType(x, y) == SquareType.EMPTY) {
            return true;
        }
        return false;
    }

    private void clearBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
		squares[i][j] = SquareType.EMPTY;
            }
        }
        this.notifyListeners();
    }

    public void updateBoard() {
        //clockTimer.setCoalesce(true);
        //clockTimer.start();
    }
}