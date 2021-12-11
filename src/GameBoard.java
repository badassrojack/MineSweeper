/**
 * This class represents the board of the game. We draw the cells in this board and implement each cell with the class Button.
 * In this class we can define some basic variables such as number of row, column, mine etc.
 * It extends the JPanel class to implement the function.
 */

import javax.swing.*;
import java.util.Random;

public class GameBoard extends JPanel {
    private int rowsNum; //number of row

    private int colsNum; //number of column

    private int minesNum; //number of mine

    int minesRemain;//record the #mine remains to be swept, initial value is minesNum, --or++ when the button is marked or unmarked

//    private int difficulty = 10; //coefficient of difficulty

    static final int CELLWIDTH = 22; //width of each cell in the board

    static final int CELLHEIGHT = 22; //height of each cell in the board

    private GameLabel[][] labels; //the position information of all the cells in the board

    private GameButton[][] buttons;//the position info of all buttons

    int clickCount;//count the frequency that the board is left-clicked

    int buttonsToBeOpened;//the buttons remain to be opened in the panel, which is used to determine whether the game is over, when it counts to 0 then win

    boolean isLost = false;//whether the game is over and lost

    private int startRow;//the #row of the first click

    private int startCol;//the #column of the first click

    /**
     * Set the start position of the first click, and do not create a bomb on this position.
     *
     * @param startRow the row of the first click
     * @param startCol the column of the first click
     */
    void setStartPos(int startRow, int startCol) {
        this.startRow = startRow;
        this.startCol = startCol;
    }

    public int getRowsNum() {
        return rowsNum;
    }

    public int getColsNum() {
        return colsNum;
    }

    public int getMinesNum() {
        return minesNum;
    }

    /**
     * Get the total height of the board for initializing the JFrame.
     *
     * @return height of the board
     */
    public int getBoardHeight() {
        return this.rowsNum * CELLHEIGHT;
    }

    /**
     * Get the total width of the board for initializing the JFrame.
     *
     * @return width of the board
     */
    public int getBoardWidth() {
        return this.colsNum * CELLWIDTH;
    }

//    /**
//     * Set the coefficient of difficulty
//     *
//     * @param difficulty coefficient of difficulty, default value is 10
//     */
//    public void setDifficulty(int difficulty) {
//        this.difficulty = difficulty;
//    }

    /**
     * Get the 2-d array of GameLabel of the board.
     *
     * @return a GameLabel[][] of all the labels on the panel
     */
    public GameLabel[][] getLabels() {
        return labels;
    }

    /**
     * Get the 2-d array of GameButton of the board.
     *
     * @return a GameLabel[][] of all buttons on the panel
     */
    public GameButton[][] getButtons() {
        return buttons;
    }

    /**
     * Determine whether the game is over and player wins. When the buttons ready to
     * be opened is 0, which means players opened all the button that is not a bomb,
     * then he should win the game. Player knows he lost when he hit a bomb and the panel
     * opens all the buttons automatically.
     *
     * @return true if the number of buttons to be opened is 0
     */
    public boolean isWin(){
        return buttonsToBeOpened == 0;
    }

    //Open all the buttons
    void openAll(){
        for (int i = 0; i < rowsNum; i++) {
            for (int j = 0; j < colsNum; j++) {
                buttons[i][j].setVisible(false);
            }
        }
    }

//    /**
//     * This is the constructor made for default difficulty we offered for players.
//     *
//     * @param rowsNum number of row
//     * @param colsNum number of column
//     */
//    public GameBoard(int rowsNum, int colsNum) {
//        this.removeAll();
//        this.rowsNum = rowsNum;
//        this.colsNum = colsNum;
//        this.minesNum = (int) (rowsNum * colsNum / difficulty);
//        labels = new GameLabel[rowsNum][colsNum];
//        buttons = new GameButton[rowsNum][colsNum];
//        this.setLayout(null);//tell the container not to use layout and manually configure all the components
//        this.setSize(CELLWIDTH * colsNum, CELLHEIGHT * rowsNum);
//        this.createButton();
//    }

    /**
     * This constructor is made for customizing the game, including resizing the
     * size of the board and the number of mines
     *
     * @param rowsNum  number of row
     * @param colsNum  number of column
     * @param minesNum number of mine
     */
    public GameBoard(int rowsNum, int colsNum, int minesNum) {
        this.removeAll();
        this.clickCount = 0;
        this.rowsNum = rowsNum;
        this.colsNum = colsNum;
        this.minesNum = minesNum;
        this.minesRemain = minesNum;
        this.labels = new GameLabel[rowsNum][colsNum];
        this.buttons = new GameButton[rowsNum][colsNum];
        this.buttonsToBeOpened = rowsNum*colsNum - minesNum;
        this.setLayout(null);//tell the container not to use layout and manually configure all the components
        this.setSize(CELLWIDTH * colsNum, CELLHEIGHT * rowsNum);
        this.createButton();
    }

    /*
    wrap the create method of the panel
     */
    void initBoard() {
        this.createLabels();
        this.createMines();
        this.createNums();
    }


    /*
    Fill the board with labels, there are rows*columns of labels as the cell,
    we set the boarder and color of each label.
     */
    private void createLabels() {
        for (int i = 0; i < this.rowsNum; i++) {
            for (int j = 0; j < this.colsNum; j++) {
                GameLabel label = new GameLabel();//let the label show its text in the center
                label.setBounds(j * CELLWIDTH, i * CELLHEIGHT, CELLWIDTH, CELLHEIGHT);//set the bound of each label
                //label.setOpaque(true);
                //label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                //label.setBackground(Color.LIGHT_GRAY);
                labels[i][j] = label;//for later usage of label
                //label.setPos(i, j);
                this.add(label);
            }
        }
    }

    /*
    Randomly create bombs in the panel.
     */
    private void createMines() {
        int countBomb = this.minesNum;
        Random r = new Random();
        while (countBomb != 0) {
            int rRow = r.nextInt(this.rowsNum);
            int rCol = r.nextInt(this.colsNum);
            if (rRow == this.startRow && rCol == this.startCol)
                continue;
            if (labels[rRow][rCol].getLabelType() != LabelType.BOMB) {
                labels[rRow][rCol].setAsBomb();
                countBomb--;
            }
        }
    }

    /*
    Count the number of mines around each empty label.
     */
    private void createNums() {

        for (int i = 0; i < this.rowsNum; i++) {
            for (int j = 0; j < this.colsNum; j++) {

                //traverse the 8 labels around labels[i][j]
                if (labels[i][j].getLabelType() != LabelType.BOMB) {
                    int bombNum = 0;
                    for (int currI = i - 1; currI <= i + 1; currI++) {
                        for (int currJ = j - 1; currJ <= j + 1; currJ++) {
                            if (indexInBounds(currI, currJ) &&
                                    labels[currI][currJ].getLabelType() == LabelType.BOMB) {
                                bombNum++;
                            }
                        }
                    }
                    if (bombNum == 0) {
                        continue;
                    }
                    //set the current label as Numb
                    labels[i][j].setAsNum(new ImageIcon("src\\resource\\" + bombNum + ".png"));
                }

            }
        }
    }

    /*
    check if the input index is in the bound of panel
     */
    private boolean indexInBounds(int i, int j) {
        return i >= 0 && i < rowsNum && j >= 0 && j < colsNum;
    }

    /*
    Covering the panel with buttons.
     */
    private void createButton() {
        for (int i = 0; i < rowsNum; i++) {
            for (int j = 0; j < colsNum; j++) {
                GameButton button = new GameButton(this);
                button.setBounds(j * CELLWIDTH, i * CELLHEIGHT, CELLWIDTH, CELLHEIGHT);
                button.setLoc(i, j);
                button.addMouseListener(new regularMouseListener());
                this.add(button);
                buttons[i][j] = button;
            }
        }
    }


}
