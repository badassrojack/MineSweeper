import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//TODO: Won't die in the first move; Mines remain; setting panel

public class GameFrame extends JFrame {

    public GameBoard board;

    public GameFrame(String title){
        super(title);
        this.setDefaultCloseOperation(3);//EXIT_ON_CLOSE
//        Container container = frame.getContentPane();
        this.setLocationRelativeTo(null);
        board = new GameBoard(5, 5);


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
        this.board = new GameBoard(this.board.getRowsNum(), this.board.getColsNum(), this.board.getMinesNum());
        this.add(board, BorderLayout.CENTER);
        //2.reset the bomb count

        repaint();
    }
}
