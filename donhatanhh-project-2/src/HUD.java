import bagel.Image;
import bagel.util.Point;

public abstract class HUD<T> {
    protected T image;
    protected Point position;

    public HUD(Point point, T format) {
        position = point;
        image = format ;
    }
}
