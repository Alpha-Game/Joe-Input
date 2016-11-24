package joe.input;

import joe.classes.identifier.IMappable;

public class Controller implements IMappable {
	private final String fIdentifier;
	
	private float fLastValue;
	private IOComponent fLastComponent;
	private long fLastUseTime;
	private long fUseCount;
	private double fTotalValue;
	
	protected Controller(String identifier) {
		fIdentifier = identifier;
		fLastComponent = null;
		fLastValue = 0;
		fTotalValue = 0;
		fUseCount = 0;
	}
	
	protected void recieveEvent(IOComponent component, float value) {
		synchronized (this) {
			fLastComponent = component;
			fLastValue = value;
			fTotalValue += value;
			fUseCount++;
			fLastUseTime = System.nanoTime();
		}
	}
	
	public IOComponent getLastUsedComponenet() {
		synchronized (this) {
			return fLastComponent;
		}
	}
	
	public long getUseCount() {
		synchronized (this) {
			return fUseCount;
		}
	}
	
	public float getLastValue() {
		synchronized (this) {
			return fLastValue;
		}
	}
	
	public double getCumulativeValue() {
		synchronized (this) {
			return fTotalValue;
		}
	}
	
	public long getTimeSinceLastUse() {
		synchronized (this) {
			return System.nanoTime() - fLastUseTime;
		}
	}

	@Override
	public String getIdentifier() {
		return fIdentifier;
	}
	
	@Override
	public int hashCode() {
		return fIdentifier.hashCode();
	}
	
	@Override
	public String toString() {
		return fIdentifier;
	}
}
