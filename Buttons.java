import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

class Buttons extends JPanel {

	// Save settings whenever user wants to erase
	private Color previous_color;
	private int previous_pen_size;

	Buttons(Canvas canvas, Gallery gallery) {

		setLayout(new GridLayout(2,5));
		setBackground(Color.WHITE);

		// Create ArrayList of buttons so we can later add borders and background color to every one of them at once
		ArrayList<JComponent> buttons = new ArrayList<>();

		// Toggle sector lines
		JButton sector_lines = new JButton("Sector Lines");
		sector_lines.addActionListener(e -> {
			canvas.toggleSectorLines();
			canvas.repaint();
		});
		buttons.add(sector_lines);

		// Clear canvas
		JButton clear = new JButton("Clear");
		clear.addActionListener(e -> {
			canvas.getLines().clear();
			canvas.repaint();
		});
		buttons.add(clear);

		// Undo previous line
		JButton undo = new JButton("Undo");
		undo.addActionListener(e -> {
			if (canvas.getLines().size() > 0) {
				canvas.undo();
				canvas.repaint();
			}
		});
		buttons.add(undo);

		// Choose number of sectors
		JPanel sectors_button = new JPanel();
		sectors_button.setLayout(new GridLayout(1,2));

		// Add label to the button
		JLabel sectors_text = new JLabel("Sectors");
		sectors_text.setForeground(Color.WHITE);
		sectors_text.setHorizontalAlignment(JTextField.CENTER);
		sectors_text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		sectors_button.add(sectors_text);

		// Add spinner to change number of sectors, position it in center
		SpinnerModel sectors = new SpinnerNumberModel(canvas.getSectors(), 1, 36, 1);
		JSpinner sector_spinner = new JSpinner(sectors);
		JComponent sectors_editor = sector_spinner.getEditor();
		JSpinner.DefaultEditor sectors_spinner_editor = (JSpinner.DefaultEditor) sectors_editor;
		((JSpinner.DefaultEditor) sectors_editor).getTextField().setForeground(Color.WHITE);
		((JSpinner.DefaultEditor) sectors_editor).getTextField().setBackground(Color.BLACK);
		((JSpinner.DefaultEditor) sectors_editor).getTextField().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		sectors_spinner_editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		sector_spinner.setBorder(null);
		sector_spinner.addChangeListener(e -> {
			JSpinner input = (JSpinner) e.getSource();
			canvas.setSectors((int) input.getValue());
			canvas.setAngle(360 / (double)canvas.getSectors());
			canvas.repaint();
		});
		sectors_button.add(sector_spinner);
		sectors_button.setBorder(new LineBorder(Color.GRAY, 1));
		buttons.add(sectors_button);

		// Save doily to the Gallery
		JButton save = new JButton("Save");
		save.addActionListener(e -> {
			if (gallery.getGallery().size() < 12) {
				if(!gallery.getInitialHeightSet()) { gallery.setInitialHeight(gallery.getHeight()); }

				// Take screenshot of the canvas
				BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), 1);

				// Minimize the image to fit the gallery
				Image scaled = image.getScaledInstance(gallery.getWidth()-2, gallery.getInitialHeight()/3-1, Image.SCALE_SMOOTH);
				canvas.paint(image.getGraphics());
				JLabel img = new JLabel(new ImageIcon(scaled));
				img.setBorder(new LineBorder(Color.GRAY, 1));
				img.setCursor(new Cursor(Cursor.HAND_CURSOR));
				gallery.save(img);
			} else {
				JOptionPane.showMessageDialog(null, "You can save max 12 doilies", "Warning", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		buttons.add(save);

		// Toggle reflection
		JButton reflection = new JButton("Reflection");
		reflection.addActionListener(e -> {
			canvas.toggleReflection();
			canvas.repaint();
		});
		buttons.add(reflection);

		// Toggle eraser
		JButton erase = new JButton("Eraser");
		erase.addActionListener(e -> {
			if(canvas.getErase()) {
				canvas.setPenSize(previous_pen_size);
				canvas.setColor(previous_color);
				canvas.toggleErase();
			} else {
				previous_pen_size = canvas.getPenSize();
				previous_color = canvas.getColor();
				canvas.toggleErase();
				canvas.setColor(Color.BLACK);
			}
		});
		buttons.add(erase);

		// Redo previous undo
		JButton redo = new JButton("Redo");
		redo.addActionListener(e -> {
			if(canvas.getDeletedLines().size() > 0) {
				canvas.redo();
				canvas.repaint();
			}
		});
		buttons.add(redo);

		// Choose size of the pen
		JPanel pen_size_button = new JPanel();
		pen_size_button.setLayout(new GridLayout(1,2));

		// Add a label to the left of the button
		JLabel pen_size_text = new JLabel("Pen Size");
		pen_size_text.setForeground(Color.WHITE);
		pen_size_text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		pen_size_text.setHorizontalAlignment(JTextField.CENTER);
		pen_size_button.add(pen_size_text);

		// Add a spinner to the right of the button
		SpinnerModel pen_size = new SpinnerNumberModel(canvas.getPenSize(), 1, 15, 1);
		JSpinner pen_size_spinner = new JSpinner(pen_size);
		JComponent pen_size_editor = pen_size_spinner.getEditor();
		JSpinner.DefaultEditor pen_size_spinner_editor = (JSpinner.DefaultEditor) pen_size_editor;
		((JSpinner.DefaultEditor) pen_size_editor).getTextField().setForeground(Color.WHITE);
		((JSpinner.DefaultEditor) pen_size_editor).getTextField().setBackground(Color.BLACK);
		((JSpinner.DefaultEditor) pen_size_editor).getTextField().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		pen_size_spinner_editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		pen_size_spinner.setBorder(null);
		pen_size_spinner.addChangeListener(e -> {
			JSpinner input = (JSpinner) e.getSource();
			canvas.setPenSize((int) input.getValue());
			canvas.repaint();
		});
		pen_size_button.add(pen_size_spinner);
		pen_size_button.setBorder(new LineBorder(Color.GRAY, 1));
		buttons.add(pen_size_button);

		// Delete doily from Gallery
		JButton delete = new JButton("Delete");
		delete.addActionListener(e -> {
			if(gallery.getGallery().size() == 0) {
				JOptionPane.showMessageDialog(null, "There is nothing to delete!", "Warning", JOptionPane.INFORMATION_MESSAGE);
			} else {
				gallery.delete();
				gallery.repaint();
			}
		});
		buttons.add(delete);

		// Add colors and cursors to every button, then add them to JPanel
		for (JComponent button : buttons) {
			button.setBackground(Color.BLACK);
			button.setForeground(Color.WHITE);
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
			add(button);
		}
	}
}
