public class Network {
    public String name;
    public Channel[] channelList = new Channel[2];

    private int numChannel = 0;

    public String getShows() {
        String result = "";
        for (int i = 0; i < numChannel; i++) {
            result += "===" + channelList[numChannel].name + "===\n";
            for (int j = 0; j < channelList[i].getNumBroadcast(); j++) {
                result += channelList[i].broadcast[j].toString() + "\n";
                result += "----\n";
            }
        }
        return result;
    }

    public void addChannel(String name, Channel aChannel) {
        if (numChannel < 3) {
            for (int i = 0; i < numChannel; i++) {
                if (channelList[numChannel] == null) {
                    channelList[numChannel] = aChannel;
                    numChannel++;
                    return;
                }
            }
        }
        System.out.println("Maximum channels reached, can't add more!");
    }

    public void removeChannel(String name) {
        for (int i = 0; i < numChannel; i++) {
            if (name.equals(channelList[numChannel].name)) {
                channelList[numChannel] = null;
                numChannel--;
                return;
            }
        }
    }

    public Channel lookupShow(Show aShow) {
        for (int i = 0; i < numChannel; i++) {
            if (channelList[numChannel].getShow(aShow.getAirTime()) != null) {
                return channelList[numChannel];
            }
        }
        return null;
    }
}
