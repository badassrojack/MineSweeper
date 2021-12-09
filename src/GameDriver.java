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

public class GameDriver {
    public static void main(String[] args) {
        GameFrame frame = new GameFrame("Mine Sweeper");
        frame.setVisible(true);
    }
}
