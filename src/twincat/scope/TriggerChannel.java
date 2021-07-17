package twincat.scope;

import java.util.Observable;
import java.util.Observer;

public class TriggerChannel extends Observable implements Observer {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static enum Combine {
        AND, OR
    }

    public static enum Release {
        RISING_EDGE, FALLING_EDGE
    }

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Combine combine = Combine.AND;

    private Release release = Release.RISING_EDGE;

    private double threshold = 0;

    private long releaseTimeStamp = 0;

    /*********************************/
    /***** global final variable *****/
    /*********************************/

    private final Channel channel;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public TriggerChannel() {
        this.channel = new Channel();
    }

    public TriggerChannel(Channel channel) {
        this.channel = channel;
        channel.addObserver(this);
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public Channel getChannel() {
        return channel;
    }

    public Combine getCombine() {
        return combine;
    }

    public void setCombine(Combine combine) {
        this.combine = combine;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public long getReleaseTimeStamp() {
        return releaseTimeStamp;
    }

    public void setReleaseTimeStamp(long setReleaseTimeStamp) {
        this.releaseTimeStamp = setReleaseTimeStamp;
    }

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public String toString() {
        return channel.getChannelName();
    }

    @Override
    public void update(Observable observable, Object object) {
        Channel channel = ((Channel) observable);

        if (isTriggerReleased(channel)) {
            setChanged();
            notifyObservers();
        }
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private boolean isTriggerReleased(Channel channel) {
        int index = channel.getSamples().getCurrentIndex();
        long time = channel.getSamples().getTimeStamp(index);
        double value = channel.getSamples().getValue(index);

        boolean released = getTriggerReleased(value);

        if (released && releaseTimeStamp == 0) {
            releaseTimeStamp = time;
            return true;
        }

        if (!released && releaseTimeStamp != 0) {
            releaseTimeStamp = 0;
            return false;
        }

        return false;
    }

    private boolean getTriggerReleased(double value) {
        switch (release) {
            case RISING_EDGE:
                return value >= threshold ? true : false;

            case FALLING_EDGE:
                return value <= threshold ? true : false;

            default:
                return false;
        }
    }
}
