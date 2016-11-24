package joe.input.jinput;

import joe.input.IODeviceList;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;
import net.java.games.input.RawInputEnvironmentPlugin;

public class JInputDeviceReader implements ControllerListener  {
	private IODeviceList fDeviceList;
	private ControllerEnvironment fEnvironment;
		
	public JInputDeviceReader(IODeviceList deviceList) {
		fDeviceList = deviceList;
		fEnvironment = new RawInputEnvironmentPlugin();
	}
	
	public void start() throws Exception {
		stop();
		fEnvironment.start();
		for (Controller controller : fEnvironment.getControllers()) {
			addController(controller);
		}
		fEnvironment.addControllerListener(this);
	}
	
	public void reset() throws Exception {
		start();
	}
	
	public void stop() throws Exception {
		fEnvironment.removeControllerListener(this);
		fEnvironment.stop();
	}

	@Override
	public void controllerRemoved(ControllerEvent ev) {
		// Do not do anything
	}

	@Override
	public void controllerAdded(ControllerEvent ev) {
		addController(ev.getController());
	}
	
	protected void addController(Controller controller) {
		System.out.println("Controller Added: " + controller.getName());
		if (controller.getType() == Controller.Type.KEYBOARD) {
			addKeyboardController(controller);
		}
	}
	
	protected void addKeyboardController(Controller controller) {
		JInputKeyboard keyboard = new JInputKeyboard(controller.getDeviceIdentifier(), controller);
		keyboard.start();
		fDeviceList.addDevice(keyboard);
	}
}
