import bagel.Font;
import bagel.util.Point;

public class Score extends HUD<String> {
    private int value = 0;
    private final int FONT_SIZE;
    private static final int WINNING_SCORE_L0 = 1210;
    private static final int WINNING_SCORE_L1 = 800;

    public Score(Point point, String font, int size) {
        super(point, font);
        FONT_SIZE = size;
    }

    public void draw() {
        new Font(super.image, FONT_SIZE).drawString("SCORE " + value,
                super.position.x, super.position.y);
    }

    public void scoreAdd(int amount) {
        value += amount;
    }

    public boolean winLevel0() {
        return value >= WINNING_SCORE_L0;
    }

    public boolean winLevel1() {
        return value >= WINNING_SCORE_L1;
    }

    public void reset() {
        value = 0;
    }

}
