package joe.input;

import java.util.Collection;

public abstract class IODevice {
	private final String fID;
	private final String fName;
	
	private Boolean fDeviceListLocker;
	private IODeviceList fDeviceList;

	public IODevice(String id, String name) {
		fID = id;
		fName = name;
		fDeviceListLocker = false;
	}
	
	public String getID() {
		return fID;
	}
	
	public String getName() {
		return fName;
	}
	
	protected void setDeviceList(IODeviceList deviceList) {
		synchronized (fDeviceListLocker) {
			if (fDeviceList != null) {
				fDeviceList.removeDevice(getID());
			}
			fDeviceList = deviceList;
		}
	}
	
	public IODeviceList getDeviceList() {
		synchronized (fDeviceListLocker) { 
			return fDeviceList.getDevice(getID()) != null ? fDeviceList : null;
		}
	}
	
	@Override
	public int hashCode() {
		return fID.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IODevice) {
			return ((IODevice) obj).getID().equals(getID());
		}
		return false;
	}
	
	public abstract Collection<IOComponent> getComponents();
	
	public abstract IOComponent getComponent(String id);
}
