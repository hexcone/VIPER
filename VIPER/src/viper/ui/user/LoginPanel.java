package viper.ui.user;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import org.jdesktop.swingx.prompt.PromptSupport;

import viper.entity.User;
import viper.ui.main.HomePanel;
import viper.ui.main.MenuPanel;
import viper.ui.main.StoredPreferences;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel implements StoredPreferences {

	private static JFrame frame = null;
	private User user = new User();
	private JLabel jLabelBackground;
	private JLabel jLabelLogo;
	private JTextField jTextFieldUsername;
	private JLabel jLabelUsernameValidator;
	private JPasswordField jPasswordField;
	private JLabel jLabelPasswordValidator;
	private JButton jButtonLogin;
	private JLabel jLabelRegister;
	private JLabel jLabelForgetPassword;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		super();
		initialize();
	}

	public LoginPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private void initialize() {

		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));

		jLabelLogo = new JLabel();
		jLabelLogo.setBounds(190, 220, 250, 194);
		jLabelLogo.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/logo-250.png")));

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if (jTextFieldUsername.getText().equals("")) {
							jLabelUsernameValidator.setText("Empty!");
							jLabelPasswordValidator.setVisible(true);
						} else {
							jLabelPasswordValidator.setVisible(false);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldUsername.setBounds(450, 250, 350, 40);
		jTextFieldUsername.setBackground(Color.white);
		PromptSupport.setPrompt("Username", jTextFieldUsername);

		jLabelUsernameValidator = new JLabel();
		jLabelUsernameValidator.setBounds(810, 250, 200, 40);
		jLabelUsernameValidator.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/validator.png")));
		jLabelUsernameValidator.setForeground(Color.gray);
		jLabelUsernameValidator.setVisible(false);

		jPasswordField = new JPasswordField();
		jPasswordField.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if (jPasswordField.getPassword().length == 0) {
							jLabelUsernameValidator.setText("Empty!");
							jLabelPasswordValidator.setVisible(true);
						} else {
							jLabelPasswordValidator.setVisible(false);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jPasswordField.setBounds(450, 310, 350, 40);
		PromptSupport.setPrompt("Password", jPasswordField);

		jLabelPasswordValidator = new JLabel();
		jLabelPasswordValidator.setBounds(810, 310, 200, 40);
		jLabelPasswordValidator.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/validator.png")));
		jLabelPasswordValidator.setForeground(Color.gray);
		jLabelPasswordValidator.setVisible(false);

		jButtonLogin = new JButton("Login");
		jButtonLogin.setBounds(700, 370, 100, 30);
		jButtonLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (jLabelUsernameValidator.isVisible()
						|| jLabelPasswordValidator.isVisible()) {

				} else {
					user = User.authenticateUser(jTextFieldUsername.getText(),
							User.hashPassword(jPasswordField.getPassword()));
					
					/*user = User.authenticateUser("Admin", "1a1dc91c907325c69271ddf0c944bc72");*/
					
					if (user == null) {
						JOptionPane.showMessageDialog(frame,
								"Username or password is incorrect!");
					} else {
						PREF.put(USERID, user.getUserId());
						PREF.put(USERNAME, user.getUserName());
						if (user.isUserFaceRegSetting() == true) {
							JPanel panel = new FaceRecPanel(frame);
							frame.getContentPane().removeAll();
							frame.getContentPane().add(panel);
							frame.getContentPane().validate();
							frame.getContentPane().repaint();
						}
						else {
							JPanel panel = new HomePanel(frame);
							JPanel menu = new MenuPanel(frame);
							menu.setLocation(700,0);
							frame.getContentPane().removeAll();
							frame.getContentPane().add(menu);
							frame.getContentPane().add(panel);
							frame.getContentPane().validate();
							frame.getContentPane().repaint();
						}
					}
				}
			}
		});

		jLabelRegister = new JLabel();
		jLabelRegister.setText("Register");
		jLabelRegister.setFont(new Font("Trebuchet MS", Font.ITALIC, 14));
		jLabelRegister.setForeground(Color.gray);
		jLabelRegister.setBounds(450, 370, 60, 30);
		jLabelRegister
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabelRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				jLabelRegister.setForeground(Color.cyan);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jLabelRegister.setForeground(Color.gray);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new RegisterPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});

		jLabelForgetPassword = new JLabel();
		jLabelForgetPassword.setText("Forget Password");
		jLabelForgetPassword.setFont(new Font("Trebuchet MS", Font.ITALIC, 14));
		jLabelForgetPassword.setForeground(Color.gray);
		jLabelForgetPassword.setBounds(520, 370, 130, 30);
		jLabelForgetPassword.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabelForgetPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				jLabelForgetPassword.setForeground(Color.cyan);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jLabelForgetPassword.setForeground(Color.gray);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new ForgetPasswordPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});

		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jLabelForgetPassword);
		this.add(jLabelRegister);
		this.add(jButtonLogin);
		this.add(jLabelPasswordValidator);
		this.add(jPasswordField);
		this.add(jLabelUsernameValidator);
		this.add(jTextFieldUsername);
		this.add(jLabelLogo);
		this.add(jLabelBackground);

	}
}
