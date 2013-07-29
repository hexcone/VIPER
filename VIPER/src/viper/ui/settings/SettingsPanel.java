package viper.ui.settings;

import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;

import java.awt.Color;
import java.awt.Cursor;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import viper.entity.TrackedPanel;
import viper.entity.User;
import viper.ui.main.MenuPanel;
import viper.ui.main.StoredPreferences;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SettingsPanel extends TrackedPanel implements StoredPreferences {

	private static JFrame frame = null;
	private User user = null;
	private JLabel jLabelBackground;
	
	private JTextField jTextFieldUsername;
	private JTextField jTextFieldEmail;
	private JTextField jTextFieldRealName;
	private JTextField jTextFieldBio;
	private JTextField jTextFieldContactNo;
	private JTextField jTextFieldCompany;
	private JTextField jTextFieldCountry;
	private JTextField jTextFieldAddress;
	private JCheckBox jCheckBoxSSL;
	private JCheckBox jCheckBoxFaceRec;
	private JCheckBox jCheckBoxProfile;
	private JButton jButtonSave;
	
	private JLabel jLabelProfileImage;
	private JLabel jLabelFaceRecImage;

	private JPasswordField jPasswordFieldExisting;
	private JPasswordField jPasswordFieldNew;
	private JPasswordField jPasswordFieldRepeat;
	
	private String newUserImagePath;
	private String newUserFaceRecPath;
	private String oldUserFaceRecPath;
	
	JFrame jFrameCapture;
	JPanel jPanelCapture;
	Thread th;
	boolean preview;
	private JLabel jLabelCanvas;
	private IplImage grabbedImage;
	private JButton jButtonCapture;
	/**
	 * Create the panel.
	 */
	public SettingsPanel() {
		super();
		initialize();
	}

	public SettingsPanel(JFrame f) {
		super();
		frame = f;
		user = User.retrieveUser(PREF.get(USERID, null));
		initialize();
	}

	@Override
	public void initialize() {
		super.initialize();
		
		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setBounds(20, 20, 400, 40);
		jTextFieldUsername.setBackground(Color.white);
		jTextFieldUsername.setEnabled(false);
		PromptSupport.setPrompt("Username", jTextFieldUsername);
		
		jTextFieldEmail = new JTextField();
		jTextFieldEmail.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						/*if (User.verifyUsername(jTextFieldUsername.getText())) {
							jLabelUsernameValidator.setVisible(false);
						} else {
							jLabelUsernameValidator.setText("Username taken!");
							jLabelUsernameValidator.setVisible(true);
						}*/
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldEmail.setBounds(20, 70, 400, 40);
		jTextFieldEmail.setBackground(Color.white);
		PromptSupport.setPrompt("Email", jTextFieldEmail);
		
		jTextFieldRealName = new JTextField();
		jTextFieldRealName.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						/*if (User.verifyUsername(jTextFieldUsername.getText())) {
							jLabelUsernameValidator.setVisible(false);
						} else {
							jLabelUsernameValidator.setText("Username taken!");
							jLabelUsernameValidator.setVisible(true);
						}*/
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldRealName.setBounds(20, 120, 400, 40);
		jTextFieldRealName.setBackground(Color.white);
		PromptSupport.setPrompt("Real Name", jTextFieldRealName);
		
		jTextFieldBio = new JTextField();
		jTextFieldBio.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						/*if (User.verifyUsername(jTextFieldUsername.getText())) {
							jLabelUsernameValidator.setVisible(false);
						} else {
							jLabelUsernameValidator.setText("Username taken!");
							jLabelUsernameValidator.setVisible(true);
						}*/
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldBio.setBounds(20, 170, 400, 40);
		jTextFieldBio.setBackground(Color.white);
		PromptSupport.setPrompt("Bio", jTextFieldBio);
		
		jTextFieldContactNo = new JTextField();
		jTextFieldContactNo.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						/*if (User.verifyUsername(jTextFieldUsername.getText())) {
							jLabelUsernameValidator.setVisible(false);
						} else {
							jLabelUsernameValidator.setText("Username taken!");
							jLabelUsernameValidator.setVisible(true);
						}*/
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldContactNo.setBounds(20, 220, 400, 40);
		jTextFieldContactNo.setBackground(Color.white);
		PromptSupport.setPrompt("Contact Number", jTextFieldContactNo);
		
		jTextFieldCompany = new JTextField();
		jTextFieldCompany.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						/*if (User.verifyUsername(jTextFieldUsername.getText())) {
							jLabelUsernameValidator.setVisible(false);
						} else {
							jLabelUsernameValidator.setText("Username taken!");
							jLabelUsernameValidator.setVisible(true);
						}*/
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldCompany.setBounds(20, 270, 400, 40);
		jTextFieldCompany.setBackground(Color.white);
		PromptSupport.setPrompt("Company", jTextFieldCompany);
		
		jTextFieldCountry = new JTextField();
		jTextFieldCountry.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						/*if (User.verifyUsername(jTextFieldUsername.getText())) {
							jLabelUsernameValidator.setVisible(false);
						} else {
							jLabelUsernameValidator.setText("Username taken!");
							jLabelUsernameValidator.setVisible(true);
						}*/
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldCountry.setBounds(20, 320, 400, 40);
		jTextFieldCountry.setBackground(Color.white);
		PromptSupport.setPrompt("Country", jTextFieldCountry);
		
		jTextFieldAddress = new JTextField();
		jTextFieldAddress.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						/*if (User.verifyUsername(jTextFieldUsername.getText())) {
							jLabelUsernameValidator.setVisible(false);
						} else {
							jLabelUsernameValidator.setText("Username taken!");
							jLabelUsernameValidator.setVisible(true);
						}*/
					}

					@Override
					public void removeUpdate(DocumentEvent e) {

					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
		jTextFieldAddress.setBounds(20, 370, 400, 40);
		jTextFieldAddress.setBackground(Color.white);
		PromptSupport.setPrompt("Address", jTextFieldAddress);
		
		jCheckBoxSSL = new JCheckBox();
		jCheckBoxSSL.setBounds(20, 420, 400, 40);
		jCheckBoxSSL.setText("Enable SSL");
		jCheckBoxSSL.setForeground(Color.white);
		
		jCheckBoxFaceRec = new JCheckBox();
		jCheckBoxFaceRec.setBounds(20, 470, 400, 40);
		jCheckBoxFaceRec.setText("Enable face recognition");
		jCheckBoxFaceRec.setForeground(Color.white);
		
		jCheckBoxProfile = new JCheckBox();
		jCheckBoxProfile.setBounds(20, 520, 400, 40);
		jCheckBoxProfile.setText("Make profile private");
		jCheckBoxProfile.setForeground(Color.white);

		jTextFieldUsername.setText(user.getUserName());
		jTextFieldEmail.setText(user.getUserEmail());
		jTextFieldRealName.setText(user.getUserRealName());
		jTextFieldBio.setText(user.getUserBio());
		jTextFieldContactNo.setText(Integer.toString(user.getUserContactNo()));
		jTextFieldCompany.setText(user.getUserCompany());
		jTextFieldCountry.setText(user.getUserCountry());
		jTextFieldAddress.setText(user.getUserAddress());
		jCheckBoxSSL.setSelected(user.isUserSSLSetting());
		jCheckBoxFaceRec.setSelected(user.isUserFaceRegSetting());
		jCheckBoxProfile.setSelected(user.isUserProfileSetting());
		
		
		
		
		jLabelProfileImage = new JLabel();
		jLabelProfileImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser fc = new JFileChooser();
		        int returnVal = fc.showOpenDialog(SettingsPanel.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            String ext = file.getName().substring(file.getName().lastIndexOf("."));
		            
		            String newFilePath = 
		            		"src/viper/image/user/profile/" + 
		            		PREF.get(USERNAME, null) 
		            		+ ext;
		            
		            newUserImagePath = 
		            		"/viper/image/user/profile/" + 
		            		PREF.get(USERNAME, null) 
		            		+ ext;
		           
					try {
						int region;
						BufferedImage originalImage = ImageIO.read(file);
						if (originalImage.getWidth() < originalImage.getHeight()) {
							region = originalImage.getWidth();
						}
						else {
							region = originalImage.getHeight();
						}
						
			            BufferedImage thumbnail = Thumbnails.of(originalImage)
			                    .size(200, 200)
			                    .sourceRegion(Positions.CENTER, region, region)
			                    .asBufferedImage();
			            

			            jLabelProfileImage.setIcon(new ImageIcon(thumbnail));

			            File outputfile = new File(newFilePath);
			            ImageIO.write(thumbnail, ext.substring(1, ext.length()), outputfile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        } else {
		        	System.out.println("Open command cancelled by user");
		        }
			}
		});
		jLabelProfileImage.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabelProfileImage.setBounds(450, 20, 200, 230);
		jLabelProfileImage.setText("Profile Image");
		jLabelProfileImage.setHorizontalTextPosition(JLabel.CENTER);
		jLabelProfileImage.setVerticalTextPosition(JLabel.BOTTOM);
		jLabelProfileImage.setForeground(Color.white);
		jLabelProfileImage.setIcon(new ImageIcon(getClass().getResource(user.getUserImagePath())));
		// System.out.println("user.getUserImagePath() :" + user.getUserImagePath());
		
		jLabelFaceRecImage = new JLabel();
		jLabelFaceRecImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				jFrameCapture = new JFrame();
				jPanelCapture = new JPanel();
				
				jLabelCanvas = new JLabel();
				jLabelCanvas.setBounds(0, 0, 640, 480);
				
				jButtonCapture = new JButton("Capture");
				jButtonCapture.setBounds(210, 500, 200, 30);
				jButtonCapture.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
						try {
								int region;
								
								String newFilePath = 
					            		"src/viper/image/user/facerec/" + 
					            		PREF.get(USERNAME, null) 
					            		+ ".jpg";
								System.out.println("newFilePath: " + newFilePath);
								
								newUserFaceRecPath = "/viper/image/user/facerec/"
										+ PREF.get(USERNAME, null) + ".jpg";
								

					            grabber.start();
								grabbedImage = grabber.grab();
					            BufferedImage bfimg = grabbedImage.getBufferedImage();
					            grabbedImage = null;
					            if (bfimg.getWidth() < bfimg.getHeight()) {
									region = bfimg.getWidth();
								}
								else {
									region = bfimg.getHeight();
								}
								
							BufferedImage thumbnail = Thumbnails
									.of(bfimg)
									.size(200, 200)
									.sourceRegion(Positions.CENTER, region,
											region).asBufferedImage();

							File outputfile = new File(newFilePath);
							ImageIO.write(thumbnail, "jpg", outputfile);

							jLabelFaceRecImage.setIcon(new ImageIcon(thumbnail));
						} catch (Exception | IOException ex) {
							ex.printStackTrace();
						}
				        
				        preview = false;
				        th.interrupt();
				        
				        jFrameCapture.dispose();
					}
				});

				jPanelCapture.setSize(650, 590);
				jPanelCapture.setLayout(null);
				jPanelCapture.setBackground(Color.black);

				jPanelCapture.add(jButtonCapture);
				jPanelCapture.add(jLabelCanvas);

				jFrameCapture.setSize(650, 590);
				jFrameCapture.getContentPane().add(jPanelCapture);
				jFrameCapture.setVisible(true);
				jFrameCapture.addWindowListener( new WindowAdapter() {
					public void windowClosing( WindowEvent e){
						preview = false;
				        th.interrupt();
					}
				});
				preview = true;
				Preview p = new Preview();
				th = new Thread(p);
		        th.start();
			}
		});
		jLabelFaceRecImage.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabelFaceRecImage.setBounds(680, 20, 200, 230);
		jLabelFaceRecImage.setText("Face Recognition Image");
		jLabelFaceRecImage.setHorizontalTextPosition(JLabel.CENTER);
		jLabelFaceRecImage.setVerticalTextPosition(JLabel.BOTTOM);
		jLabelFaceRecImage.setForeground(Color.white);
		if (user.getUserFaceRecPath() == null) {
			jLabelFaceRecImage.setIcon(new ImageIcon(getClass().getResource("/viper/image/user/null.jpg")));
		} 
		else if (user.getUserFaceRecPath().equals("")) {
			jLabelFaceRecImage.setIcon(new ImageIcon(getClass().getResource("/viper/image/user/null.jpg")));
		}
		else {
			oldUserFaceRecPath = user.getUserFaceRecPath();
			jLabelFaceRecImage.setIcon(new ImageIcon(getClass().getResource(user.getUserFaceRecPath())));
		}
		
		jButtonSave = new JButton("Save Settings");
		jButtonSave.setBounds(800, 600, 150, 30);
		jButtonSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (jTextFieldEmail.getText().equals("")) {
					JOptionPane.showMessageDialog(frame,
							"Please enter your email!");
				} else if (jCheckBoxFaceRec.isSelected()
						&& (newUserFaceRecPath == null) && user
								.getUserFaceRecPath() == null) {
					JOptionPane.showMessageDialog(frame,
							"To enable face recognition, pleas take a photo!");
				} else {
					user = new User();
					user.setUserId(PREF.get(USERID, null));
					user.setUserEmail(jTextFieldEmail.getText());
					user.setUserRealName(jTextFieldRealName.getText());
					user.setUserBio(jTextFieldBio.getText());
					user.setUserContactNo(Integer.parseInt(jTextFieldContactNo
							.getText()));
					user.setUserCompany(jTextFieldCompany.getText());
					user.setUserCountry(jTextFieldCountry.getText());
					user.setUserAddress(jTextFieldAddress.getText());
					user.setUserSSLSetting(jCheckBoxSSL.isSelected());
					user.setUserFaceRegSetting(jCheckBoxFaceRec.isSelected());
					user.setUserProfileSetting(jCheckBoxProfile.isSelected());
					if (newUserImagePath != null) {
						user.setUserImagePath(newUserImagePath);
					}
					else {
						user.setUserImagePath("/viper/image/user/profile/image.jpg");
					}
					if (oldUserFaceRecPath != null) {
						user.setUserFaceRecPath(oldUserFaceRecPath);
					}
					else if (newUserFaceRecPath != null) {
						user.setUserFaceRecPath(newUserFaceRecPath);
					}
					user.updateUser();
					
					JOptionPane.showMessageDialog(frame,
							"Updated successfully!");
					
					JPanel panel = new SettingsPanel(frame);
					JPanel menu = new MenuPanel(frame);
					menu.setLocation(700, 0);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(menu);
					frame.getContentPane().add(panel);
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
				}
			}
		});

		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jButtonSave);
		this.add(jLabelFaceRecImage);
		this.add(jLabelProfileImage);
		this.add(jCheckBoxProfile);
		this.add(jCheckBoxFaceRec);
		this.add(jCheckBoxSSL);
		this.add(jTextFieldAddress);
		this.add(jTextFieldCountry);
		this.add(jTextFieldCompany);
		this.add(jTextFieldContactNo);
		this.add(jTextFieldBio);
		this.add(jTextFieldRealName);
		this.add(jTextFieldEmail);
		this.add(jTextFieldUsername);
		this.add(jLabelBackground);

	}
	
	private class Preview implements Runnable {
		
		@Override
		public void run() {
			FrameGrabber grabber = new OpenCVFrameGrabber(0);
			CvMemStorage storage = CvMemStorage.create();
	        try {
				grabber.start();
		        while (preview)
		        {
					grabbedImage = grabber.grab();
		            BufferedImage bfimg = grabbedImage.getBufferedImage();
		            jLabelCanvas.setIcon(new ImageIcon(bfimg));
		            cvClearMemStorage(storage);
		        }
		        grabber.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}