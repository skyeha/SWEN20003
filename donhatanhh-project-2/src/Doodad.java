import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Doodad {
    protected Point position;
    protected final Image image;

    protected Rectangle boundedRect;

    public Doodad(Point point, String imagePath) {
        position = point;
        image = new Image(imagePath);
        boundedRect = new Rectangle(position, image.getWidth(), image.getHeight());
    }

    // THis function renders the object
    protected void draw() {image.drawFromTopLeft(position.x, position.y); }
}
