import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ghost extends Character {
    private String name;
    private String movementDirection;
    private boolean visible = true;
    private double currentX;
    private double currentY;

    // Constructor for level 0
    public Ghost(Point point) {
        super(point, new String[] {"res/ghostRed.png"});
        super.NORMAL_SPEED = 0;
        super.FRENZY_SPEED = 0;
        super.imageDirection = 0;
        currentX = point.x;
        currentY = point.y;
    }

    // Constructor for level 1
    public Ghost(Point point,String name, String[] imagePath,
                 double normalSpeed, double frenzySpeed) {
        super(point, imagePath);
        super.NORMAL_SPEED = normalSpeed;
        super.FRENZY_SPEED = frenzySpeed;
        super.imageDirection = 0;
        this.name = name;
        currentX = point.x;
        currentY = point.y;
        // Assigning movement direction for each ghost at the start
        switch (name) {
            case "ghostred":
                movementDirection = "RIGHT";
                break;
            case "ghostblue":
                movementDirection = "DOWN";
                break;
            case "ghostgreen":
                movementDirection = chooseRandom2Direction();
                break;
            case "ghostpink":
                movementDirection = chooseRandom4Direction();
                break;
            default:
                break;
        }
    }

    // This function renders the ghost
    private void draw() {
        if (!isFrenzied) {
            super.image.get(0).drawFromTopLeft(currentX, currentY);
        } else {
            super.image.get(1).drawFromTopLeft(currentX, currentY);
        }
    }

    public void move() {
        draw();
    }

    // This function manage the movement of the ghosts in level 1
    public void move(ArrayList<Wall> walls) {
        if (!visible) {
            return;
        }
        double speed = 0;
        switch (name) {
            case "ghostred":
            case "ghostblue":
            case "ghostgreen":
                wallCollide(walls);
                speed = !super.isFrenzied ? super.NORMAL_SPEED : super.FRENZY_SPEED; // check if the game is in frenzy state or not
                moveInDirection(speed, movementDirection);
                draw();
                break;
            case "ghostpink":
                movePink(walls);
                draw();
                break;
            default:
                draw();
                break;
        }
    }

    // This function changes the direction of the movement for 3 ghost Red, Blue, and Green
    private void changeDirection() {
        super.NORMAL_SPEED = -super.NORMAL_SPEED;
        super.FRENZY_SPEED = -super.FRENZY_SPEED;
    }
    // This function checks if ghosts Red, Blue, and Green hit the wall
    private void wallCollide(ArrayList<Wall> walls) {
        for (Wall wall: walls) {
            if (super.boundedRect.intersects(wall.boundedRect)) {
                changeDirection();
                break;
            }
        }
    }

    // Because pink ghost is an autopilot evil pacman, I dedicated a whole movement
    // logic for it
    private void movePink(ArrayList<Wall> walls) {
        double speed = !super.isFrenzied ? NORMAL_SPEED : FRENZY_SPEED;
        if (pinkHitWall(walls, speed)) {
            movementDirection = chooseRandom4DirectionExclude(movementDirection);
            draw();
            return;
        }
        moveInDirection(speed, movementDirection);
        draw();

    }

    // Check if the future movement of pink ghost will make it collide with the wall
    private boolean pinkHitWall(ArrayList<Wall> walls, double speed) {
        double x = currentX; double y = currentY;

        switch (movementDirection) {
            case "UP":
                y -= speed;
                break;
            case "DOWN":
                y += speed;
                break;
            case "LEFT":
                x -= speed;
                break;
            case "RIGHT":
                x += speed;
                break;
            default:
                break;
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

    // This function choose random direction for ghost Green
    private String chooseRandom2Direction() {
        ArrayList<String> directionList = new ArrayList<String> (
                Arrays.asList("DOWN","RIGHT")
        );
        Random rand = new Random();

        return directionList.get(rand.nextInt(directionList.size()));
    }

    // This function choose random direction for ghost Pink
    private String chooseRandom4Direction() {
        ArrayList<String> directionList = new ArrayList<String> (
                Arrays.asList("UP","DOWN","LEFT","RIGHT")
        );
        Random rand = new Random();

        return directionList.get(rand.nextInt(directionList.size()));
    }

    // This function choose random direction for ghost Pink when hitting the wall, excluding the direction it was moving
    // before the collision
    private String chooseRandom4DirectionExclude(String exclusion) {
        ArrayList<String> directionList = new ArrayList<String> (
                Arrays.asList("UP","DOWN","LEFT","RIGHT")
        );
        directionList.remove(exclusion);

        Random rand = new Random();
        return directionList.get(rand.nextInt(directionList.size()));
    }

    // This function moves the ghosts accordingly with it movement direction
    private void moveInDirection(double speed, String direction) {
        switch (direction) {
            case "UP":
                currentY -= speed;
                break;
            case "DOWN":
                currentY += speed;
                break;
            case "LEFT":
                currentX -= speed;
                break;
            case "RIGHT":
                currentX += speed;
                break;
            default:
                break;
        }
        super.boundedRect.moveTo(new Point(currentX, currentY));
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisibility() {
        return visible;
    }

    // This function reset the ghost position upon collision with Pacman
    public void reset() {
        currentX = super.initialPos.x;
        currentY = super.initialPos.y;
        super.boundedRect.moveTo(new Point(currentX, currentY));
    }
}
