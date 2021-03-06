import java.awt.*;
import javax.swing.*;

class Window extends JFrame {

	private int width;
	private int height;

	Window() {

		// Make window fullscreen
		getContentPane().setPreferredSize(new Dimension(1200,700));
		setVisible(true);

		width = 1200;
		height = 700;

		// Add main panel with BorderLayout that will contain every element
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// Add canvas to the main panel
		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width/4*3, height/10*9));
		panel.add(canvas, BorderLayout.CENTER);

		// Create gallery so we can pass it in Buttons constructor and add functionality
		Gallery gallery = new Gallery();

		// Create panel on the right which contains exit button, gallery, and color picker then add it to main panel
		RightPanel right_panel = new RightPanel(this, gallery, canvas);
		panel.add(right_panel, BorderLayout.EAST);

		// Add panel for buttons
		JPanel button_panel = new JPanel();
		button_panel.setPreferredSize(new Dimension(width, height/10));
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));
		panel.add(button_panel, BorderLayout.SOUTH);

		// Create buttons, then add them to the panel
		Buttons buttons = new Buttons(canvas, gallery);
		button_panel.add(buttons);

		add(panel);
		pack();
	}

	public int fetchWidth() { return width; }
	public int fetchHeight() { return height; }

}
