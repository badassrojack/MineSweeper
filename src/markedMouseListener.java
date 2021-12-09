/**
 * This is the specified mouse listener of the marked button, it only receives mouse
 * event of right-click, to reset the marked button to regular button.
 */

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class markedMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            GameButton.unmark((GameButton) e.getSource());
        }
    }
}
