import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The toppest container of the game window, a JFrame, which contains the game board
 * and a bottom bar with reset button and info about the mines remain. Before game start
 * there will pop up a dialog to choose the difficulty or customize the board.
 */
public class GameFrame extends JFrame {

    private GameBoard board;
    private JLabel status = new JLabel();

    public GameFrame(int row, int col, int mine) {
        this.setTitle("Mine Sweeper");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //pop up the setting dialog and get the input info
//        SettingDialog dialog = new SettingDialog();
//        dialog.setAlwaysOnTop(true);

        //initialize the panel
        board = new GameBoard(row, col, mine);
        this.setSize(board.getBoardWidth() + 14, board.getBoardHeight() + 64);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

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
        status.setText("MINES REMAIN: "+board.getMinesNum());
        bottomBar.add(reset, BorderLayout.EAST);
        bottomBar.add(status, BorderLayout.WEST);

        /*
        Create a timer to periodically scan the status of the board and change
        the display of bottom label: current mines remain, win or lose.
         */
//        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
//        ses.scheduleAtFixedRate(renewBottomLabel(), 0, 200, TimeUnit.MICROSECONDS);
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

    private void renewBottomLabel(){
        if(board.isLost){
            status.setText("BOOM! EXPLODED!");
        }else if(board.isWin()){
            board.openAll();
            status.setText("MISSION ACCOMPLISHED!");
        }else{
            status.setText("MINES REMAIN: "+board.minesRemain);
        }
    }

    private void reset() {
        //paint a new game board
        this.remove(board);
        this.board = new GameBoard(this.board.getRowsNum(), this.board.getColsNum(), this.board.getMinesNum());
        this.add(board, BorderLayout.CENTER);
        repaint();
    }
}



