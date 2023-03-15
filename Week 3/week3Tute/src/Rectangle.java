public class Rectangle {
    private Point leftCoor;
    private Point topCoor;
    private double width;
    private double height;

    // Adding default method
    public void Rectangle() {
        leftCoor.setXCoord(0);
        leftCoor.setYCoord(0);

        topCoor.setXCoord(0);
        topCoor.setYCoord(1);

        width = 2;
        height = 1;
    }

    // Additional methoc
    public void Rectangle(Point leftCoor, Point topCoor, double width, double height) {
        this.leftCoor.setXCoord(leftCoor.getXCoord());
        this.leftCoor.setYCoord(leftCoor.getYCoord());

        this.topCoor.setXCoord(topCoor.getXCoord());
        this.topCoor.setYCoord(topCoor.getYCoord());

        this.width = width;
        this.height = height;
    }

    public double rectArea() {
        return width * height;
    }
}
