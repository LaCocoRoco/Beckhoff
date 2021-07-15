package twincat.scope;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import twincat.Utilities;

public final class Scope {
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private String scopeName = "Scope";

    private long recordTime = 0;
	
    /*********************************/
    /***** global final variable *****/
    /*********************************/
	
	private final CopyOnWriteArrayList<Chart> chartList = new CopyOnWriteArrayList<Chart>();

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }
	
	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public CopyOnWriteArrayList<Chart> getChartList() {
		return chartList;
	}

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public String toString() {
        return scopeName;
    }
	
    /*********************************/
    /********* public method *********/
    /*********************************/

	public void setRecordTime(String recordTime) {
		this.recordTime = Utilities.stringTimeToMilliseconds(recordTime);
	}

    public void addChart(Chart chart) {
		chartList.add(chart);
	}

	public void removeChart(Chart remove) {
		Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
        	Chart chart = chartIterator.next();
        	
			if (chart.equals(remove)) {
				chart.close();
				chartIterator.remove();
			}	
        }
	}

	public void start() {
		Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
        	Chart chart = chartIterator.next();
        	chart.setRecordTime(recordTime);
        	chart.start();
        }	
	}
	
	public void stop() {
		Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
        	Chart chart = chartIterator.next();
        	chart.stop();
        }	
	}
	
	public void close() {
		Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
        	Chart chart = chartIterator.next();
        	chart.close();
        }
	}
}
