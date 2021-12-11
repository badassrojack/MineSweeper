/**
 * This class represents the button in the game board, it records the position of itself.
 * When click it, if the cell under it is a bomb then game over; if the cell is number then just open the button to show the label;
 * if the cell is empty then recursively check whether the 8 cells around this button is empty.
 * Players can right click the button to mark it as a bomb under it and the marked button cannot be clicked again.
 */

import javax.swing.*;
import java.awt.*;


public class GameButton extends JButton {
    private int[] loc = new int[2];//store the position of this button on the panel
    private boolean marked = false;
    private GameBoard motherBoard;//composition, enable the button to get access to the board it placed

    /**
     * The default constructor sets the button with an empty image.
     */
    public GameButton(GameBoard motherBoard) {
        super();
        this.motherBoard = motherBoard;
        this.setButtonImage("src\\resource\\button.png");
    }

    /**
     * Set the location array with its position on the panel.
     *
     * @param row row of this button
     * @param col column of this button
     */
    public void setLoc(int row, int col) {
        loc[0] = row;
        loc[1] = col;
    }

    /**
     * Get the #row of this button.
     *
     * @return row of this button on the panel
     */
    public int getRow() {
        return loc[0];
    }

    /**
     * Get the #column of this button
     *
     * @return column of this button on the panel
     */
    public int getCol() {
        return loc[1];
    }

    /**
     * Check if the button has been marked.
     *
     * @return true if it is marked
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * Set the button as marked
     */
    public void setAsMarked() {
        this.marked = true;
    }

    /**
     * Set the button as unmarked
     */
    public void setAsUnmarked() {
        this.marked = false;
    }

    /**
     * Disclose the button by click. If the label underneath is a bomb, game over; if
     * a number, open it; if an empty label, open it and check the 8 labels around it
     * and recursively disclose them.
     *
     * @param button the button to be set
     */
    public static void disclose(GameButton button) {
        if (button.motherBoard.clickCount == 0) {
            button.motherBoard.setStartPos(button.getRow(), button.getCol());
            button.motherBoard.initBoard();
            button.motherBoard.clickCount++;
            disclose(button);
        }

        button.motherBoard.buttonsToBeOpened--;
        GameLabel[][] labels = button.motherBoard.getLabels();
        int rows = labels.length;
        int cols = labels[0].length;
        GameButton[][] buttons = button.motherBoard.getButtons();

        switch (labels[button.getRow()][button.getCol()].getLabelType()) {
            case BOMB -> {//open all the buttons and game over
                button.motherBoard.isLost = true;
                button.motherBoard.openAll();
            }
            case EMPTY -> {
                button.setVisible(false);
                //if the label underneath is empty, iterate the labels around
                for (int currI = button.getRow() - 1; currI <= button.getRow() + 1; currI++) {
                    for (int currJ = button.getCol() - 1; currJ <= button.getCol() + 1; currJ++) {
                        if (currI >= 0 && currI < rows && currJ >= 0 && currJ < cols) {
                            GameButton gb = buttons[currI][currJ];
                            GameLabel labelUnder = labels[currI][currJ];
                            if (gb.isMarked() || !gb.isVisible() || labelUnder.getLabelType() == LabelType.BOMB)
                                continue;
                            disclose(gb);
                        }
                    }
                }
            }
            case NUMBER -> button.setVisible(false);
        }
    }

//    regularMouseListener[] mls = (regularMouseListener[])(this.getListeners(regularMouseListener.class)) ;


    /**
     * Once a button is right-clicked, it is set as marked and cannot response to
     * click, only thing can do on this button is right click it again and reset it
     * as a regular button.
     *
     * @param button the button to be set
     */
    public static void markGB(GameButton button) {
        button.motherBoard.minesRemain--;
        button.setAsMarked();
        button.setButtonImage("src\\resource\\mark.png");
        button.addMouseListener(new markedMouseListener());
        button.removeMouseListener(button.getMouseListeners()[1]);//Must remove the last listener or the event of that listener will still be enabled.
    }

    /**
     * Unmark the button, reset it to a regular button.
     *
     * @param button the button to be set
     */
    public static void unmark(GameButton button) {
        button.motherBoard.minesRemain++;
        button.setAsUnmarked();
        button.setButtonImage("src\\resource\\button.png");
        button.addMouseListener(new regularMouseListener());
        button.removeMouseListener(button.getMouseListeners()[1]);
    }

    void setButtonImage(String s) {
        ImageIcon icon = new ImageIcon(s);
        icon.setImage(icon.getImage().getScaledInstance(GameBoard.CELLWIDTH, GameBoard.CELLHEIGHT, Image.SCALE_DEFAULT));
        this.setIcon(icon);
    }


}
