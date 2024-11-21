import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Wall extends Entities {
    // Instantiate wall image
    private static final Image WALL_IMAGE = new Image("res/wall.png");

    // Wall constructor
    public Wall(double x, double y) {
        super(x, y);
    }


    // Draw wall
    public void draw() {
        WALL_IMAGE.drawFromTopLeft(this.returnXPos(), this.returnYPos());
    }

    // Get fitted bounding rectangle for wall
    public Rectangle getBound() {
        return new Rectangle(super.returnXPos(), super.returnYPos(),
                WALL_IMAGE.getWidth(), WALL_IMAGE.getHeight());
    }
}
