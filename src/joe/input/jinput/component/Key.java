package joe.input.jinput.component;

import net.java.games.input.Component;

public class Key {
	private String fIdentifier;
	private String fName;
	
	public Key(int keyCode, Component.Identifier.Key key) {
		fIdentifier = getIdentifier(keyCode, key);
		fName = getName(keyCode, key);
	}
	
	public static String getIdentifier(int keyCode, Component.Identifier key) {
		if (key == null || key.equals(Component.Identifier.Key.UNKNOWN)) {
			return "Key: U" + keyCode;
		} else {
			return "Key: " + key.getName();
		}
	}
	
	public static String getName(int keyCode, Component.Identifier key) {
		if (key == null || key.equals(Component.Identifier.Key.UNKNOWN)) {
			return "Key " + keyCode;
		} else {
			return key.getName();
		}
	}
	
	public String getIdentifier() {
		return fIdentifier;
	}
	
	public String getName() {
		return fName;
	}
}
