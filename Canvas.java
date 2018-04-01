import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Canvas extends JPanel {

	private Color color = Color.YELLOW;
	private int pen_size = 5;
	private int sectors = 12;
	private double angle = 360 / (double) sectors;
	private boolean sector_lines = true;
	private boolean reflection = false;
	private boolean erase = false;
	private Line line = new Line(this);
	private ArrayList<Line> lines = new ArrayList<>();
	private ArrayList<Line> deleted_lines = new ArrayList<>();

	Canvas() {

		setBackground(Color.BLACK);

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { addPoint(e); }

			@Override
			public void mouseReleased(MouseEvent e) {
				lines.add(line);
				line = new Line(Canvas.this);
			}
			public void mouseEntered(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
			public void mousePressed(MouseEvent e) { }
		});

		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				addPoint(e);
			}
			public void mouseMoved(MouseEvent e) { }
		});

	}

	// Store removed line, remove it from the screen
	public void undo() {
		deleted_lines.add(lines.get(lines.size()-1));
		lines.remove(lines.size()-1);
	}

	// Add the last line from deleted lines to screen, then remove it from deleted lines
	public void redo() {
		lines.add(deleted_lines.get(deleted_lines.size()-1));
		deleted_lines.remove(deleted_lines.size()-1);
	}

	// Capture mouse position, add point to the current line depending on whether the number of sectors is even or odd
	private void addPoint(MouseEvent e) {
		int x,y;
		if (sectors % 2 == 1) {
			x = e.getX() - getWidth() / 2;
			y = e.getY() - getHeight() / 2;
			line.addPoint(new Point(x, y));
		} else {
			x = e.getX() - getWidth() / 2;
			y = e.getY() - getHeight() / 2;
			line.addPoint(new Point(-x, -y));
		}
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Paint in the center of canvas
		g2d.translate(getWidth() / 2,getHeight() / 2);
		g2d.setStroke(new BasicStroke(pen_size));
		g2d.setColor(color);

		// Draw every line stored in lines ArrayList
		lines.forEach(line -> line.drawLine(g2d));
		line.drawLine(g2d);

		// Draw sector lines if needed
		if (sector_lines) {
			for (int i = 0 ; i < sectors ; i++) {
				g2d.setColor(Color.WHITE);
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, 0, 0, 1000);
				g2d.rotate(angle*Math.PI/180);
			}
		}
	}

	public ArrayList<Line> getLines() { return lines; }
	public ArrayList<Line> getDeletedLines() { return deleted_lines; }

	public void toggleSectorLines() { sector_lines = !sector_lines; }

	public int getPenSize() { return pen_size; }
	public void setPenSize(int pen_size) {
		// Canvas pen_size determines current pen size when drawing
		this.pen_size = pen_size;
		// Line pen_size determines pen size of each line during rendering
		line.setPenSize(pen_size);
	}

	public boolean getReflection() { return reflection; }
	public void toggleReflection() {
		reflection = !reflection;
		line.toggleReflection();
	}

	public void setColor(Color color) {
		this.color = color;
		line.setColor(color);
	}

	public int getSectors() { return sectors; }
	public void setSectors(int sectors) {  this.sectors = sectors; }

	public void setAngle(double angle) { this.angle = angle; }
	public double getAngle() {return angle; }

	public void toggleErase() { erase = !erase; }
	public boolean getErase() { return erase; }

	public void paint(Graphics g) { super.paint(g); }
	public Color getColor() { return color; }
}
