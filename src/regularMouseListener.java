/**
 * This is the specified mouse listener for regular button. If it takes in a mouse event
 * of left click, it discloses the button, if there is a right-click, it marks the button.
 */

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class regularMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            GameButton.markGB((GameButton) e.getSource());
        } else {
            GameButton.disclose((GameButton) e.getSource());
        }
    }
}
