/**
 * The toppest container of the game window, a JFrame, which contains the game board
 * and a bottom bar with reset button and info about the mines remain. Before game start
 * there will pop up a dialog to choose the difficulty or customize the board.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;


public class GameFrame extends JFrame {

    private GameBoard board;
    private JLabel status = new JLabel();

    /**
     * Constructor of the class, takes in the parameters of the size of the board
     *
     * @param row number of row of the board
     * @param col number of column of the board
     * @param mine number of mine of the board
     */
    public GameFrame(int row, int col, int mine) {
        this.setTitle("Mine Sweeper");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //initialize the panel
        board = new GameBoard(row, col, mine);
        this.setSize(board.getBoardWidth() + 14, board.getBoardHeight() + 64);
        setCenter(this);

        //initialize the bottom bar with a reset button and text of mines remain
        JPanel bottomBar = new JPanel(new BorderLayout());//to contain the three components: reset button, number of mines remain and time elapsed.
        JButton reset = new JButton("RESET");
        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reset();
            }
        });
        reset.setBackground(Color.lightGray);
        bottomBar.setBackground(Color.lightGray);
        status.setText("MINES REMAIN: " + board.getMinesNum());
        bottomBar.add(reset, BorderLayout.EAST);
        bottomBar.add(status, BorderLayout.WEST);

        /*
        Create a timer to periodically scan the status of the board and change
        the display of bottom label: current mines remain, win or lose.
         */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                renewBottomLabel();
            }
        }, 0, 50);

        //add the components to the frame
        this.setLayout(new BorderLayout());
        this.add(board, BorderLayout.CENTER);
        this.add(bottomBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    //Change the title of bottom status label
    private void renewBottomLabel() {
        if (board.isLost) {
            status.setText("BOOM! EXPLODED!");
        } else if (board.isWin()) {
            board.openAll();
            status.setText("MISSION ACCOMPLISHED!");
        } else {
            status.setText("MINES REMAIN: " + board.minesRemain);
        }
    }

    //reset the board to restart a game
    private void reset() {
        //paint a new game board
        this.remove(board);
        this.board = new GameBoard(this.board.getRowsNum(), this.board.getColsNum(), this.board.getMinesNum());
        this.add(board, BorderLayout.CENTER);
        repaint();
    }

    /**
     * Set the location of a GUI component in the center of screen
     *
     * @param c the component to be set
     */
    public static void setCenter(Component c){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - c.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - c.getHeight()) / 2);
        c.setLocation(x, y);
    }
}



