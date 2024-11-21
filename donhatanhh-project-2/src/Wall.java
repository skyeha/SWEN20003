import bagel.util.Point;
import bagel.util.Rectangle;

public class Wall extends Doodad {
    public Wall(Point point) {
        super(point, "res/wall.png");
    }

    public Rectangle getBound() {
        return super.boundedRect;
    }
}
