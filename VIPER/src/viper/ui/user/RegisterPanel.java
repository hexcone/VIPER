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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.prompt.PromptSupport;

import viper.entity.TrackedPanel;
import viper.entity.User;
import viper.ui.main.StoredPreferences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPanel extends TrackedPanel implements StoredPreferences {

	private static JFrame frame = null;
	private User user;
	private JLabel jLabelBackground;
	private JLabel jLabelRegister;
	private JLabel jLabelLogin;
	private JTextField jTextFieldUsername;
	private JLabel jLabelUsernameValidator;
	private JTextField jTextFieldEmail;
	private JLabel jLabelEmailValidator;
	private JPasswordField jPasswordField;
	private JPasswordField jPasswordFieldRepeat;
	private JLabel jLabelPasswordValidator;
	private JButton jButtonRegister;

	/**
	 * Create the panel.
	 */
	public RegisterPanel() {
		super();
		initialize();
	}

	public RegisterPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	@Override
	public void initialize() {
		super.initialize();
		
		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));

		jLabelRegister = new JLabel();
		jLabelRegister.setBounds(300, 200, 120, 45);
		jLabelRegister.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/user/register.png")));

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

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if (User.verifyUsername(jTextFieldUsername.getText())) {
							jLabelUsernameValidator.setVisible(false);
						} else {
							jLabelUsernameValidator.setText("Username taken!");
							jLabelUsernameValidator.setVisible(true);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldUsername.setBounds(300, 250, 400, 40);
		jTextFieldUsername.setBackground(Color.white);
		PromptSupport.setPrompt("Username", jTextFieldUsername);

		jLabelUsernameValidator = new JLabel();
		jLabelUsernameValidator.setBounds(710, 250, 200, 40);
		jLabelUsernameValidator.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/validator.png")));
		jLabelUsernameValidator.setForeground(Color.gray);
		jLabelUsernameValidator.setVisible(false);

		jTextFieldEmail = new JTextField();
		jTextFieldEmail.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						Pattern pattern = Pattern
								.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
										+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
						Matcher matcher = pattern.matcher(jTextFieldEmail
								.getText());
						if (matcher.matches()) {
							jLabelEmailValidator.setVisible(false);
						} else {
							jLabelEmailValidator.setText("Invalid!");
							jLabelEmailValidator.setVisible(true);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		/*
		 * jTextFieldEmail.addFocusListener(new FocusAdapter() {
		 * 
		 * @Override public void focusLost(FocusEvent arg0) { Pattern pattern =
		 * Pattern .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
		 * "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); Matcher matcher
		 * = pattern.matcher(jTextFieldEmail.getText()); if (matcher.matches())
		 * { jLabelEmailValidator.setVisible(false); } else {
		 * jLabelEmailValidator.setVisible(true); } } });
		 */
		jTextFieldEmail.setBounds(300, 300, 400, 40);
		jTextFieldEmail.setBackground(Color.white);
		PromptSupport.setPrompt("Email", jTextFieldEmail);

		jLabelEmailValidator = new JLabel();
		jLabelEmailValidator.setBounds(710, 300, 200, 40);
		jLabelEmailValidator.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/validator.png")));
		jLabelEmailValidator.setForeground(Color.gray);
		jLabelEmailValidator.setVisible(false);

		jPasswordField = new JPasswordField();
		jPasswordField.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						String password = new String(jPasswordField
								.getPassword());
						String passwordRepeat = new String(jPasswordFieldRepeat
								.getPassword());
						if (password.equals(passwordRepeat)) {
							jLabelPasswordValidator.setVisible(false);
						} else {
							jLabelPasswordValidator
									.setText("Password different!");
							jLabelPasswordValidator.setVisible(true);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		/*
		 * jPasswordField.addFocusListener(new FocusAdapter() {
		 * 
		 * @Override public void focusLost(FocusEvent arg0) { String password =
		 * new String(jPasswordField.getPassword()); String passwordRepeat = new
		 * String(jPasswordFieldRepeat.getPassword()); if
		 * (password.equals(passwordRepeat)) {
		 * jLabelPasswordValidator.setVisible(false); } else {
		 * jLabelPasswordValidator.setVisible(true); } } });
		 */
		jPasswordField.setBounds(300, 350, 400, 40);
		PromptSupport.setPrompt("Password", jPasswordField);

		jPasswordFieldRepeat = new JPasswordField();
		jPasswordFieldRepeat.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						String password = new String(jPasswordField
								.getPassword());
						String passwordRepeat = new String(jPasswordFieldRepeat
								.getPassword());
						if (password.equals(passwordRepeat)) {
							jLabelPasswordValidator.setVisible(false);
						} else {
							jLabelPasswordValidator
									.setText("Password different!");
							jLabelPasswordValidator.setVisible(true);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		/*
		 * jPasswordFieldRepeat.addFocusListener(new FocusAdapter() {
		 * 
		 * @Override public void focusLost(FocusEvent arg0) { String password =
		 * new String(jPasswordField.getPassword()); String passwordRepeat = new
		 * String(jPasswordFieldRepeat.getPassword()); if
		 * (password.equals(passwordRepeat)) {
		 * jLabelPasswordValidator.setVisible(false); } else {
		 * jLabelPasswordValidator.setVisible(true); } } });
		 */
		jPasswordFieldRepeat.setBounds(300, 400, 400, 40);
		PromptSupport.setPrompt("Repeat Password", jPasswordFieldRepeat);

		jLabelPasswordValidator = new JLabel();
		jLabelPasswordValidator.setBounds(710, 400, 200, 40);
		jLabelPasswordValidator.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/validator.png")));
		jLabelPasswordValidator.setForeground(Color.gray);
		jLabelPasswordValidator.setVisible(false);

		jButtonRegister = new JButton("Register");
		jButtonRegister.setBounds(600, 451, 100, 30);
		jButtonRegister.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (jLabelUsernameValidator.isVisible()
						|| jLabelEmailValidator.isVisible()
						|| jLabelPasswordValidator.isVisible()) {

				} else {
					if (jTextFieldUsername.getText().equals("")
							|| jTextFieldEmail.getText().equals("")
							|| jPasswordField.getPassword().length == 0
							|| jPasswordFieldRepeat.getPassword().length == 0) {
						if (jTextFieldUsername.getText().equals("")) {
							jLabelUsernameValidator.setText("Empty field!");
							jLabelUsernameValidator.setVisible(true);
						}
						if (jTextFieldEmail.getText().equals("")) {
							jLabelEmailValidator.setText("Empty field!");
							jLabelEmailValidator.setVisible(true);
						}
						if (jPasswordField.getPassword().length == 0) {
							jLabelPasswordValidator.setText("Empty field!");
							jLabelPasswordValidator.setVisible(true);
						}
						if (jPasswordFieldRepeat.getPassword().length == 0) {
							jLabelPasswordValidator.setText("Empty field!");
							jLabelPasswordValidator.setVisible(true);
						} else {

							try {
								createUser();
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(frame,
										"Account creation failed.");
							}
							JOptionPane.showMessageDialog(frame,
									"Account created sucessfully.");
							JPanel panel = new LoginPanel(frame);
							frame.getContentPane().removeAll();
							frame.getContentPane().add(panel);
							frame.getContentPane().validate();
							frame.getContentPane().repaint();
						}
					}
				}
			}
		});

		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jButtonRegister);
		this.add(jLabelPasswordValidator);
		this.add(jPasswordFieldRepeat);
		this.add(jPasswordField);
		this.add(jLabelEmailValidator);
		this.add(jTextFieldEmail);
		this.add(jLabelUsernameValidator);
		this.add(jTextFieldUsername);
		this.add(jLabelLogin);
		this.add(jLabelRegister);
		this.add(jLabelBackground);

	}

	public void createUser() {
		user = new User(jTextFieldUsername.getText(),
				jTextFieldEmail.getText(), User.hashPassword(jPasswordField
						.getPassword()));
		user.createUser();
	}
}