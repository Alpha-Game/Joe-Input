package joe.input;

public class ControllerAction implements IControllerAction {
	private Controller fController;
	private long fUseCount;
	private double fTotalValue;
	
	public ControllerAction(Controller controller) {
		fController = controller;
		reset();
	}
	
	Controller getController() {
		return fController;
	}
	
	@Override
	public void reset() {
		fUseCount = fController.getUseCount();
		fTotalValue = fController.getCumulativeValue();
	}
	
	@Override
	public long getUseCount() {
		return fController.getUseCount() - fUseCount;
	}
	
	@Override
	public double getCumulativeValue() {
		return fController.getCumulativeValue() - fTotalValue;
	}
	
	@Override
	public IOComponent getLastUsedComponenet() {
		return fController.getLastUsedComponenet();
	}
	
	@Override
	public float getLastValue() {
		return fController.getLastValue();
	}
	
	@Override
	public long getTimeSinceLastUse() {
		return fController.getTimeSinceLastUse();
	}
}
