package viper.ui.user;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import viper.ui.main.StoredPreferences;

public class UserProfilePanel extends JPanel implements StoredPreferences {

	private static JFrame frame = null;
	private JLabel jLabelBackground;
	private JLabel jLabelUserProfile;
	
	/**
	 * Create the panel.
	 */
	public UserProfilePanel() {
		super();
		initialize();
	}

	public UserProfilePanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private void initialize() {
		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));
		
		jLabelUserProfile = new JLabel();
		jLabelUserProfile.setText("User Profile");
		jLabelUserProfile.setForeground(Color.gray);
		jLabelUserProfile.setBounds(400, 300, 200, 30);
		
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jLabelUserProfile);
		this.add(jLabelBackground);

	}
}
