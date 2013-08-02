package viper.ui.stegnograph;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import viper.entity.TrackedPanel;
import viper.ui.main.StoredPreferences;

import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Point;

public class DetectSteganographPanel extends TrackedPanel implements StoredPreferences {
	
	private static final long serialVersionUID = 1L;
	private static JFrame frame = null;
	private JLabel jLabelTitle = null;
	
	public DetectSteganographPanel() {
		super();
		initialize();

	}
	
	
	public DetectSteganographPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	@Override
	public void initialize() {
		jLabelTitle = new JLabel();
		jLabelTitle.setFont(new Font("Britannic Bold", Font.BOLD, 20));
		jLabelTitle.setLocation(new Point(343, 16));
		jLabelTitle.setSize(new Dimension(300, 30));
		jLabelTitle.setText("Steganograph Detection");
		this.setLayout(null);
        this.setSize(new Dimension(1000, 700));
        this.add(jLabelTitle, null);
		
	}
	

}
