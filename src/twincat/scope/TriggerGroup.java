package twincat.scope;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public class TriggerGroup implements Observer {
	/*************************/
	/*** global attributes ***/
	/*************************/

	private boolean enabled = true;

	private int triggerOffset = 0;

	private final CopyOnWriteArrayList<Long> triggerTimeStampList = new CopyOnWriteArrayList<Long>();

	private final CopyOnWriteArrayList<TriggerChannel> triggerChannelList = new CopyOnWriteArrayList<TriggerChannel>();

	/*************************/
	/**** setter & getter ****/
	/*************************/
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getTriggerOffset() {
		return triggerOffset;
	}

	public void setTriggerOffset(int triggerOffset) {
		this.triggerOffset = triggerOffset;
	}

	public CopyOnWriteArrayList<Long> getTriggerTimeStampList() {
		return triggerTimeStampList;
	}

	public CopyOnWriteArrayList<TriggerChannel> getTriggerChannelList() {
		return triggerChannelList;
	}

	/*************************/
	/********* public ********/
	/*************************/

	@Override
	public void update(Observable observable, Object object) {
		if (getTriggerCombined()) {
			TriggerChannel triggerChannel = (TriggerChannel) observable;
			triggerTimeStampList.add(triggerChannel.getReleaseTimeStamp());
		}
	}

	public long getTriggerTimeStamp(long displayTime) {
		if (enabled) {
			if (!triggerTimeStampList.isEmpty()) {
				long displayTimeOffset = displayTime / 100 * (100 - triggerOffset);
				long lastTriggerTimeStamp = triggerTimeStampList.get(triggerTimeStampList.size() - 1);
				
				for (int i = triggerTimeStampList.size() - 1; i >= 0; i--) {
					long triggerTimeStamp = triggerTimeStampList.get(i);
		
					if (triggerTimeStamp < lastTriggerTimeStamp - displayTimeOffset) {
						return triggerTimeStamp + displayTimeOffset;
					}	
				}
				
			}
		}
		
		return 0;
	}
	
	public void addTrigger(TriggerChannel trigger) {
		trigger.addObserver(this);
		triggerChannelList.add(trigger);
	}

	public void removeTrigger(TriggerChannel remove) {
        for (TriggerChannel triggerChannel : triggerChannelList) {
			if (triggerChannel.equals(remove)) {
				triggerChannel.deleteObserver(this);
				triggerChannelList.remove(triggerChannel);
			}
        }
	}

	/*************************/
	/******** private ********/
	/*************************/

	private boolean getTriggerCombined() {
		if (!triggerChannelList.isEmpty()) {
			
			Iterator<TriggerChannel> triggerChannelIterator = triggerChannelList.iterator();
	        while (triggerChannelIterator.hasNext()) {
	        	TriggerChannel triggerChannel = triggerChannelIterator.next();	
	        
				if (triggerChannel.getCombine() == TriggerChannel.Combine.OR) {
					if (triggerChannel.getReleaseTimeStamp() != 0) {
						return true;
					}
				}

				if (triggerChannel.getCombine() == TriggerChannel.Combine.AND) {
					if (triggerChannel.getReleaseTimeStamp() == 0) {
						return false;
					}
				}
	        }
			return true;
		}
		return false;
	}
}
