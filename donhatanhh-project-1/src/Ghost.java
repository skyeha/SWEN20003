import bagel.*;
import bagel.util.Rectangle;

public class Ghost extends Entities {
    // Instantiate ghost image
    private static final Image GHOST_IMAGE = new Image("res/ghostRed.png");

    // Ghost constructor
    public Ghost(double x, double y) {
        super(x,y);
    }

    // Draw ghost
    public void draw() {
        GHOST_IMAGE.drawFromTopLeft(this.returnXPos(), this.returnYPos());
    }

    // Get fitted bounding rectangle for ghost
    public Rectangle getBound() {
        return new Rectangle(super.returnXPos(), super.returnYPos(),
                GHOST_IMAGE.getWidth(), GHOST_IMAGE.getHeight());
    }

}
