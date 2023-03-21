public class Show {
    private String name;
    private int minute;

    @Override
    public String toString() {
        return "Show{" +
                "name='" + name + '\'' +
                ", minute=" + minute +
                ", airTime=" + airTime +
                '}';
    }

    public Show(String name, int minute, double airTime) {
        this.name = name;
        this.minute = minute;
        this.airTime = airTime;
    }

    private double airTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public double getAirTime() {
        return airTime;
    }

    public void setAirTime(double airTime) {
        this.airTime = airTime;
    }
}
