import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//TODO:
//2.Won't die in the first move
// 3.Mines remain
// 4.setting panel

public class GameFrame extends JFrame {

    public GameBoard board;
//    private int rows;
//    private int cols;



    public GameFrame(String title){
        super(title);
        this.setDefaultCloseOperation(3);//EXIT_ON_CLOSE
//        Container container = frame.getContentPane();

        board = new GameBoard(20, 15);
//        this.rows = board.getRowsNum();
//        this.cols = board.getColsNum();

        JPanel bottomBar = new JPanel(new BorderLayout());//to contain the three components: reset button, number of mines remain and time elapsed.
        JButton reset = new JButton("RESET");
        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reset();
            }
        });
        JLabel mines = new JLabel("MINES REMAIN:");

        bottomBar.add(reset, BorderLayout.EAST);
        bottomBar.add(mines, BorderLayout.WEST);

        this.setSize(board.getBoardWidth() + 14, board.getBoardHeight()+ 64);


//        frame.getContentPane().add(board);
        this.setLayout(new BorderLayout());
        this.add(board, BorderLayout.CENTER);
        this.add(bottomBar, BorderLayout.SOUTH);

        this.setResizable(false);
        this.setVisible(true);
    }

    private void reset(){
        //1.repaint a new game board
        this.remove(board);
        this.board = new GameBoard(this.board.getRowsNum(), this.board.getColsNum());
        this.add(board, BorderLayout.CENTER);
        //2.reset the bomb count

        repaint();
    }
}
