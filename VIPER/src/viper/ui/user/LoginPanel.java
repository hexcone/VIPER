package viper.ui.user;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import nl.captcha.gimpy.RippleGimpyRenderer;
import nl.captcha.gimpy.StretchGimpyRenderer;
import nl.captcha.text.producer.FiveLetterFirstNameTextProducer;
import nl.captcha.text.producer.NumbersAnswerProducer;

import org.jdesktop.swingx.prompt.PromptSupport;

import viper.entity.Logger;
import viper.entity.TrackedPanel;
import viper.entity.User;
import viper.ui.main.HomePanel;
import viper.ui.main.MenuPanel;
import viper.ui.main.StoredPreferences;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public class LoginPanel extends TrackedPanel implements StoredPreferences {

	private static JFrame frame = null;
	private User user = new User();
	private JLabel jLabelBackground;
	private JLabel jLabelLogo;
	private JTextField jTextFieldUsername;
	private JPasswordField jPasswordField;
	private JButton jButtonLogin;
	private JLabel jLabelRegister;
	private JLabel jLabelForgetPassword;
	private ArrayList<Date> usernameArray = new ArrayList<Date>();
	private ArrayList<Date> passwordArray = new ArrayList<Date>();
	private int usernameCount = 0;
	private int passwordCount = 0;
	private double typeSpeed = 0;
	private double interval = 0;
	private JTextField jTextFieldCaptchaAnswerField = null;
	private Captcha captcha;
	private JButton jButtonRefresh = null;
	private JLabel jLabelCaptchaImage = null;
	
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

	@Override
	public void initialize() {
		super.initialize();
		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));
		
		captcha = new Captcha.Builder(250, 70)
        .addText(new NumbersAnswerProducer(3))
        .addText(new FiveLetterFirstNameTextProducer())
		.addBackground(new FlatColorBackgroundProducer())
		.gimp(new FishEyeGimpyRenderer())
		.build();
		
		BufferedImage bi = captcha.getImage();
		ImageIcon icon = new ImageIcon(bi);
		
		jLabelCaptchaImage = new JLabel(icon);
		jLabelCaptchaImage.setBounds(450, 370, 255, 70);
		
		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));

		jLabelLogo = new JLabel();
		jLabelLogo.setBounds(190, 220, 250, 194);
		jLabelLogo.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/logo-250.png")));

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setBounds(450, 250, 350, 40);
		jTextFieldUsername.setBackground(Color.white);
		PromptSupport.setPrompt("Username", jTextFieldUsername);
		jTextFieldUsername.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				System.out.println("Pressed: " + keyEvent);
				usernameArray.add(new Date());
				usernameCount++;
			}

			public void keyReleased(KeyEvent keyEvent) {
				System.out.println("Released: " + keyEvent);
			}

			public void keyTyped(KeyEvent keyEvent) {
				System.out.println("Typed: " + keyEvent);

			}
		});

		jPasswordField = new JPasswordField();
		jPasswordField.setBounds(450, 310, 350, 40);
		PromptSupport.setPrompt("Password", jPasswordField);
		
		jTextFieldUsername.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				System.out.println("Pressed: " + keyEvent);
				passwordArray.add(new Date());
				passwordCount++;
			}

			public void keyReleased(KeyEvent keyEvent) {
				System.out.println("Released: " + keyEvent);
			}

			public void keyTyped(KeyEvent keyEvent) {
				System.out.println("Typed: " + keyEvent);

			}
		});


		jButtonLogin = new JButton("Login");
		jButtonLogin.setBounds(700, 510, 100, 30);
		jButtonLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				PREF.put("SSL", "false");
				if (jTextFieldUsername.getText().equals("")
						|| jPasswordField.getPassword().length < 1) {
					JOptionPane.showMessageDialog(frame,
							"Please fill in all fields");
				} else {
					if (true){
					//if (captcha.getAnswer().equals(jTextFieldCaptchaAnswerField.getText())){
						user = User.authenticateUser(jTextFieldUsername.getText(),
								User.hashPassword(jPasswordField.getPassword()));
						/*user = User.authenticateUser("Admin", "1a1dc91c907325c69271ddf0c944bc72");*/
						
						if (user == null) {
							JOptionPane.showMessageDialog(frame,
									"Username or password is incorrect!");
							Logger.logInvalidUsernames(jTextFieldUsername.getText());
						} else {
							PREF.put(USERID, user.getUserId());
							PREF.put(USERNAME, user.getUserName());
							PREF.put(SSL, String.valueOf(user.isUserSSLSetting()));
							
							interval = usernameArray.get(usernameArray.size()-1).getTime() - usernameArray.get(0).getTime();
							typeSpeed = interval / usernameCount;
							Logger.logAction(0, "Typing Speed", true, "Username::" + typeSpeed);
							
							interval = passwordArray.get(passwordArray.size()-1).getTime() - passwordArray.get(0).getTime();
							typeSpeed = interval / passwordCount;
							Logger.logAction(0, "Typing Speed", true, "Password::" + typeSpeed);
							
							if (user.isUserFaceRegSetting() == true) {
								JPanel panel = new FaceRecPanel(frame);
								frame.getContentPane().removeAll();
								frame.getContentPane().add(panel);
								frame.getContentPane().validate();
								frame.getContentPane().repaint();
							}
							else {
								try {
									Logger.logUserLogin(PREF.get(USERID, null), true,
											"Reg::"+getIp());
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
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
					else
					{
						JOptionPane.showMessageDialog(frame, "Invalid Code! Please try again! ", 
								"Fail!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		jLabelRegister = new JLabel();
		jLabelRegister.setText("Register");
		jLabelRegister.setFont(new Font("Trebuchet MS", Font.ITALIC, 14));
		jLabelRegister.setForeground(Color.gray);
		jLabelRegister.setBounds(450, 510, 60, 30);
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
		jLabelForgetPassword.setBounds(520, 510, 130, 30);
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
		this.add(jPasswordField);
		this.add(jTextFieldUsername);
		this.add(jLabelLogo);
		this.add(jLabelCaptchaImage, null);
		this.add(getJButtonRefresh(), null);
		this.add(getJTextFieldCaptchaAnswerField(), null);
		this.add(jLabelBackground);
	}
	
	private JTextField getJTextFieldCaptchaAnswerField() {
		if (jTextFieldCaptchaAnswerField == null) {
			jTextFieldCaptchaAnswerField = new JTextField();
			//jTextFieldUsername.setBounds(450, 250, 350, 40);
			//jPasswordField.setBounds(450, 310, 350, 40);
			//jLabelCaptchaImage.setBounds(450, 370, 255, 70);
			//jButtonRefresh.setBounds(710, 370, 90, 30);
			jTextFieldCaptchaAnswerField.setBounds(450, 460, 350, 40);
			PromptSupport.setPrompt("Enter CAPTCHA challenge", jTextFieldCaptchaAnswerField);
		}
		return jTextFieldCaptchaAnswerField;
	}
	
	private JButton getJButtonRefresh() {
		if (jButtonRefresh == null) {
			jButtonRefresh = new JButton();
			jButtonRefresh.setBounds(710, 370, 90, 30);
			jButtonRefresh.setText("Refresh");
			jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
					jTextFieldCaptchaAnswerField.setText(null);
					captcha = new Captcha.Builder(250, 70)
			        .addText(new NumbersAnswerProducer(3))
			        .addText(new FiveLetterFirstNameTextProducer())
					.addBackground(new GradiatedBackgroundProducer())
					.gimp(new RippleGimpyRenderer())
					.gimp(new DropShadowGimpyRenderer())
					.gimp(new StretchGimpyRenderer())
					.build();
					
					BufferedImage bi = captcha.getImage();
					ImageIcon icon = new ImageIcon(bi);
					jLabelCaptchaImage.setIcon(icon);
				}
			});
		}
		return jButtonRefresh;
	}
}
