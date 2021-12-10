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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

    public SettingDialog() {
        setTitle("Settings");
        setSize(400, 300);
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
        JLabel chooseDiff = new JLabel("Preset Difficulty:");
        chooseDiff.setBounds(20, 15, 105,20);
        chooseDiff.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(chooseDiff);

        JButton easy = new JButton("Easy");
        easy.setBounds(20,45, 100, 30);
        easy.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(9, 14, 15);
                dispose();
            }
        });
        this.add(easy);

        JButton intermediate = new JButton("Intermediate");
        intermediate.setBounds(145,45, 100, 30);
        intermediate.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(15, 20, 40);
                dispose();
            }
        });
        this.add(intermediate);

        JButton expert = new JButton("Expert");
        expert.setBounds(270,45, 100, 30);
        expert.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(20, 30, 100);
                dispose();
            }
        });
        this.add(expert);

        JLabel custom = new JLabel("Custom:");
        custom.setBounds(20, 100, 60,20);
        custom.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(custom);

        JLabel row = new JLabel("Rows:");
        row.setBounds(50, 130, 45,20);
        row.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(row);

        JLabel col = new JLabel("Columns:");
        col.setBounds(165, 130, 60,20);
        col.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(col);

        JLabel mine = new JLabel("Mines:");
        mine.setBounds(295, 130, 45,20);
        mine.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(mine);

        JTextField rowText = new JTextField(10);
        rowText.setBounds(50, 140, 45, 20);
        this.add(rowText);

    }
}