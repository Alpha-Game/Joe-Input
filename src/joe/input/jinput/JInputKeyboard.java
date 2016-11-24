package joe.input.jinput;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import joe.classes.base.ILoopable;
import joe.classes.base.Loopable;
import joe.classes.constants.GlobalConstants;
import joe.input.IOComponent;
import joe.input.IODevice;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.RawKeyboard.Key;

public class JInputKeyboard extends IODevice implements ILoopable {
	private Controller fController;
	private Map<String, IOComponent> fKeys;
	private Loopable fLoopable;

	public JInputKeyboard(String id, Controller controller) {
		super(id, controller.getName());
		fController = controller;
	}

	@Override
	public Collection<IOComponent> getComponents() {
		return fKeys.values();
	}

	@Override
	public IOComponent getComponent(String id) {
		return fKeys.get(id);
	}

	@Override
	public void initialize() {
		fKeys = new HashMap<String, IOComponent>();
		
		fController.poll();
		Component[] keys = fController.getComponents();
		
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] instanceof Key) {
				JInputKey key = new JInputKey(this, (Key)keys[i]);
				fKeys.put(key.getID(), key);
			}
		}
	}

	@Override
	public long run() {
		fController.poll();
		for (IOComponent key : fKeys.values()) {
			((JInputKey)key).performEventIfRequired();
		}
		return GlobalConstants.MILLISECONDS;
	}

	@Override
	public void destroy() {
		fKeys = null;
	}

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
		return "Keyboard <" + fController.getDeviceIdentifier() + ">";
	}
}
