package joe.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IODeviceList {
	private Map<String, IODevice> fDevices;
	private ControllerMap fControllerMap;
	
	public IODeviceList() {
		fDevices = new HashMap<String, IODevice>();
		fControllerMap = new ControllerMap();
		fControllerMap.start();
	}
	
	public Collection<IODevice> getDevices() {
		synchronized(fDevices) {
			return fDevices.values();
		}
	}
	
	public IODevice getDevice(String id) {
		synchronized(fDevices) {
			return fDevices.get(id);
		}
	}
	
	public boolean addDevice(IODevice device) {
		synchronized(fDevices) {
			device.setDeviceList(this);
			return fDevices.put(device.getID(), device) == null;
		}
	}
	
	public boolean removeDevice(String id) {
		synchronized(fDevices) {
			return fDevices.remove(id) != null;
		}
	}
	
	public ControllerMap getControllerMap() {
		return fControllerMap;
	}
}
