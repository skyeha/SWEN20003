public class Channel {
    public String name;
    public Show[] broadcast = new Show[4];

    public int getNumBroadcast() {
        return numBroadcast;
    }

    private int numBroadcast = 0;
    public void addShow(String name, int minute, double airTime) {
        if (numBroadcast == 0) {
            broadcast[numBroadcast] = new Show(name, minute, airTime);
            numBroadcast++;
            return;
        }

        if (numBroadcast < 4 ) {
            for (int i = 0; i < numBroadcast; i++) {
                if (airTime != broadcast[numBroadcast].getAirTime()) {
                    System.out.println("Show's airtime clashed with another, can't add!");
                    return;
                }
            }
            broadcast[numBroadcast] = new Show(name, minute, airTime);
            numBroadcast++;
            return;
        }

        System.out.println("Maximum number of shows reached, can't add more!");
    }

    public void cancelShow(String name) {
        for (int i = 0; i < numBroadcast; i++) {
            if (name.equals(broadcast[numBroadcast].getName())) {
                broadcast[numBroadcast] = null;
                numBroadcast--;
                return;
            }
        }
    }

    public Show getShow(double airTime) {
        for (int i = 0; i < numBroadcast; i++) {
            if (airTime == broadcast[numBroadcast].getAirTime()) {
                return broadcast[numBroadcast];
            }
        }
        return null;
    }
}
