package viper.ui.main;

import java.awt.Font;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import viper.ui.user.LoginPanel;

public class MainFrame extends JFrame implements StoredPreferences {

	private static JPanel contentPane = null;

	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					UIManager.getLookAndFeelDefaults()
			        .put("defaultFont", new Font("Trebuchet MS", Font.PLAIN, 16));
					break;
				}
			}
		} catch (Exception e) {

		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getContentPane());
		this.setBounds(10, 10, 1000, 700);
		this.setResizable(false);
		this.setTitle("VIPER");
		this.setVisible(false);
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setLayout(null);
		}
		return contentPane;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		JPanel panel = new LoginPanel(frame);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

}
