package viper.ui.main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

import viper.ui.data.DataProfilePanel;
import viper.ui.settings.SettingsPanel;
import viper.ui.user.UserProfilePanel;

public class MenuPanel extends JPanel implements StoredPreferences {

	private static JFrame frame = null;
	private static HeaderPanel header;
	private static ContentPanel content;
	private static boolean selected = false;
	private JLabel jLabelLogo;
	private JLabel jLabelMenuSteganograph;
	private JLabel jLabelMenuWatermark;
	private JLabel jLabelMenuFileManagement;
	private JLabel jLabelMenuDataProfile;
	private JLabel jLabelMenuUserProfile;
	private JLabel jLabelMenuSettings;
	/**
	 * This is the default constructor
	 */
	public MenuPanel() {
		super();
		initialize();
	}

	public MenuPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private class HeaderPanel extends JPanel implements MouseListener {
		BufferedImage hover;

		public HeaderPanel() {
			addMouseListener(this);
			this.setSize(15, 700);
			this.setLayout(null);
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			try {
				hover = ImageIO
						.read(new File("src/viper/image/main/hover.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			if (selected)
				g2.drawImage(hover, 0, 0, 15, 700, this);
			else
				g2.drawImage(hover, 0, 0, 15, 700, this);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			selected = !selected;

			if (content.isShowing()) {
				content.setVisible(false);
				content.setEnabled(false);
			} else {
				content.setVisible(true);
				content.setEnabled(true);
			}
			header.repaint();
			validate();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

	}

	private class ContentPanel extends JPanel {
		public ContentPanel() {
			initialize();
		}

		private Image getScaledImage(Image srcImg, int w, int h) {
			BufferedImage resizedImg = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = resizedImg.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(srcImg, 0, 0, w, h, null);
			g2.dispose();
			return resizedImg;
		}

		public void initialize() {
			jLabelLogo = new JLabel();
			jLabelLogo.setBounds(50, 10, 200, 155);
			jLabelLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			jLabelLogo.setIcon(new ImageIcon(getClass().getResource(
					"/viper/image/main/logo.png")));
			jLabelLogo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					JPanel panel = new HomePanel(frame);
					JPanel menu = new MenuPanel(frame);
					menu.setLocation(700,0);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(menu);
					frame.getContentPane().add(panel);
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
				}
			});
			
			jLabelMenuSteganograph = new JLabel();
			jLabelMenuSteganograph.setBounds(20, 175, 250, 45);
			jLabelMenuSteganograph.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			jLabelMenuSteganograph.setIcon(new ImageIcon(getClass()
					.getResource("/viper/image/main/menu_steganograph.png")));
			jLabelMenuSteganograph.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});
			
			jLabelMenuWatermark = new JLabel();
			jLabelMenuWatermark.setBounds(20, 220, 250, 45);
			jLabelMenuWatermark.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			jLabelMenuWatermark.setIcon(new ImageIcon(getClass().getResource(
					"/viper/image/main/menu_watermark.png")));
			jLabelMenuWatermark.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});

			jLabelMenuFileManagement = new JLabel();
			jLabelMenuFileManagement.setBounds(20, 265, 250, 45);
			jLabelMenuFileManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			jLabelMenuFileManagement.setIcon(new ImageIcon(getClass()
					.getResource("/viper/image/main/menu_filemanagement.png")));
			jLabelMenuFileManagement.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});

			jLabelMenuDataProfile = new JLabel();
			jLabelMenuDataProfile.setBounds(20, 310, 250, 45);
			jLabelMenuDataProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			jLabelMenuDataProfile.setIcon(new ImageIcon(getClass().getResource(
					"/viper/image/main/menu_dataprofile.png")));
			jLabelMenuDataProfile.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					JPanel panel = new DataProfilePanel(frame);
					JPanel menu = new MenuPanel(frame);
					menu.setLocation(700,0);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(menu);
					frame.getContentPane().add(panel);
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
				}
			});

			jLabelMenuUserProfile = new JLabel();
			jLabelMenuUserProfile.setBounds(20, 355, 250, 45);
			jLabelMenuUserProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			jLabelMenuUserProfile.setIcon(new ImageIcon(getClass().getResource(
					"/viper/image/main/menu_userprofile.png")));
			jLabelMenuUserProfile.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					JPanel panel = new UserProfilePanel(frame);
					JPanel menu = new MenuPanel(frame);
					menu.setLocation(700,0);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(menu);
					frame.getContentPane().add(panel);
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
				}
			});

			jLabelMenuSettings = new JLabel();
			jLabelMenuSettings.setBounds(20, 400, 250, 45);
			jLabelMenuSettings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			jLabelMenuSettings.setIcon(new ImageIcon(getClass().getResource(
					"/viper/image/main/menu_settings.png")));
			jLabelMenuSettings.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					JPanel panel = new SettingsPanel(frame);
					JPanel menu = new MenuPanel(frame);
					menu.setLocation(700,0);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(menu);
					frame.getContentPane().add(panel);
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
				}
			});

			this.setSize(285, 700);
			this.setLayout(null);
			this.setBackground(Color.black);

			this.add(jLabelMenuSettings);
			this.add(jLabelMenuUserProfile);
			this.add(jLabelMenuDataProfile);
			this.add(jLabelMenuFileManagement);
			this.add(jLabelMenuWatermark);
			this.add(jLabelMenuSteganograph);
			this.add(jLabelLogo);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		selected = false;
		content = new ContentPanel();
		header = new HeaderPanel();

		this.setSize(300, 700);
		this.setLayout(null);
		this.setOpaque(false);

		header.setLocation(280, 0);
		this.add(header);

		content.setVisible(false);
		content.setLocation(0, 0);
		this.add(content);

	}

}
