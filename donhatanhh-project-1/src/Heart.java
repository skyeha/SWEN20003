import bagel.*;
public class Heart {
    // Instantiate image and coordinates
    private static final Image HEART_IMAGE = new Image("res/heart.png");
    private double x;
    private double y;

    // Instantiate max HP and array of HP
    private static final int MAX_HEART = 3;
    private static Heart[] hearts = new Heart[MAX_HEART];

    // Instantiate visibility status
    private boolean visible = true;

    // Heart array constructor
    public Heart() {
        double startX = 900;
        double startY = 10;
        for (int i = 0; i < MAX_HEART; i++) {
            hearts[i] = new Heart(startX, startY);
            startX += 30;
        }
    }

    // Heart constructor
    private Heart(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Draw heart
    public void draw() {
        for (Heart h: hearts) {
            if (h.visible) {
                HEART_IMAGE.drawFromTopLeft(h.x, h.y);
            }
        }
    }

    // Modify visibility
    public void setVisible(int index, boolean visible) {
        hearts[index].visible = visible;
    }
}
