public class Circle {
    private Point centre;
    private double radius;

    // default constructor
    public void Circle() {
        centre.setXCoord(0);
        centre.setYCoord(0);
        radius = 1;
    }

    // Question 1B
    public Circle(double radius) {
        centre.setXCoord(0);
        centre.setYCoord(0);
        this.radius = radius;
    }

    // Question 1C
    public void Circle(double radius, Point centre) {
        this.radius = radius;
        this.centre.setXCoord(centre.getXCoord());
        this.centre.setYCoord(centre.getYCoord());
    }

    //Question 1D
    public String toString() {
        return "This is a circle with radius " + radius + " with centre coordinates ("
                + centre.getXCoord() + ',' + centre.getYCoord() + ").";
    }

    public boolean equals(Circle circle) {
        if (this.radius == circle.radius &&
                this.centre.getXCoord() == circle.centre.getXCoord() &&
                this.centre.getYCoord() == circle.centre.getYCoord()) {
            return true;
        } else {
            return false;
        }
    }
}
