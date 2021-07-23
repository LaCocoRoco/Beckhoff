package twincat.scope;

import java.awt.Color;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import twincat.constant.DefaultColorTable;

public class Axis implements Observer {
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private boolean refresh = false;

    private String axisName = "Axis";

    private Color axisColor = DefaultColorTable.DEEPBLUE.color;

    private boolean axisVisible = true;

    private boolean axisNameVisible = true;

    private int lineWidth = 1;

    private double valueMin = 0;

    private double valueMax = 0;

    private boolean autoscale = true;

    private boolean scaleSymetrical = false;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final CopyOnWriteArrayList<Channel> channelList = new CopyOnWriteArrayList<Channel>();

    /*********************************/
    /******** local variable *********/
    /*********************************/

    private double symetricalValue = 0;

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public String getAxisName() {
        return axisName;
    }

    public void setAxisName(String axisName) {
        this.axisName = axisName;
        this.refresh = true;
    }

    public Color getAxisColor() {
        return axisColor;
    }

    public void setAxisColor(Color axisColor) {
        this.axisColor = axisColor;
        this.refresh = true;
    }

    public boolean isAxisVisible() {
        return axisVisible;
    }

    public void setAxisVisible(boolean axisVisible) {
        this.axisVisible = axisVisible;
        this.refresh = true;
    }
    
    public boolean isAxisNameVisible() {
        return axisNameVisible;
    }

    public void setAxisNameVisible(boolean axisNameVisible) {
        this.axisNameVisible = axisNameVisible;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        this.refresh = true;
    }

    public double getValueMin() {
        return valueMin;
    }

    public void setValueMin(double valueMin) {
        this.valueMin = valueMin;
        this.refresh = true;
    }

    public double getValueMax() {
        return valueMax;
    }

    public void setValueMax(double valueMax) {
        this.valueMax = valueMax;
        this.refresh = true;
    }

    public boolean isAutoscale() {
        return autoscale;
    }

    public void setAutoscale(boolean autoscale) {
        this.autoscale = autoscale;
        this.refresh = true;
        this.symetricalValue = 0;
        this.valueMin = autoscale ? 0 : valueMin;
        this.valueMax = autoscale ? 0 : valueMax;
    }

    public boolean isScaleSymetrical() {
        return scaleSymetrical;
    }

    public void setScaleSymetrical(boolean scaleSymetrical) {
        this.scaleSymetrical = scaleSymetrical;
        this.refresh = true;
        this.symetricalValue = 0;
        this.valueMin = autoscale ? 0 : valueMin;;
        this.valueMax = autoscale ? 0 : valueMax;;
    }

    public CopyOnWriteArrayList<Channel> getChannelList() {
        return channelList;
    }

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public String toString() {
        return axisName;
    }

    @Override
    public void update(Observable observable, Object object) {
        scaleValues((Channel) observable);
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void start() {
        Iterator<Channel> channelIterator = channelList.iterator();
        while (channelIterator.hasNext()) {
            Channel channel = channelIterator.next();
            channel.start();
        }
    }

    public void stop() {
        Iterator<Channel> channelIterator = channelList.iterator();
        while (channelIterator.hasNext()) {
            Channel channel = channelIterator.next();
            channel.stop();
        }
    }

    public void close() {
        Iterator<Channel> channelIterator = channelList.iterator();
        while (channelIterator.hasNext()) {
            Channel channel = channelIterator.next();
            channel.close();
        }
    }

    public void addChannel(Channel channel) {
        channelList.add(channel);
        channel.addObserver(this);
    }

    public void removeChannel(Channel channelRemove) {
        for (Channel channel : channelList) {
            if (channel.equals(channelRemove)) {
                channel.stop();
                channelList.remove(channel);
            }
        }
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void scaleValues(Channel channel) {
        if (autoscale) {
            double value = channel.getSamples().getCurrentValue();

            if (scaleSymetrical) {
                if (Math.abs(Math.ceil(value)) > Math.abs(symetricalValue)) {          
                    symetricalValue = Math.abs(Math.ceil(value));
                    valueMax = (long) + symetricalValue;
                    valueMin = (long) - symetricalValue;
                    refresh = true;
                }
            } else {
                if (Math.floor(value) < valueMin) {
                    valueMin = (long) value;
                    refresh = true;
                }

                if (Math.ceil(value) > valueMax) {
                    valueMax = (long) value;
                    refresh = true;
                }  
            }
        }
    }
}
