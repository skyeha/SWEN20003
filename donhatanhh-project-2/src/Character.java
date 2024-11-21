import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

public abstract class Character {
    protected Point initialPos;
    protected ArrayList<Image> image = new ArrayList<Image>();
    protected Rectangle boundedRect;
    protected double NORMAL_SPEED;
    protected double FRENZY_SPEED;

    protected boolean isFrenzied = false;
    protected double imageDirection;

    public Character(Point point, String[] imagePath) {
        initialPos = point;
        for (String s: imagePath) {
            image.add(new Image(s));
        }
        boundedRect = new Rectangle(initialPos, image.get(0).getWidth(), image.get(0).getHeight());
    }
}
