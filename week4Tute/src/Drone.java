public class Drone {
    public double getHomeX() {
        return homeX;
    }

    public void setHomeX(double homeX) {
        this.homeX = homeX;
    }

    public double getHomeY() {
        return homeY;
    }

    public void setHomeY(double homeY) {
        this.homeY = homeY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    private double homeX;
    private double homeY;
    private double x;
    private double y;
    private double altitude = 0.0;

    public Drone(double homeX, double homeY) {
        this.homeX = homeX;
        this.homeY = homeY;
        this.x = homeX;
        this.y = homeY;
    }

    public void flyUp(double amount) {
        altitude += amount;
    }

    public void flyDown(double amount) {
        altitude = Math.max(altitude - amount ,0); // this is to make sure the drone "does not go underground"
    }

    double distanceToHome() {
        return distance(x,y, homeX, homeY);
    }

    static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) +(y1 -y2) * (y1 - y2));
    }
}
