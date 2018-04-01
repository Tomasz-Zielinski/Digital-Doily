import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

class ColorPicker extends JPanel {

    ColorPicker(Canvas canvas) {

        setLayout(new GridLayout(1, 12));
        setBorder(new LineBorder(Color.GRAY, 1));
        setBackground(Color.GRAY);

        ArrayList<Color> color_list = new ArrayList<>();
        color_list.add(Color.BLACK);
        color_list.add(Color.DARK_GRAY);
        color_list.add(Color.GRAY);
        color_list.add(Color.LIGHT_GRAY);
        color_list.add(Color.WHITE);
        color_list.add(Color.MAGENTA);
        color_list.add(Color.BLUE);
        color_list.add(Color.CYAN);
        color_list.add(Color.GREEN);
        color_list.add(Color.YELLOW);
        color_list.add(Color.ORANGE);
        color_list.add(Color.RED);

        // Add button for every color in ArrayList
        for (Color color : color_list) {
            JButton btn = new JButton();
            btn.setBackground(color);
            btn.setBorder(new EmptyBorder(20,20,10,10));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(btn);
            btn.addActionListener(e -> {
                canvas.setColor(color);
            });
        }
    }

}
