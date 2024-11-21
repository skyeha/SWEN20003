public abstract class Entities {

    // Initialise final position of each entity
    private final double x;
    private final double y;

    public Entities(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double returnXPos() {
        return x;
    }

    public double returnYPos() {
        return y;
    }
}
