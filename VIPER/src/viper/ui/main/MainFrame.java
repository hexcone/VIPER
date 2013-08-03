package viper.ui.main;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import viper.entity.Logger;
import viper.entity.TrackedPanel;
import viper.ui.user.LoginPanel;

public class MainFrame extends JFrame implements StoredPreferences {

	private static TrackedPanel contentPane = null;

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
		
		PREF.put(PROGRAMDIR, "C:/ProgramData/VIPERMEDIA/");
		String dir = PREF.get(PROGRAMDIR, null);
		File theDir = new File(dir);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + dir);
			boolean result = theDir.mkdir();

			if (result) {
				System.out.println("DIR created");
			}
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getContentPane());
		this.setBounds(10, 10, 1000, 700);
		this.setResizable(false);
		this.setTitle("VIPER");
		this.setVisible(false);
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				Logger.logUserLogout();
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public TrackedPanel getContentPane() {
		if (contentPane == null) {
			contentPane = new TrackedPanel();
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
