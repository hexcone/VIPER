package viper.ui.main;

import java.util.prefs.Preferences;

public interface StoredPreferences {
	public static final String GENERAL_NODE = "viper/ui/main/mainframe";
	public static final Preferences PREF = Preferences.userRoot().node(GENERAL_NODE);
	public static final String USERID = "USERID";
	public static final String USERNAME = "USERNAME";
}
