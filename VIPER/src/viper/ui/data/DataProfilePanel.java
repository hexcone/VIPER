package viper.ui.data;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import viper.ui.main.StoredPreferences;

public class DataProfilePanel extends JPanel implements StoredPreferences {

	private static JFrame frame = null;
	private JLabel jLabelBackground;
	private JLabel jLabelPlaceHolder;
	
	/**
	 * Create the panel.
	 */
	public DataProfilePanel() {
		super();
		initialize();
	}

	public DataProfilePanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private void initialize() {

		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));
		
		jLabelPlaceHolder = new JLabel();
		jLabelPlaceHolder.setText("Data Profile Panel");
		jLabelPlaceHolder.setForeground(Color.gray);
		jLabelPlaceHolder.setBounds(400, 300, 200, 30);
		
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jLabelPlaceHolder);
		this.add(jLabelBackground);

	}
}