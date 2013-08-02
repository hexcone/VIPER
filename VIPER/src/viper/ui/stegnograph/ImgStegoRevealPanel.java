package viper.ui.stegnograph;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

import viper.entity.TrackedPanel;
import viper.ui.main.StoredPreferences;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ImgStegoRevealPanel extends TrackedPanel implements StoredPreferences {

	private static final long serialVersionUID = 1L;
	private static JFrame frame = null;
	private JLabel jLabelBackground;
	private JLabel jLabelTitle = null;
	private JLabel jLabelStegoImg = null;
	private JButton jButtonBrowse = null;
	private JLabel jLabelPwd = null;
	private JTextField jTextFieldPwd = null;
	private JLabel jLabelDecryptTxt = null;
	private JTextField jTextFieldDecryptedTxt = null;
	private JButton jButtonOK = null;
	private JFileChooser fc = null;
	private JButton jButtonRetrieve = null;
	private File file;  
	
	String filename;
	String pathname;

	public ImgStegoRevealPanel() {
		super();
		initialize();

	}

	public ImgStegoRevealPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	@Override
	public void initialize() {

		super.initialize();
		jLabelDecryptTxt = new JLabel();
		jLabelDecryptTxt.setText("The hidden message is: ");
		jLabelDecryptTxt.setLocation(new Point(630, 173));
		jLabelDecryptTxt.setForeground(Color.gray);
		jLabelDecryptTxt.setSize(new Dimension(180, 27));
		jLabelPwd = new JLabel();
		jLabelPwd.setText("Password: ");
		jLabelPwd.setLocation(new Point(630, 76));
		jLabelPwd.setForeground(Color.gray);
		jLabelPwd.setSize(new Dimension(85, 27));
		jLabelPwd.setVisible(false);
		jLabelStegoImg = new JLabel();
		jLabelStegoImg.setText("StegoImg");
		jLabelStegoImg.setLocation(new Point(60, 76));
		jLabelStegoImg.setForeground(Color.gray);
		jLabelStegoImg.setSize(new Dimension(400, 450));
		jLabelTitle = new JLabel();
		jLabelTitle.setFont(new Font("Britannic Bold", Font.BOLD, 20));
		jLabelTitle.setLocation(new Point(330, 15));
		jLabelTitle.setSize(new Dimension(450, 30));
		jLabelTitle.setForeground(Color.gray);
		jLabelTitle.setText("Retrieve Hidden Message from Picture");
		jLabelBackground = new JLabel();
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));
		jLabelBackground.setSize(new Dimension(1000, 700));
		jLabelBackground.setForeground(Color.black);
		jLabelBackground.setText("");
		jLabelBackground.setLocation(new Point(0, 0));
		fc = new JFileChooser();
		this.setLayout(null);
		this.setSize(new Dimension(1000, 700));
		this.add(jLabelTitle, null);
		this.add(jLabelStegoImg, null);
		this.add(getJButtonBrowse(), null);
		this.add(jLabelPwd, null);
		this.add(getJTextFieldPwd(), null);
		this.add(jLabelDecryptTxt, null);
		this.add(getJTextFieldDecryptedTxt(), null);
		this.setBackground(Color.black);
		this.add(getJButtonOK(), null);
		this.add(getJButtonRetrieve(), null);
		this.add(jLabelBackground, jLabelBackground.getName());
	}

	/**
	 * This method initializes jButtonBrowse
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonBrowse() {
		if (jButtonBrowse == null) {
			jButtonBrowse = new JButton();
			jButtonBrowse.setLocation(new Point(473, 76));
			jButtonBrowse.setText("Browse");
			jButtonBrowse.setForeground(Color.black);
			jButtonBrowse.setSize(new Dimension(100, 29));
			jButtonBrowse
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							int returnVal = fc
									.showOpenDialog(ImgStegoRevealPanel.this);

							if (returnVal == fc.APPROVE_OPTION) {

								if (isImage(fc.getSelectedFile()) == true) {
									jLabelStegoImg.setText(fc.getSelectedFile().getName());
									filename = fc.getSelectedFile().getName();
									pathname = fc.getSelectedFile().getAbsolutePath();
									pathname = pathname.substring(0,pathname.length()-filename.length()-1);
								}
							}
						}

					});
		}
		return jButtonBrowse;
	}

	private boolean isImage(File Imagefile) {

		try {
			jLabelStegoImg.setIcon(new ImageIcon(Imagefile.getAbsolutePath()));
			file = Imagefile;
		}

		catch (NullPointerException e) {
			e.printStackTrace();
			
			return false;
		}

		return true;	
	}

	/**
	 * This method initializes jTextFieldPwd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldPwd() {
		if (jTextFieldPwd == null) {
			jTextFieldPwd = new JTextField();
			jTextFieldPwd.setLocation(new Point(630, 118));
			jTextFieldPwd.setSize(new Dimension(200, 33));
			jTextFieldPwd.setVisible(false);
		}
		return jTextFieldPwd;
	}

	/**
	 * This method initializes jTextFieldDecryptedTxt
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldDecryptedTxt() {
		if (jTextFieldDecryptedTxt == null) {
			jTextFieldDecryptedTxt = new JTextField();
			jTextFieldDecryptedTxt.setLocation(new Point(630, 214));
			jTextFieldDecryptedTxt.setSize(new Dimension(250, 300));
		}
		return jTextFieldDecryptedTxt;
	}


	/**
	 * This method initializes jButtonOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setLocation(new Point(847, 594));
			jButtonOK.setText("OK");
			jButtonOK.setSize(new Dimension(100, 29));
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					String message = null;
					byte[] msg;
					
					 try
					 {
						msg = get_byte_data(ImageIO.read(file));
						if (msg == null || msg.equals(""))
							message = "msg is null";
						else
							message = decode(pathname,filename);
					}
					
					 catch (UnsupportedEncodingException e1) 
					 {
							e1.printStackTrace();
						}
					 
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					
					jTextFieldDecryptedTxt.setText(message);
				}
			});
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonRetrieve	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRetrieve() {
		if (jButtonRetrieve == null) {
			jButtonRetrieve = new JButton();
			jButtonRetrieve.setText("Retrieve");
			jButtonRetrieve.setSize(new Dimension(100, 29));
			jButtonRetrieve.setForeground(Color.black);
			jButtonRetrieve.setLocation(new Point(837, 119));
			jButtonRetrieve.setVisible(false);
		}
		return jButtonRetrieve;
	}
	
	private byte[] get_byte_data(BufferedImage image)

	{
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}
	
	/*
     *Retrieves hidden text from an image
     *@param image Array of data, representing an image
     *@return Array of data which contains the hidden text
 */

    private byte[] decode_text(byte[] image)
    {
    	
        //int length = (int)image.length;
    	int length = 0;
        int offset  = 32;

        //loop through 32 bytes of data to determine text length

        for(int i=0; i<32; i++) //i=24 will also work, as only the 4th byte contains real data

        {
            length = (length << 1) | (image[i] & 1);
        }

        //ByteArrayOutputstream os = new ByteArrayOtputStream();
        //String test = "length = " + length +"\n" + "image length = " + image.length;
        //if (true)
        //	return test.getBytes();

        byte[] result = new byte[length];
               //loop through each byte of text

               for(int b=0; b<result.length; b++ )

               {
                   //loop through each bit within a byte of text

                   for(int i=0; i<8; i++, offset++)

                   {
                       //assign bit: [(new byte value) << 1] OR [(text byte) AND 1]

                       result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
                   }

               }

               return result;
           }
    
    public String decode(String path, String name)
    {
        byte[] decode;

        try
        {
            //user space is necessary for decrypting

            BufferedImage image  = user_space(getImage(image_path(path,name)));

            decode = decode_text(get_byte_data(image));

            return(new String(decode));

        }
        catch(Exception e)

        {

            JOptionPane.showMessageDialog(null,
                "There is no hidden message in this image!","Error",

                JOptionPane.ERROR_MESSAGE);

            return "";

        }
    }
    
    private String image_path(String path, String name)
    {

        return path + "/" + name;

    }
    
    private BufferedImage getImage(String f)
    {

        BufferedImage image = null;

        File file = new File(f);

        try

        {

            image = ImageIO.read(file);
        }
        catch(Exception ex)

        {

            JOptionPane.showMessageDialog(null,

                "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
        }

        return image;

    }
    
    private BufferedImage user_space(BufferedImage image)
    {

        //create new_img with the attributes of image

        BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D  graphics = new_img.createGraphics();

        graphics.drawRenderedImage(image, null);

        graphics.dispose(); //release all allocated memory for this image

        return new_img;

    }
}
