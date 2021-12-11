/**
 * This is the entrance of the mine sweeper game, it is basically a JFrame and contains
 * the GameBoard. There are also some auxiliary functionalities such as restart game button,
 * setup popup, game over popup etc.
 *
 * @Author: Zhongshi Luo
 * @Date: Dec.1.2021
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameDriver {
    public static void main(String[] args) {
        SettingDialog dialog = new SettingDialog();
    }
}

/*
The dialog of setting panel, it offers three preset: easy, intermediate, difficult and
also allows players to customize the number of row, column and mine.
 */
class SettingDialog extends JDialog{
    private int rows;
    private int cols;
    private int mines;

    public SettingDialog() {
        setTitle("Settings");
        setSize(400, 250);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setLayout(null);
        setResizable(false);
        setVisible(true);

        //add elements
        JLabel chooseDiff = new JLabel("Preset Difficulty");
        chooseDiff.setBounds(20, 15, 105,20);
        chooseDiff.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(chooseDiff);

        JButton easy = new JButton("Easy");
        easy.setBounds(20,45, 100, 30);
        easy.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(9, 14, 15);
                dispose();
            }
        });
        easy.setBackground(Color.lightGray);
        this.add(easy);

        JButton intermediate = new JButton("Intermediate");
        intermediate.setBounds(145,45, 100, 30);
        intermediate.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(15, 20, 40);
                dispose();
            }
        });
        intermediate.setBackground(Color.lightGray);
        this.add(intermediate);

        JButton expert = new JButton("Expert");
        expert.setBounds(270,45, 100, 30);
        expert.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(20, 30, 100);
                dispose();
            }
        });
        expert.setBackground(Color.lightGray);
        this.add(expert);

        JLabel custom = new JLabel("Custom");
        custom.setBounds(20, 110, 60,20);
        custom.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(custom);

        JLabel row = new JLabel("Rows <=30");
        row.setBounds(20, 140, 70,20);
        row.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(row);

        JLabel col = new JLabel("Columns <= 58");
        col.setBounds(110, 140, 90,20);
        col.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(col);

        JLabel mine = new JLabel("Mines <r*c");
        mine.setBounds(220, 140, 70,20);
        mine.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(mine);

        JButton start = new JButton("GO");
        start.setBackground(Color.lightGray);
        this.add(start);

        //row<30
        TextField rowText = new TextField(70);
        rowText.setBounds(20, 170, 70, 20);
        this.add(rowText);

        //col<58
        TextField colText = new TextField(90);
        colText.setBounds(110, 170, 90, 20);
        this.add(colText);

        //mines < row*col
        TextField mineText = new TextField(70);
        mineText.setBounds(220, 170, 70, 20);
        this.add(mineText);


        start.setBounds(310, 140, 50,50);
        start.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        start.addActionListener(new SettingListener(rowText, colText, mineText, this));
    }

}

class SettingListener implements ActionListener{
    private TextField row, col, mine;
    JDialog owner;

    public SettingListener(TextField row, TextField col, TextField mine, JDialog owner){
        this.row = row;
        this.col = col;
        this.mine = mine;
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try{
            int rows = Integer.parseInt(row.getText());
            int cols = Integer.parseInt(col.getText());
            int mines = Integer.parseInt(mine.getText());

            if(rows > 30 || rows <=0 || cols <= 0 || cols > 58 || mines >= rows*cols || mines <= 0){
                throw new IllegalArgumentException("Parameter out of bounds!");
            }else{
                new GameFrame(rows, cols, mines);
                owner.dispose();
            }
        } catch (IllegalArgumentException ex) {
            JFrame frame = new JFrame();
            frame.setBounds(500, 500, 100,50);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
            frame.setLocation(x, y);
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
            JLabel label = new JLabel("Invalid Input!");
            frame.add(label);
        }

    }
}