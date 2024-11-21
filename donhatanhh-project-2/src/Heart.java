import bagel.util.Point;
import bagel.Image;
public class Heart extends HUD<Image> {

    public Heart(Point point) {
        super(point, new Image("res/heart.png"));
    }

    public void draw(){
            super.image.drawFromTopLeft(super.position.x, super.position.y);
    }
}
