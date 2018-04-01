import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Line {

	private Canvas canvas;
	private Color color;
	private ArrayList<Point> line;
	private int pen_size;
	private boolean reflection;

	public void setPenSize(int pen_size) { this.pen_size = pen_size; }
	public void setColor(Color color) { this.color = color; }
	public void addPoint(Point point) {
		line.add(point);
	}
	public void toggleReflection() {
		reflection = !reflection;
	}

	Line(Canvas canvas) {
		this.canvas = canvas;
		line = new ArrayList<>();
		color = canvas.getColor();
		reflection = canvas.getReflection();
		pen_size = canvas.getPenSize();
	}

	public void drawLine(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(pen_size));

		// For every sector
		for (int i = 0 ; i < canvas.getSectors() ; i++) {

			Point first, second;
			Iterator<Point> iterator = line.iterator();

			// If it is a single point
			if (line.size() == 1) {

				// Load it and draw a rectangle where the mouse is
				first = line.get(0);
				g2d.drawRect((int) first.getX(), (int) first.getY(), pen_size, pen_size);

				// If it should be reflected, draw it on the other side of the sector
				if (reflection) { g2d.drawRect(-(int) first.getX(), (int) first.getY(), pen_size, pen_size); }

				// Rotate so that next point will be drawn on the next sector
				g2d.rotate(canvas.getAngle()*Math.PI/180);

			} else if (line.size() > 1) {

				// If its a line, load first point
				first = iterator.next();

				while (iterator.hasNext()) {

					// Load next point
					second = iterator.next();

					// Draw a line between the points
					g2d.drawLine((int) first.getX(), (int) first.getY(), (int) second.getX(), (int) second.getY());
					if (reflection) { g2d.drawLine(-(int) first.getX(), (int) first.getY(), -(int) second.getX(), (int) second.getY()); }

					// Move to the next pair of points
					first = second;
				}
				// Rotate so that next line will be drawn on the next sector
				g2d.rotate(canvas.getAngle()*Math.PI/180);
			}
		}

	}


}
