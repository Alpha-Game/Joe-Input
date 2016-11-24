package joe.input;

import java.util.Objects;

public abstract class IOComponent {
	private final IODevice fDevice;
	private final String fID;
	private final String fName;

	public IOComponent(IODevice device, String id, String name) {
		fDevice = device;
		fID = id;
		fName = name;
	}
	
	public IODevice getDevice() {
		return fDevice;
	}
	
	public String getID() {
		return fID;
	}
	
	public String getName() {
		return fName;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fDevice.hashCode(), fID.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IOComponent) {
			return ((IOComponent) obj).getID().equals(getID()) && ((IOComponent) obj).getDevice().equals(getDevice());
		}
		return false;
	}
	
	public abstract float getCurrentState();
	
	protected void callEvent() {
		getDevice().getDeviceList().getControllerMap().registerEvent(this);
	}
}
