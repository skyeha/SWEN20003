import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Colour;

public class Pacman extends Entities {

    // Initialise image of closing and open mouth
    private final Image PACMAN_CLOSE = new Image("res/pac.png");
    private final Image PACMAN_OPEN = new Image("res/pacOpen.png");

    // Current position of Pacman
    private double x;
    private double y;

    // Pacman actual rectangle
    private Rectangle pacmanRect;

    // Keep track of current frame to open and close mouth
    private int frame_count = 0;

    // Movement's logic
    private static final double STEP_SIZE = 3;
    private double direction = 0;
    private static final double DOWN = -(3.0 * Math.PI) / 2.0;
    private static final double UP = -(Math.PI) / 2.0;
    private static final double LEFT = Math.PI;
    private static final double RIGHT = 0;

    // Pacman constructor
    public Pacman(double x, double y) {
        super(x, y);
        this.x = x;
        this.y = y;
        pacmanRect = new Rectangle(x, y,
                PACMAN_CLOSE.getWidth(), PACMAN_CLOSE.getHeight());
    }

    // Get direction based on input key
    private double getDirection(Input input) {
        if (input.isDown(Keys.DOWN)) {
            return DOWN;
        } else if (input.isDown(Keys.UP)) {
            return UP;
        } else if (input.isDown(Keys.LEFT)) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }

    // Pacman's movement
    public void move(Input input, boolean wallHit) {
        Image currentPac = biting();

        if (wallHit) {
            direction = getDirection(input);
            currentPac.drawFromTopLeft(x,y, new DrawOptions().setRotation(direction));
            return;
        }

        if (input.isDown(Keys.DOWN)) {
            direction = getDirection(input);
                this.y += STEP_SIZE;
        }

        if (input.isDown(Keys.UP)) {
            direction = getDirection(input);
            this.y -= STEP_SIZE;
        }

        if (input.isDown(Keys.LEFT)) {
            direction = getDirection(input);
            this.x -= STEP_SIZE;
        }

        if (input.isDown(Keys.RIGHT)) {
            direction = getDirection(input);
            this.x += STEP_SIZE;
        }


        pacmanRect.moveTo(new Point(x, y));
        currentPac.drawFromTopLeft(x, y, new DrawOptions().setRotation(direction));
    }

    // Mouth movement
    private Image biting() {
        if (0 <= frame_count && frame_count <= 15) {
            frame_count++;
            return PACMAN_CLOSE;
        } else if (15 < frame_count && frame_count <= 30) {
            frame_count++;
            return PACMAN_OPEN;
        } else {
            frame_count = 0;
            return PACMAN_CLOSE;
        }
    }

    // Create a fitted bounding rectangle
    public Rectangle getBound() {
        return pacmanRect;
    }

    // Check for wall collision in the headed direction
    public boolean willHitWall(Wall wall, Input input) {
        double x = this.x, y = this.y;
        if (input.isDown(Keys.DOWN)) {
            y += STEP_SIZE;
        }

        if (input.isDown(Keys.UP)) {
            y -= STEP_SIZE;
        }

        if (input.isDown(Keys.LEFT)) {
            x -= STEP_SIZE;
        }

        if (input.isDown(Keys.RIGHT)) {
            x += STEP_SIZE;
        }

        Rectangle nextRect = new Rectangle(new Point(x,y),
                pacmanRect.right() - pacmanRect.left(),
                pacmanRect.bottom() - pacmanRect.top());
        Drawing.drawRectangle(nextRect.topLeft(),
                pacmanRect.right() - pacmanRect.left(),
                pacmanRect.top() - pacmanRect.bottom(),
                new Colour(1,1,1));
        if (nextRect.intersects(wall.getBound())) {
            return true;
        }
        return false;
    }

    // Move Pacman to starting position
    public void reset() {
        this.x = super.returnXPos();
        this.y = super.returnYPos();
        direction = 0;
        pacmanRect.moveTo(new Point(x, y));
    }
}


