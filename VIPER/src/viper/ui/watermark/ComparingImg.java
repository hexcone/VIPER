package viper.ui.watermark;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class ComparingImg extends JFrame {

	private static JPanel ContentPane = null;

	public ComparingImg() {
		super();
		initialize();
	}
	
	public ComparingImg(JPanel panel) {
		super();
		initialize();
		this.getContentPane().add(panel);
	}
	
	public static void main(String[] args) {
				ComparingImg thisClass = new ComparingImg();
				
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
		}
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		this.setSize(1000, 700);
		this.setContentPane(getJContentPane());
		this.setTitle("Comparing Images");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (ContentPane == null) {
			ContentPane = new JPanel();
			ContentPane.setLayout(new BorderLayout());
		}
		return ContentPane;
	}

}
