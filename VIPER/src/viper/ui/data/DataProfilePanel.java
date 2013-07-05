package viper.ui.data;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;

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

import org.apache.james.mime4j.dom.datetime.DateTime;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.xml.sax.SAXException;

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
	private static JTextField jTextFieldPath;
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
		jButtonGenerate = new JButton("Generate profile");
		jButtonGenerate.setBounds(800, 600, 150, 30);
		jButtonGenerate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				walkTree(new File(jTextFieldPath.getText()));
			}
		});
		
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jButtonGenerate);
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
					
					try {
						System.out.println();
						System.out.println();
						System.out.println(listFile[i].getPath());
						
						Path filePath = Paths.get(listFile[i].getPath());
						BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
						
						File file = new File(listFile[i].getPath());
						InputStream input = new FileInputStream(file);
						Metadata metadata = new Metadata();
						BodyContentHandler handler = new BodyContentHandler(
								10 * 1024 * 1024);
						AutoDetectParser parser = new AutoDetectParser();
						parser.parse(input, handler, metadata);
						
			            // Display all metadata
						/*
						String[] metadataNames = metadata.names();
						for(String name : metadataNames){
			                System.out.println(name + ": " + metadata.get(name));
			            }
						*/
						String type = metadata.get("Content-Type").split("/")[0];
						String ext = metadata.get("Content-Type").split("/")[1];
						int size = (int) attr.size();
						int height = 0;
						int width = 0;
						int res = 0;
						
						int releaseDate = 0;
						double duration = 0;
						String channelType = "unknown";
						String artist = "unknown";
						int sampleRate = 0;
						String genre = "unknown";
						for (int j = 0; j < metadata.names().length; j++) {
							if (type.equals("image")) {
								if (metadata.names()[j].toLowerCase().contains(
										"height")) {
									height = Integer.parseInt(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"width")) {
									width = Integer.parseInt(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"x resolution")) {
									res = Integer.parseInt(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
							}
							if (type.equals("audio")) {
								if (metadata.names()[j].toLowerCase().contains(
										"releasedate")) {
									releaseDate = Integer.parseInt(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"duration")) {
									duration = Double.parseDouble(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"channeltype")) {
									channelType = metadata.get(metadata.names()[j]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"artist")) {
									artist = metadata.get(metadata.names()[j]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"samplerate")) {
									sampleRate = Integer.parseInt(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"genre")) {
									genre = metadata.get(metadata.names()[j]);
								}
							}
							if (type.equals("video")) {
								if (metadata.names()[j].toLowerCase().contains(
										"height") || metadata.names()[j].toLowerCase().contains(
												"length")) {
									height = (int) Double.parseDouble(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"width")) {
									width = (int) Double.parseDouble(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
								if (metadata.names()[j].toLowerCase().contains(
										"samplerate")) {
									sampleRate = (int) Double.parseDouble(metadata.get(
											metadata.names()[j]).split(" ")[0]);
								}
							}
						}
						
						System.out.println("Full Path: " + listFile[i].getPath());
						System.out.println("Type: " + type);
						System.out.println("Extension: " + ext);
						System.out.println("Size: " + size);
						Entry entry = new Entry();
						entry.setFullpath(listFile[i].getPath());
						entry.setType(type);
						entry.setExtension(ext);
						entry.setSize(size);
						if (type.equals("image")) {
							System.out.println("Height: " + height);
							System.out.println("Width: " + width);
							System.out.println("Resolution: " + res);
							entry.setHeight(height);
							entry.setWidth(width);
							entry.setReleaseDate(res);
						}
						if (type.equals("audio")) {
							System.out.println("Release Date: " + releaseDate);
							System.out.println("Duration: " + duration);
							System.out.println("Channel Type: " + channelType);
							System.out.println("Artist: " + artist);
							System.out.println("Sample Rate: " + sampleRate);
							System.out.println("Genre: " + genre);
							entry.setReleaseDate(releaseDate);
							entry.setDuration(duration);
							entry.setChannelType(channelType);
							entry.setArtist(artist);
							entry.setSampleRate(sampleRate);
							entry.setGenre(genre);
						}
						if (type.equals("video")) {
							System.out.println("Height: " + height);
							System.out.println("Width: " + width);
							System.out.println("Sample Rate: " + sampleRate);
							entry.setHeight(height);
							entry.setWidth(width);
							entry.setSampleRate(sampleRate);
						}
						entryArray.add(entry);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TikaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            }
        }
        
        try {
			XmlFile file = new XmlFile();
			file.setRootDir(jTextFieldPath.getText());
			file.setDateProfiled(new Date());
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