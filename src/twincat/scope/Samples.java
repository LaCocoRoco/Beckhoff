package twincat.scope;

import java.util.Arrays;

public class Samples {
	/*********************************/
	/*** global constant variable ****/
	/*********************************/
	
	public static final int CAPACITY = 100000;
	
	/*********************************/
	/***** global final variable *****/
	/*********************************/

	private final double[] value = new double[CAPACITY];
	
	private final long[] time = new long[CAPACITY];

	/*********************************/
	/******** local variable *********/
	/*********************************/

	private int index = 0;
	
	/*********************************/
	/******** setter & getter ********/
	/*********************************/

	public void add(double value, long time) {
		this.value[index] = value;
		this.time[index] = time;
		index = index < CAPACITY - 1 ? index + 1: 0;
	}

	public void clear() {
		Arrays.fill(value, 0);
		Arrays.fill(time, 0);
	}
	
	public double getValue(int index) {
		return index < 0 || index >= CAPACITY ? 0 : value[index];
	}
	
	public long getTimeStamp(int index) {
		return index < 0 || index >= CAPACITY ? 0 : time[index];
	}

	public int getCurrentIndex() {
		return index - 1 < 0 ? 0 : index - 1;
	}
		
	public long getCurrentTimeStamp() {
		return getTimeStamp(index - 1);
	}
	
	public double getCurrentValue() {
		return getValue(index - 1);
	}
}
