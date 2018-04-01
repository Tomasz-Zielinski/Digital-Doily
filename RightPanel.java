import javax.swing.*;
import java.awt.*;

class RightPanel extends JPanel {

    RightPanel(Window window, Gallery gallery, Canvas canvas) {

        // Add exit button on the top right
        JButton exit = new JButton("Exit");
        exit.setBackground(Color.BLACK);
        exit.setForeground(Color.WHITE);
        exit.addActionListener(e -> window.dispose());
        exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exit.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

        // Add scroll to the right of gallery
        JScrollPane scroll = new JScrollPane(gallery, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getVerticalScrollBar().setUnitIncrement(32);
        scroll.getVerticalScrollBar().setBackground(Color.BLACK);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(window.fetchWidth()/5, window.fetchHeight()/6*5));

        // Add everything
        add(exit, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(new ColorPicker(canvas), BorderLayout.SOUTH);
    }
}
