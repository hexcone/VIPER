package viper.ui.user;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.prompt.PromptSupport;

import viper.ui.main.StoredPreferences;

public class ForgetPasswordPanel extends JPanel implements StoredPreferences {

	private static JFrame frame = null;
	private JLabel jLabelBackground;
	private JLabel jLabelLogin;
	private JLabel jLabelForgetPassword;
	private JTextField jTextFieldUsername;
	private JTextField jTextFieldEmail;
	private JButton jButtonSendEmail;
	/**
	 * Create the panel.
	 */
	public ForgetPasswordPanel() {
		super();
		initialize();
	}

	public ForgetPasswordPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private void initialize() {

		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));
		
		jLabelLogin = new JLabel();
		jLabelLogin.setBounds(890, 10, 100, 30);
		jLabelLogin.setForeground(Color.gray);
		jLabelLogin.setText("Back to login");
		jLabelLogin.setFont(new Font("Trebuchet MS", Font.ITALIC, 14));
		jLabelLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabelLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				jLabelLogin.setForeground(Color.cyan);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jLabelLogin.setForeground(Color.gray);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new LoginPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});
		
		jLabelForgetPassword = new JLabel();
		jLabelForgetPassword.setBounds(300, 200, 265, 45);
		jLabelForgetPassword.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/user/forgetpassword.png")));

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setBounds(300, 250, 400, 40);
		jTextFieldUsername.setBackground(Color.white);
		PromptSupport.setPrompt("Username", jTextFieldUsername);

		jTextFieldEmail = new JTextField();
		jTextFieldEmail.setBounds(300, 300, 400, 40);
		jTextFieldEmail.setBackground(Color.white);
		PromptSupport.setPrompt("Email", jTextFieldEmail);
		
		jButtonSendEmail = new JButton("Send Email");
		jButtonSendEmail.setBounds(570, 350, 130, 30);
		jButtonSendEmail.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonSendEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new LoginPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});
		
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);
		
		this.add(jButtonSendEmail);
		this.add(jTextFieldEmail);
		this.add(jTextFieldUsername);
		this.add(jLabelForgetPassword);
		this.add(jLabelLogin);
		this.add(jLabelBackground);

	}
}