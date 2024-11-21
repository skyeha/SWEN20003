import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

public class Pacman extends Character {
    // Instantiate Pacman's current position while moving
    private double currentX;
    private double currentY;

    private int frameCount = 0;

    public Pacman(Point point) {
        super(point, new String[]{"res/pac.png", "res/pacOpen.png"});
        super.NORMAL_SPEED = 3;
        super.FRENZY_SPEED = 4;
        currentX = point.x;
        currentY = point.y;
        imageDirection = 0;
    }

    // This function return Pacman image direction
    private double getDirection(Input input) {
        if (input.isDown(Keys.DOWN)) {
            return -(3.0 * Math.PI) / 2.0;
        } else if (input.isDown(Keys.UP)) {
            return -(Math.PI) / 2.0;
        } else if (input.isDown(Keys.LEFT)) {
            return Math.PI;
        }
        return 0;
    }

    // This function return the speed accordingly to the game mode
    private double getSpeed() {
        if (super.isFrenzied) {
            return super.FRENZY_SPEED;
        }
        return super.NORMAL_SPEED;
    }

    // This function replicates biting movement of pacman
    private Image biting() {
        Image PACMAN_CLOSE = super.image.get(0);
        Image PACMAN_OPEN = super.image.get(1);
        if (0 <= frameCount && frameCount <= 15) {
            frameCount++;
            return PACMAN_CLOSE;
        } else if (15 < frameCount && frameCount <= 30) {
            frameCount++;
            return PACMAN_OPEN;
        } else {
            frameCount = 0;
            return PACMAN_CLOSE;
        }
    }

    // This function move our pacman
    public void move(Input input, ArrayList<Wall> walls) {
        Image currentPac = biting();
        double currentSpeed = getSpeed();

        if (willHitWall(walls,input)) {
            imageDirection = getDirection(input);
            currentPac.drawFromTopLeft(currentX, currentY, new DrawOptions().setRotation(imageDirection));
            return;
        }

        if (input.isDown(Keys.DOWN)) {
            imageDirection = getDirection(input);
            this.currentY += currentSpeed;
        }

        if (input.isDown(Keys.UP)) {
            imageDirection = getDirection(input);
            this.currentY -= currentSpeed;
        }

        if (input.isDown(Keys.LEFT)) {
            imageDirection = getDirection(input);
            this.currentX -= currentSpeed;
        }

        if (input.isDown(Keys.RIGHT)) {
            imageDirection = getDirection(input);
            this.currentX += currentSpeed;
        }


        super.boundedRect.moveTo(new Point(currentX, currentY));
        currentPac.drawFromTopLeft(currentX, currentY, new DrawOptions().setRotation(imageDirection));
    }

    // This function check if the movement will result in a collision with the wall
    public boolean willHitWall(ArrayList<Wall> walls, Input input) {
        double x = currentX, y = currentY;
        double currentSpeed = getSpeed();
        if (input.isDown(Keys.DOWN)) {
            y += currentSpeed;
        }

        if (input.isDown(Keys.UP)) {
            y -= currentSpeed;
        }

        if (input.isDown(Keys.LEFT)) {
            x -= currentSpeed;
        }

        if (input.isDown(Keys.RIGHT)) {
            x += currentSpeed;
        }

        Rectangle nextRect = new Rectangle(new Point(x,y),
                super.boundedRect.right() - super.boundedRect.left(),
                super.boundedRect.bottom() - super.boundedRect.top());

        for (Wall wall: walls) {
            if (nextRect.intersects(wall.getBound())) {
                return true;
            }
        }
        return false;
    }

    // This function reset pacman's location upon collision with ghost
    public void reset() {
        currentX = super.initialPos.x;
        currentY = super.initialPos.y;
        imageDirection = 0;
        super.boundedRect.moveTo(new Point(currentX, currentY));
    }

}
