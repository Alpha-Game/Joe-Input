package joe.input.jinput;

import joe.input.IOComponent;
import joe.input.IODevice;
import joe.input.jinput.component.Key;
import net.java.games.input.RawKeyboard;

public class JInputKey extends IOComponent {
	private RawKeyboard.Key fKey;
	private float fLastState;
	
	public JInputKey(IODevice device, RawKeyboard.Key key) {
		super(device, Key.getIdentifier(key.getKeyCode(), key.getIdentifier()), Key.getName(key.getKeyCode(), key.getIdentifier()));
		fKey = key;
		fLastState = getCurrentState();
	}

	@Override
	public float getCurrentState() {
		synchronized (fKey) {
			return fKey.getPollData();
		}
	}
	
	protected void performEventIfRequired() {
		float currentState = getCurrentState();
		if (currentState != fLastState) {
			callEvent();
		}
		fLastState = currentState;
	}
}
