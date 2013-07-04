package viper.ui.data;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jfif.JfifDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.photoshop.PsdHeaderDirectory;

import viper.entity.Entry;
import viper.entity.User;
import viper.entity.XmlFile;
import viper.ui.main.HomePanel;
import viper.ui.main.MenuPanel;
import viper.ui.main.StoredPreferences;
import viper.ui.settings.SettingsPanel;
import viper.ui.user.FaceRecPanel;

public class DataProfilePanel extends JPanel implements StoredPreferences {

	private static JFrame frame = null;
	private JLabel jLabelBackground;
	private JTextField jTextFieldPath;
	private JLabel jLabelImage;
	private JCheckBox jCheckBoxImageJPEG;
	private JCheckBox jCheckBoxImageTIFF;
	private JCheckBox jCheckBoxImagePSD;
	private JCheckBox jCheckBoxImagePNG;
	private JCheckBox jCheckBoxImageBMP;
	private JCheckBox jCheckBoxImageGIF;
	private JButton jButtonGenerate;
	private static ArrayList<String> extArray = new ArrayList<String>();
	private static ArrayList<Entry> entryArray = new ArrayList<Entry>();
	
	/**
	 * Create the panel.
	 */
	public DataProfilePanel() {
		super();
		initialize();
	}

	public DataProfilePanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private void initialize() {

		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/main/background.png")));
		
		jTextFieldPath = new JTextField();
		jTextFieldPath.setBounds(20, 20, 940, 40);
		jTextFieldPath.setBackground(Color.white);
		PromptSupport.setPrompt(
				"Profile not generated. Click to select a directory.",
				jTextFieldPath);
		jTextFieldPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Select directory");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				String userhome = System.getProperty("user.home");
				fc.setCurrentDirectory(new File(userhome));
				int returnVal = fc.showOpenDialog(DataProfilePanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jTextFieldPath.setText(fc.getSelectedFile().getAbsolutePath());
				}

			}
		});

		jLabelImage = new JLabel();
		jLabelImage.setBounds(20, 80, 150, 40);
		jLabelImage.setIcon(new ImageIcon(getClass().getResource(
				"/viper/image/data/image.png")));
		
		jCheckBoxImageJPEG = new JCheckBox();
		jCheckBoxImageJPEG.setBounds(20, 120, 150, 20);
		jCheckBoxImageJPEG.setText("JPEG");
		jCheckBoxImageJPEG.setForeground(Color.white);
		jCheckBoxImageJPEG.setSelected(true);
		
		jCheckBoxImageTIFF = new JCheckBox();
		jCheckBoxImageTIFF.setBounds(20, 150, 150, 20);
		jCheckBoxImageTIFF.setText("TIFF");
		jCheckBoxImageTIFF.setForeground(Color.white);
		jCheckBoxImageTIFF.setSelected(true);

		jCheckBoxImagePSD = new JCheckBox();
		jCheckBoxImagePSD.setBounds(20, 180, 150, 20);
		jCheckBoxImagePSD.setText("PSD");
		jCheckBoxImagePSD.setForeground(Color.white);
		jCheckBoxImagePSD.setSelected(true);
		
		jCheckBoxImageBMP = new JCheckBox();
		jCheckBoxImageBMP.setBounds(20, 210, 150, 20);
		jCheckBoxImageBMP.setText("BMP");
		jCheckBoxImageBMP.setForeground(Color.white);
		jCheckBoxImageBMP.setSelected(true);
		
		jCheckBoxImagePNG = new JCheckBox();
		jCheckBoxImagePNG.setBounds(20, 240, 150, 20);
		jCheckBoxImagePNG.setText("PNG");
		jCheckBoxImagePNG.setForeground(Color.white);
		jCheckBoxImagePNG.setSelected(true);
		
		jCheckBoxImageGIF = new JCheckBox();
		jCheckBoxImageGIF.setBounds(20, 270, 150, 20);
		jCheckBoxImageGIF.setText("GIF");
		jCheckBoxImageGIF.setForeground(Color.white);
		jCheckBoxImageGIF.setSelected(true);

		jButtonGenerate = new JButton("Generate profile");
		jButtonGenerate.setBounds(800, 600, 150, 30);
		jButtonGenerate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (jCheckBoxImageJPEG.isSelected()) {
					extArray.add(".jpeg");
					extArray.add(".jpg");
					extArray.add(".jpe");
				}
				if (jCheckBoxImageTIFF.isSelected()) {
					extArray.add(".tif");
					extArray.add(".tiff");
				}
				if (jCheckBoxImagePSD.isSelected()) {
					extArray.add(".psd");
				}
				/*if (jCheckBoxImageBMP.isSelected()) {
					extArray.add(".bmp");
					extArray.add(".rle");
					extArray.add(".dib");
				}
				if (jCheckBoxImagePNG.isSelected()) {
					extArray.add(".png");
					extArray.add(".pns");
				}
				if (jCheckBoxImageGIF.isSelected()) {
					extArray.add(".gif");
				}*/
				
				walkTree(new File(jTextFieldPath.getText()));
			}
		});
		
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jButtonGenerate);
		/*this.add(jCheckBoxImageGIF);
		this.add(jCheckBoxImagePNG);
		this.add(jCheckBoxImageBMP);*/
		this.add(jCheckBoxImagePSD);
		this.add(jCheckBoxImageTIFF);
		this.add(jCheckBoxImageJPEG);
		this.add(jLabelImage);
		this.add(jTextFieldPath);
		this.add(jLabelBackground);

	}
	
	public static void walkTree(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null) {
            for (int i=0; i<listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                	walkTree(listFile[i]);
                } else {
                	for (int j=0; j<extArray.size(); j++) {
                		if (listFile[i].getName().endsWith(extArray.get(j))) {
                			Entry entry = new Entry();
                			
                			System.out.println();
					    	System.out.println("Full Path: " + listFile[i].getPath());
					    	entry.setFullpath(listFile[i].getPath());
					    	System.out.println("Type: " + "image");
					    	entry.setType("image");
							System.out.println("Extension: " + extArray.get(j));
							entry.setExtension(extArray.get(j));
							Path path = Paths.get(listFile[i].getPath());
							try {
								Path filePath = Paths.get(listFile[i].getPath());
								BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
								System.out.println("Size: " + attr.size());
								entry.setSize((int)attr.size());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
							File file = new File(listFile[i].getPath());
							try {
								Metadata metadata = ImageMetadataReader
										.readMetadata(file);
								if (extArray.get(j).equals(".jpeg")
										|| extArray.get(j).equals(".jpg")
										|| extArray.get(j).equals(".jpe")) {
									JpegDirectory directory = metadata
											.getDirectory(JpegDirectory.class);
									System.out.println("Height: " +directory.getString(JpegDirectory.TAG_JPEG_IMAGE_HEIGHT));
									entry.setHeight(Integer.parseInt(directory.getString(JpegDirectory.TAG_JPEG_IMAGE_HEIGHT)));
									System.out.println("Width: " +directory.getString(JpegDirectory.TAG_JPEG_IMAGE_WIDTH));
									entry.setWidth(Integer.parseInt(directory.getString(JpegDirectory.TAG_JPEG_IMAGE_WIDTH)));
								}
								if (extArray.get(j).equals(".tif")
										|| extArray.get(j).equals(".tiff")) {
									ExifSubIFDDirectory directory = metadata
											.getDirectory(ExifSubIFDDirectory.class);
									System.out.println("Height: " +directory.getString(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));
									entry.setHeight(Integer.parseInt(directory.getString(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT)));
									System.out.println("Width: " +directory.getString(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH));
									entry.setWidth(Integer.parseInt(directory.getString(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH)));
								}
								if (extArray.get(j).equals(".psd")) {
									PsdHeaderDirectory directory = metadata
											.getDirectory(PsdHeaderDirectory.class);
									System.out.println("Height: " +directory.getString(PsdHeaderDirectory.TAG_IMAGE_HEIGHT));
									entry.setHeight(Integer.parseInt(directory.getString(PsdHeaderDirectory.TAG_IMAGE_HEIGHT)));
									System.out.println("Width: " +directory.getString(PsdHeaderDirectory.TAG_IMAGE_WIDTH));
									entry.setWidth(Integer.parseInt(directory.getString(PsdHeaderDirectory.TAG_IMAGE_WIDTH)));
								}
								/*
								for (Directory directory : metadata.getDirectories()) {
								    for (Tag tag : directory.getTags()) {
								        System.out.println(tag);
								    }
								}*/
							} catch (ImageProcessingException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							entryArray.add(entry);
							
							
                        }
                	}
                }
            }
        }
        
        try {
			XmlFile file = new XmlFile();
			file.setEntry(entryArray);
			File xmlFile = new File("./viperdata.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlFile.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(file, xmlFile);
			jaxbMarshaller.marshal(file, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
    }
}