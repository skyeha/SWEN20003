import bagel.*;
import bagel.util.Rectangle;

public class Dot extends Entities {
    // Instantiate dot image
    private static final Image DOT_IMAGE = new Image("res/dot.png");

    // Instantiate dot visibility
    private boolean visible = true;

    // Dot constructor
    public Dot(double x, double y) {
        super(x, y);
    }

    // Draw dot
    public void draw() {
        if (visible) {
            DOT_IMAGE.drawFromTopLeft(super.returnXPos(), super.returnYPos());
        }
    }

    // Get fitted bounding rectangle for dot
    public Rectangle getBound() {
        return new Rectangle(super.returnXPos(), super.returnYPos(),
                DOT_IMAGE.getWidth(), DOT_IMAGE.getHeight());
    }

    // Dot visibility
    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    // Return visibility
    public boolean getVisibility() {
        return visible;
    }

}
