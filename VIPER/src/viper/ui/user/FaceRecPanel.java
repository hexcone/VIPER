package viper.ui.user;

import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import viper.entity.Logger;
import viper.entity.TrackedPanel;
import viper.ui.main.MenuPanel;
import viper.ui.main.StoredPreferences;
import viper.ui.stegnograph.ImgStegoPanel;

public class FaceRecPanel extends TrackedPanel implements StoredPreferences {

	private static JFrame frame = null;
	private JLabel jLabelBackground;
	private JButton jButtonCapture;
	private JLabel jLabelCanvas;
	private IplImage grabbedImage;
	Thread th;
	boolean preview;
	private static String programDir = PREF.get(PROGRAMDIR, null);
	/**
	 * Create the panel.
	 */
	public FaceRecPanel() {
		super();
		initialize();
	}

	public FaceRecPanel(JFrame f) {
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

		jButtonCapture = new JButton("Capture");
		jButtonCapture.setBounds(400, 600, 200, 30);
		jButtonCapture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String newFilePath = programDir + "temp.jpg";
				String newUserFaceRecPath = programDir + "temp.jpg";
				OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
				try {
					int region;

					

					grabber.start();
					grabbedImage = grabber.grab();
					BufferedImage bfimg = grabbedImage.getBufferedImage();
					grabbedImage = null;
					if (bfimg.getWidth() < bfimg.getHeight()) {
						region = bfimg.getWidth();
					} else {
						region = bfimg.getHeight();
					}

					BufferedImage thumbnail = Thumbnails.of(bfimg)
							.size(200, 200)
							.sourceRegion(Positions.CENTER, region, region)
							.asBufferedImage();

					File outputfile = new File(newFilePath);
					ImageIO.write(thumbnail, "jpg", outputfile);

				} catch (Exception | IOException ex) {
					ex.printStackTrace();
				}
		        
		        preview = false;
		        th.interrupt();
		        
		        File file = new File(programDir + "temp.jpg"); 
		        File parent = new File(programDir + "facerec/");
		        System.out.println("getAbsolutePath: " + file.getAbsolutePath());
		        System.out.println("getParent: " + parent.getAbsolutePath());
		        
		        OpenCVFaceRecognizer fr = new OpenCVFaceRecognizer(parent.getAbsolutePath(), file.getAbsolutePath());
		        if (fr.isMatch()) {
					try {
						Logger.logUserLogin(PREF.get(USERID, null), true,
								"FaceRec::"+
						getIp());
					} catch (java.lang.Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(frame,
							"Match successful!");
					Logger.logUserLogin(PREF.get(USERID, null), true,
							"FaceRec");
		        	JPanel panel = new ImgStegoPanel(frame);
					JPanel menu = new MenuPanel(frame);
					menu.setLocation(700,0);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(menu);
					frame.getContentPane().add(panel);
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
		        }
		        else {
		        	try {
						Logger.logUserLogin(PREF.get(USERID, null), false,
								"FaceRec::"+getIp());
					} catch (java.lang.Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	JOptionPane.showMessageDialog(frame,
							"Match unsuccessful!");
		        	JPanel panel = new LoginPanel(frame);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(panel);
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
		        }
		        
			}
		});
		
		jLabelCanvas = new JLabel();
		jLabelCanvas.setBounds(180, 100, 800, 400);
		

		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jLabelCanvas);
		this.add(jButtonCapture);
		this.add(jLabelBackground);
		
		preview = true;
		Preview p = new Preview();
		th = new Thread(p);
        th.start();

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
