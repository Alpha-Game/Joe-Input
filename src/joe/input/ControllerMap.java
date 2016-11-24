package joe.input;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joe.classes.base.ILoopable;
import joe.classes.base.Loopable;
import joe.classes.constants.GlobalConstants;

public class ControllerMap implements ILoopable {
	private class ComponentValuePair {
		public IOComponent fComponent;
		public float fValue;
		
		public ComponentValuePair(IOComponent componenet, float value) {
			fComponent = componenet;
			fValue = value;
		}
	}
	
	
	private Map<String, Set<IOComponent>> fComponentIDToComponentMap;
	private Controller fGlobalController;
	private Map<String, Controller> fControllerMap;
	private List<ComponentValuePair> fEventsToExecute;
	private Loopable fLoopable;
	
	protected ControllerMap() {
		fComponentIDToComponentMap = new HashMap<String, Set<IOComponent>>();
		fControllerMap = new HashMap<String, Controller>();
		fEventsToExecute = new LinkedList<ComponentValuePair>();
		fGlobalController = new Controller("");
	}
	
	protected void registerEvent(IOComponent component) {
		synchronized (fEventsToExecute) {
			fEventsToExecute.add(new ComponentValuePair(component, component.getCurrentState()));
		}
	}
	
	private String createComponentDeviceKey(String deviceID, String componentID) {
		return createDeviceKey(deviceID) + " " + createComponentKey(componentID);
	}
	
	private String createComponentKey(String componentID) {
		return "Component <" + componentID + ">";
	}
	
	private String createDeviceKey(String deviceID) {
		return "Device <" + deviceID + ">";
	}
	
	private void sendEvent(IOComponent component, float value) {
		// Send event to Controller for Global
		sendEvent(fGlobalController, component, value);
		
		// Send event to Controller for Device
		Controller controller;
		synchronized (fControllerMap) { 
			controller = fControllerMap.get(createDeviceKey(component.getDevice().getID()));
		}
		sendEvent(controller, component, value);
		
		// Send event to Controller for Component ID
		synchronized (fControllerMap) { 
			controller = fControllerMap.get(createComponentKey(component.getID()));
		}
		sendEvent(controller, component, value);
		
		// Send event to Controller for Component
		synchronized (fControllerMap) { 
			controller = fControllerMap.get(createComponentDeviceKey(component.getDevice().getID(), component.getID()));
		}
		sendEvent(controller, component, value);
	}
	
	private void sendEvent(Controller controller, IOComponent component, float value) {
		if (controller != null) {
			controller.recieveEvent(component, value);
		}
	}
	
	protected boolean registerComponent(IOComponent component) {
		synchronized (fComponentIDToComponentMap) { 
			Set<IOComponent> components = fComponentIDToComponentMap.get(component.getID());
			if (components == null) {
				components = new HashSet<IOComponent>();
				fComponentIDToComponentMap.put(component.getID(), components);
			}
			return components.add(component);
		}
	}
	
	protected boolean unregisterComponent(IOComponent component) {
		synchronized (fComponentIDToComponentMap) { 
			Set<IOComponent> components = fComponentIDToComponentMap.get(component.getID());
			if (components != null) {
				return components.remove(component);
			}
			return false;
		}
	}
	
	public Controller getControllerForComponentOnDevice(String deviceID, String componentID) {
		return getController(createComponentDeviceKey(deviceID, componentID));
	}
	
	public Controller getControllerForComponentOnAllDevices(String componentID) {
		return getController(createComponentKey(componentID));
	}
	
	public Controller getControllerForAllComponentsOnDevice(String deviceID) {
		return getController(createDeviceKey(deviceID));
	}
	
	private Controller getController(String id) {
		synchronized (fControllerMap) { 
			Controller controller = fControllerMap.get(id);
			if (controller == null) {
				controller = new Controller(id);
				fControllerMap.put(id, controller);
			}
			return controller;
		}
	}
	
	public Controller getControllerForAllComponentsOnAllDevices() {
		return fGlobalController;
	}

	@Override
	public void initialize() {}

	@Override
	public long run() {
		try {
			ComponentValuePair pair;
			synchronized (fEventsToExecute) {
				pair = fEventsToExecute.remove(0);
			}
			if (pair != null) {
				sendEvent(pair.fComponent, pair.fValue);
			}
			return 0;
		} catch (IndexOutOfBoundsException ex) {
			return GlobalConstants.MILLISECONDS;
		}				
	}

	@Override
	public void destroy() {}
	
	public void start() {
		if (fLoopable == null) {
			fLoopable = new Loopable(this);
		}
		fLoopable.start();
	}
	
	public void stop() {
		if (fLoopable != null) {
			fLoopable.stop();
		}
	}
	
	public boolean isRunning() {
		return fLoopable != null && fLoopable.isRunning();
	}
	
	@Override
	public String name() {
		return "Device Reader";
	}
}
