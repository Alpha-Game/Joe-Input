package joe.input;

import java.util.LinkedList;
import java.util.List;


// TODO this whole class
public class MultiControllerAction implements IControllerAction {
	private List<Controller> fControllers;
	private long fUseCount;
	private double fTotalValue;
	
	protected MultiControllerAction() {
		fControllers = new LinkedList<Controller>();
	}
	
	public MultiControllerAction(Object... objects) {
		this();
		for (Object object : objects) {
			addController(object);
		}
		reset();
	}
	
	public MultiControllerAction(Iterable<?> objects) {
		this();
		for (Object object : objects) {
			addController(object);
		}
		reset();
	}
	
	private void addController(Object controller) {
		if (controller instanceof Controller) {
			fControllers.add((Controller)controller);
		} else if (controller instanceof ControllerAction) {
			fControllers.add(((ControllerAction) controller).getController());
		} else if (controller instanceof MultiControllerAction) {
			//fControllers.add(((MultiControllerAction) controller).getController());
		}
	}
	
	@Override
	public void reset() {
		fUseCount = getCurrentUseCount();
		fTotalValue = getCurrentCumulativeValue();
	}
	
	protected long getCurrentUseCount() {
		long useCount = 0;
		for (Controller controller : fControllers) {
			useCount += controller.getUseCount();
		}
		return useCount;
	}
	
	protected double getCurrentCumulativeValue() {
		long value = 0;
		for (Controller controller : fControllers) {
			value += controller.getCumulativeValue();
		}
		return value;
	}
	
	@Override
	public long getUseCount() {
		return getCurrentUseCount() - fUseCount;
	}
	
	@Override
	public double getCumulativeValue() {
		return getCurrentCumulativeValue() - fTotalValue;
	}
	
	@Override
	public IOComponent getLastUsedComponenet() {
		// TODO
		return null;
	}
	
	@Override
	public float getLastValue() {
		// TODO
		return 0f;
	}
	
	@Override
	public long getTimeSinceLastUse() {
		// TODO
		return 0l;
	}
}