package joe.input;

public interface IControllerAction {
	void reset();
	
	long getUseCount();
	
	double getCumulativeValue();
	
	IOComponent getLastUsedComponenet();
	
	float getLastValue();
	
	long getTimeSinceLastUse();
}
