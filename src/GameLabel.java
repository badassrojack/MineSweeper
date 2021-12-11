/**
 * This is the label on the game board, which extends JLabel class and have a LabelType variable to record the
 * type of itself. The default label is empty type. Some of them will be switch to
 * bomb and number in the process of game board initializing.
 */

import javax.swing.*;
import java.awt.*;

public class GameLabel extends JLabel {
    private LabelType labelType = LabelType.EMPTY;//set the default label type as EMPTY

    /**
     * The default constructor, call the super constructor of JLabel, and set the
     * icon of the label as normal background.
     */
    public GameLabel() {
        super();
        ImageIcon icon = new ImageIcon("src\\resource\\labelBG.png");
        icon.setImage(icon.getImage().getScaledInstance(GameBoard.CELLWIDTH, GameBoard.CELLHEIGHT, Image.SCALE_DEFAULT));
        this.setIcon(icon);
    }

    /**
     * Getter of the label type.
     *
     * @return type of the label
     */
    public LabelType getLabelType() {
        return labelType;
    }

    /**
     * Setter of the label type.
     *
     * @param labelType the type to be setted for the label
     */
    public void setLabelType(LabelType labelType) {
        this.labelType = labelType;
    }

    /**
     * Set the calling label as a bomb.
     */
    public void setAsBomb() {
        this.setLabelType(LabelType.BOMB);
        ImageIcon icon = new ImageIcon("src\\resource\\bomb.png");
        icon.setImage(icon.getImage().getScaledInstance(GameBoard.CELLWIDTH, GameBoard.CELLHEIGHT, Image.SCALE_DEFAULT));
        this.setIcon(icon);
    }

    /**
     * Set the calling label as a number
     */
    public void setAsNum(ImageIcon icon) {
        setLabelType(LabelType.NUMBER);
        icon.setImage(icon.getImage().getScaledInstance(GameBoard.CELLWIDTH, GameBoard.CELLHEIGHT, Image.SCALE_DEFAULT));
        this.setIcon(icon);
    }

}
