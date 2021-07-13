package twincat.scope;

import java.awt.Color;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public class Axis implements Observer {
    /*********************************/
    /**** local constant variable ****/
    /*********************************/
	
	private static final double AUTOSCALE_OFFSET = 0.1;
	
	private static final int AUTOSCALE_MODULATE = 10;
	
    /*********************************/
    /******** global variable ********/
    /*********************************/
	
	private boolean refresh = false;

	private String axisName = "Axis";

	private Color axisColor = new Color(41, 61, 74, 255);
    
    private boolean axisVisible = true;
    
	private int lineWidth = 1;   

	private double valueMin = 0;
    	
	private double valueMax = 0;
	
    private boolean autoscale = true;

    private int autoscaleOffset = 20;

    /*********************************/
    /****** local final variable *****/
    /*********************************/
    
	private final CopyOnWriteArrayList<Channel> channelList = new CopyOnWriteArrayList<Channel>();   

    /*********************************/
    /******** local variable *********/
    /*********************************/
	
	private double autoscaleValueMin = 0;
	
	private double autoscaleValueMax = 0;
	
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
	}

	public int getAutoscaleOffset() {
		return autoscaleOffset;
	}

	public void setAutoscaleOffset(int autoscaleOffset) {
		this.autoscaleOffset = autoscaleOffset;
		this.refresh = true;
	}

	public CopyOnWriteArrayList<Channel> getChannelList() {
		return channelList;
	}

	/*********************************/
    /******** override method ********/
    /*********************************/

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

			if (value < autoscaleValueMin) {
				autoscaleValueMin = value;	
			}
			
			if (value > autoscaleValueMax) {
				autoscaleValueMax = value;
			}
			
			double autoscaleRange = autoscaleValueMax - autoscaleValueMin;
			double autoscaleOffset = autoscaleRange * AUTOSCALE_OFFSET;
			double autoscaleMin = autoscaleValueMin - autoscaleOffset;
			double autoscaleMax = autoscaleValueMax + autoscaleOffset;
			double autosclaeMinMod = AUTOSCALE_MODULATE + autoscaleMin % AUTOSCALE_MODULATE;
			double autoscaleMaxMod = AUTOSCALE_MODULATE - autoscaleMax % AUTOSCALE_MODULATE;
			
			valueMin = autoscaleValueMin - autoscaleOffset - autosclaeMinMod;
			valueMax = autoscaleValueMax + autoscaleOffset + autoscaleMaxMod;
		}
	}
}
