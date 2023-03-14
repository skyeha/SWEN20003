public class Point {
    private double xCoord;
    private double yCoord;

    public Point(double xCoord, double yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    // Get (X,Y) coordinates
    public double getXCoord() {
        return xCoord;
    }
    public double getYCoord() {
        return yCoord;
    }

    // Set (X,Y) coordinates

    public void setXCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public void setYCoord(double yCoord) {
        this.yCoord = yCoord;
    }
}
