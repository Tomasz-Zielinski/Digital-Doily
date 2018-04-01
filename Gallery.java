import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Gallery extends JPanel {

	// Initial height is needed so that images added to the gallery will not stretch every time we add to gallery
    private JLabel selected;
    private int initialHeight;
    private boolean initialHeightSet = false;
    private ArrayList<JLabel> gallery = new ArrayList<>();

    Gallery()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.BLACK);
    }

    public void save(JLabel image) {
        image.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {
                gallery.forEach(el -> el.setBorder(new LineBorder(Color.GRAY, 1)));
                selected = image;
                selected.setBorder(new LineBorder(Color.GREEN, 1));
            }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });
        gallery.add(image);
        add(image);
        gallery.forEach(e -> e.setBorder(new LineBorder(Color.GRAY, 1)));
        selected = image;
        image.setBorder(new LineBorder(Color.GREEN, 1));
        revalidate();
    }

    public void delete() {
        if(selected == null) return;
		gallery.remove(selected);
		remove(selected);
		if(gallery.size() > 0) selected = gallery.get(gallery.size()-1);
		gallery.forEach(e -> e.setBorder(new LineBorder(Color.GRAY, 1)));
		selected.setBorder(new LineBorder(Color.GREEN, 1));
		revalidate();
	}

    public void setInitialHeight(int initialHeight) {
        this.initialHeight = initialHeight;
        if(!initialHeightSet) initialHeightSet = true;
    }

    public ArrayList<JLabel> getGallery() { return gallery; }
    public int getInitialHeight() { return initialHeight; }
    public boolean getInitialHeightSet() { return initialHeightSet; }
}
