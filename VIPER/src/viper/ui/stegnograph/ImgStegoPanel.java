package viper.ui.stegnograph;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import viper.entity.TrackedPanel;
import viper.ui.main.StoredPreferences;

import java.awt.TextArea;

public class ImgStegoPanel extends TrackedPanel implements StoredPreferences {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private JLabel jLabelTitle;
	private JLabel jLabelStegoImg;
	private JButton jButtonBrowse;
	private JLabel jLabelCryptingTxt;
	private JFileChooser fc;
	private JLabel jLabelBackground;
	private JTextField jTextFieldCryptingTxt;
	private JButton jButtonSave;
	private JButton jButtonOK;
	private File fileChosen; 
    
	public ImgStegoPanel() {
		super();
		initialize();
	}

	public ImgStegoPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}


	@Override
	public void initialize() {
		super.initialize();
		jLabelCryptingTxt = new JLabel();
		jLabelCryptingTxt.setText("Text for embedding:");
		jLabelCryptingTxt.setSize(new Dimension(170, 27));
		jLabelCryptingTxt.setForeground(Color.gray);
		jLabelCryptingTxt.setLocation(new Point(630, 229));
		jLabelStegoImg = new JLabel();
		jLabelStegoImg.setText("StegoImg");
		jLabelStegoImg.setSize(new Dimension(400, 450));
		jLabelStegoImg.setForeground(Color.gray);
		jLabelStegoImg.setLocation(new Point(60, 76));
		jLabelTitle = new JLabel();
		jLabelTitle.setFont(new Font("Britannic Bold", Font.PLAIN, 20));
		jLabelTitle.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabelTitle.setSize(new Dimension(210, 25));
		jLabelTitle.setLocation(new Point(450, 16));
		jLabelTitle.setForeground(Color.gray);
		jLabelTitle.setText("Image Steganography");
		jLabelBackground = new JLabel();
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));
		jLabelBackground.setSize(new Dimension(1000, 700));
		jLabelBackground.setForeground(Color.black);
		jLabelBackground.setLocation(new Point(0, 0));
		jLabelBackground.setVisible(false);
		fc = new JFileChooser();
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);
		this.setVisible(true);
		this.add(jLabelTitle, null);
		this.add(jLabelStegoImg, null);
		this.add(getJButtonBrowse(), null);
		this.add(jLabelCryptingTxt, null);
		this.add(getJTextFieldCryptingTxt(), null);
		this.add(getJButtonOK(), null);
		this.add(getJButtonSave(), null);
		this.add(jLabelBackground);
	}

	/**
	 * This method initializes jButtonBrowse
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonBrowse() {
		if (jButtonBrowse == null) {
			jButtonBrowse = new JButton();
			jButtonBrowse.setText("Browse");
			jButtonBrowse.setLocation(new Point(475, 76));
			jButtonBrowse.setForeground(Color.black);
			jButtonBrowse.setSize(new Dimension(100, 29));
			jButtonBrowse
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							int returnVal = fc
									.showOpenDialog(ImgStegoPanel.this);

							if (returnVal == fc.APPROVE_OPTION) {
								File file = fc.getSelectedFile();

								if (isImage(file) != true) {
									jLabelStegoImg.setText(file.getName());
									
								}
							}
						}
					});
		}
		return jButtonBrowse;
	}

	
	private boolean isImage(File imageFile) {
		try {
			jLabelStegoImg.setIcon(new ImageIcon(imageFile.getAbsolutePath()));
			fileChosen = imageFile;
		}

		catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * This method initializes jTextFieldCryptingTxt
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldCryptingTxt() {
		if (jTextFieldCryptingTxt == null) {
			jTextFieldCryptingTxt = new JTextField();
			jTextFieldCryptingTxt.setSize(new Dimension(250, 260));
			jTextFieldCryptingTxt.setLocation(new Point(630, 263));
		}
		return jTextFieldCryptingTxt;
	}

	/**
	 * This method initializes jButtonSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton();
			jButtonSave.setText("Save");
			jButtonSave.setSize(new Dimension(100, 29));
			jButtonSave.setHorizontalTextPosition(SwingConstants.TRAILING);
			jButtonSave.setLocation(new Point(800, 620));
			jButtonSave.setForeground(Color.black);
			jButtonSave.setVisible(false);
			jButtonSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					JFileChooser fc = new JFileChooser();
					if (fc.showSaveDialog(ImgStegoPanel.this) == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						// save to file
					}
				}

			});

		}
		return jButtonSave;
	}

	/**
	 * This method initializes jButtonOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setText("OK");
			jButtonOK.setLocation(new Point(800, 563));
			jButtonOK.setSize(new Dimension(100, 29));
			jButtonOK.setForeground(Color.black);
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					BufferedImage img = null;
					
					try
					{
						img = ImageIO.read(fileChosen);
					}
					
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					
					Image result = null;
					byte img_result[];
								
					byte[] imgToBeSteg = new byte[(int) fileChosen.length()];
				
					result = add_text(img, jTextFieldCryptingTxt.getText());
					
					byte[] array = jTextFieldCryptingTxt.getText().getBytes();
					
					//img_result = encode_text(imgToBeSteg , array , 32);
					
					try {
						String text = jTextFieldCryptingTxt.getText();
						String name = fileChosen.getName();
						String path = fileChosen.getPath();
						path = path.substring(0,path.length()-name.length()-1);
						
						String stego = JOptionPane.showInputDialog(frame,
										"Enter output file name:", "File name",
										JOptionPane.PLAIN_MESSAGE);
						
						if(encode(path,name,stego,text))
						{
							JOptionPane.showMessageDialog(frame, "The Image was encoded Successfully!", 
								"Success!", JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(frame, "The Image could not be encoded!", 
								"Error!", JOptionPane.INFORMATION_MESSAGE);
						}
						//save new image in folder
						jLabelStegoImg.setIcon(new ImageIcon(ImageIO.read(new File(path + "/" + stego))));
						
					}
					catch(Exception except) {
					//msg if opening fails
						
						except.printStackTrace();
						
					JOptionPane.showMessageDialog(frame, "The File cannot be opened!", 
						"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
		
			});
}
		return jButtonOK;
	}
	
	/*
     *Encrypt an image with text, the output file will be of type .png
     *@param path      The path (folder) containing the image to modify
     *@param original  The name of the image to modify
     *@param stego     The output name of the file
     *@param message   The text to hide in the image
     *@param type      integer representing either basic or advanced encoding
 */

	public boolean encode(String path, String original, String stego,
			String message) {
		
		String file_name = image_path(path, original);
		BufferedImage image_orig = getImage(file_name);

		// user space is not necessary for Encrypting

		BufferedImage image = user_space(image_orig);

		image = add_text(image, message);

		return setImage(image, new File(image_path(path, stego)));

	}

	private String image_path(String path, String name) {

		return path + "/" + name;

	}
	
	/*
     *Handles the addition of text into an image
     *@param image The image to add hidden text to
     *@param text The text to hide in the image
     *@return Returns the image with the text embedded in it
 */

	private BufferedImage add_text(BufferedImage image, String text)
	{
		// convert all items to byte arrays: image, message, message length
		byte img[] = get_byte_data(image);
		byte msg[] = text.getBytes();
		byte len[] = bit_conversion(msg.length);
		
		try {	
			encode_text(img, len, 0); // 0 first positiong

			encode_text(img, msg, 32); // 4 bytes of space for length:
										// 4bytes*8bit = 32 bits
		}
		catch (Exception e)
		{

			JOptionPane.showMessageDialog(null,

			"Target File cannot hold message!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return image;
	}

	private BufferedImage user_space(BufferedImage image) {

		// create new_img with the attributes of image

		BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D graphics = new_img.createGraphics();

		graphics.drawRenderedImage(image, null);

		graphics.dispose(); // release all allocated memory for this image

		return new_img;

	}

	private byte[] get_byte_data(BufferedImage image)

	{
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}

	private byte[] bit_conversion(int i)

	{
		// originally integers (ints) cast into bytes

		// byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
		// byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
		// byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
		// byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);

		// only using 4 bytes

		byte byte3 = (byte) ((i & 0xFF000000) >>> 24); // 0
		byte byte2 = (byte) ((i & 0x00FF0000) >>> 16); // 0
		byte byte1 = (byte) ((i & 0x0000FF00) >>> 8); // 0
		byte byte0 = (byte) ((i & 0x000000FF));

		// {0,0,0,byte0} is equivalent, since all shifts >=8 will be 0

		return (new byte[] { byte3, byte2, byte1, byte0 });
	}

	/*
     *Encode an array of bytes into another array of bytes at a supplied offset
     *@param image   Array of data representing an image
     *@param addition Array of data to add to the supplied image data array
     *@param offset   The offset into the image array to add the addition data
     *@return Returns data Array of merged image and addition data
     */
	
	private byte[] encode_text(byte[] image, byte[] addition, int offset) {
		// check that the data + offset will fit in the image

		if (addition.length + offset > image.length)

		{
			throw new IllegalArgumentException("File not long enough!");
		}

		// loop through each addition byte

		for (int i = 0; i < addition.length; i++)

		{
			// loop through the 8 bits of each byte

			int add = addition[i];

			for (int bit = 7; bit >= 0; --bit, ++offset) 
				// ensure the new offset value carries on through both loops
			{
                // assign an integer to b, shifted by bit spaces AND 1

				// a single bit of the current byte

				int b = (add >>> bit) & 1;

				// assign the bit by taking: [(previous byte value) AND 0xfe] OR
				// bit to add

				// changes the last bit of the byte in the image to be the bit
				// of addition

				image[offset] = (byte) ((image[offset] & 0xFE) | b);
			}
		}
		return image;
	}

	private boolean setImage(BufferedImage image, File file)

	{
		try

		{
			file.delete(); // delete resources used by the File
			ImageIO.write(image, "gif", file);

			return true;
		}

		catch (Exception e)

		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
			"File could not be saved!", "Error", JOptionPane.ERROR_MESSAGE);

			return false;
		}
	}
	
	private BufferedImage getImage(String f) {

		BufferedImage img = null;
			
		try
		{
			File file = new File(f);
		    img = ImageIO.read(file);
		        
		} catch (IOException ex)

		{
			JOptionPane.showMessageDialog(null,

			"Image could not be read!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return img;
	}
}

