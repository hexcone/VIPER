package viper.ui.data;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.xml.sax.SAXException;

import viper.entity.Entry;
import viper.entity.User;
import viper.entity.XmlFile;
import viper.ui.main.StoredPreferences;
import viper.ui.user.RegisterPanel;

public class DataProfilePanel extends JPanel implements StoredPreferences {

	private static JFrame frame = null;
	private JLabel jLabelBackground;
	private static JTextField jTextFieldPath;
	private JButton jButtonGenerate;
	private ChartPanel chartPanel;
	private static ArrayList<String> extArray = new ArrayList<String>();
	private static ArrayList<Entry> entryArray = new ArrayList<Entry>();
	private User user = User.retrieveUser(PREF.get(USERID, null));
	private static XmlFile xmlfile;
	private String category;
	private static String catArray[] = {"main", "image", "audio", "video"};
	private int loopCount;
	private JLabel jLabelAllFiles;
	
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
		jTextFieldPath.setBounds(20, 20, 830, 40);
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
		jButtonGenerate = new JButton("Generate");
		jButtonGenerate.setBounds(870, 25, 100, 30);
		jButtonGenerate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				walkTree(new File(jTextFieldPath.getText()));
				User.updateUser("./"+ PREF.get(USERNAME, null) +"-viperprofile.xml");
			}
		});
		if (user.getUserMetadataPath() != null) {
			try {
				File file = new File("./"+ PREF.get(USERNAME, null) +"-viperprofile.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(XmlFile.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				xmlfile = (XmlFile) jaxbUnmarshaller.unmarshal(file);
				jTextFieldPath.setText(xmlfile.getRootDir());
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			chartPanel = new ChartPanel(drawChart("main", 0));
			chartPanel.setBounds(20, 70, 950, 550);
			chartPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			chartPanel.addChartMouseListener(new ChartMouseListener(){
				@Override
				public void chartMouseClicked(ChartMouseEvent e) {
					System.out.println(e.getEntity().getToolTipText());
					String backupCategory = category;
					category = e.getEntity().getToolTipText().split(":")[0].toLowerCase();
					if (Arrays.asList(catArray).contains(category)) {
						chartPanel.setChart(drawChart(category, 0));
						loopCount = 0;
						chartPanel.repaint();
					}
					else {
						category = backupCategory;
						if (category.equals("image")) {
							chartPanel.setChart(drawChart(category, loopCount%3));
							loopCount++;
							chartPanel.repaint();
						}
						else if (category.equals("audio")) {
							chartPanel.setChart(drawChart(category, loopCount%7));
							loopCount++;
							chartPanel.repaint();
						}
						else if (category.equals("video")) {
							chartPanel.setChart(drawChart(category, loopCount%3));
							loopCount++;
							chartPanel.repaint();
						}
					}
					if (category.toLowerCase().equals("main")) {
						jLabelAllFiles.setVisible(false);
						jLabelAllFiles.repaint();
					}
					else {
						jLabelAllFiles.setVisible(true);
						jLabelAllFiles.repaint();
					}
				}

				@Override
				public void chartMouseMoved(ChartMouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		jLabelAllFiles = new JLabel();
		jLabelAllFiles.setText("Show all files");
		jLabelAllFiles.setFont(new Font("Trebuchet MS", Font.ITALIC, 14));
		jLabelAllFiles.setForeground(Color.gray);
		jLabelAllFiles.setBounds(850, 630, 90, 30);
		jLabelAllFiles.setVisible(false);
		jLabelAllFiles
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabelAllFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				jLabelAllFiles.setForeground(Color.cyan);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jLabelAllFiles.setForeground(Color.gray);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				category = "main";
				chartPanel.setChart(drawChart(category, 0));
				loopCount = 0;
				chartPanel.repaint();
				jLabelAllFiles.setVisible(false);
				jLabelAllFiles.repaint();
			}
		});
		
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		
		if (user.getUserMetadataPath() != null) {
			this.add(jLabelAllFiles);
			this.add(chartPanel);
		}
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
							entry.setRes(res);
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
			xmlfile = new XmlFile();
			xmlfile.setRootDir(jTextFieldPath.getText());
			xmlfile.setDateProfiled(new Date());
			xmlfile.setEntry(entryArray);
			File xmlFilePath = new File("./"+ PREF.get(USERNAME, null) +"-viperprofile.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlFile.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(xmlfile, xmlFilePath);
			jaxbMarshaller.marshal(xmlfile, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
    }
	
	public JFreeChart drawChart(String category, int quotient) {
		// create a dataset...
        final DefaultPieDataset data = new DefaultPieDataset();
        entryArray = xmlfile.getEntry();
        String chartTitle = null;
        if (category.toLowerCase().contains("main")) {
        	chartTitle = "All Files";
	        int image = 0;
	        int audio = 0;
	        int video = 0;
	        int other = 0;
	        for (int i=0; i< entryArray.size(); i++) {
	        	if (entryArray.get(i).getType().equals("image")) {
	        		image++;
	        	}
	        	else if (entryArray.get(i).getType().equals("audio")) {
	        		audio++;
	        	}
	        	else if (entryArray.get(i).getType().equals("video")) {
	        		video++;
	        	}
	        	else {
	        		other++;
	        	}
	        }
	        int sum = image + audio + video + other;
	        data.setValue("Image", (double) image/sum);
	        data.setValue("Audio", (double) audio/sum);
			data.setValue("Video", (double) video / sum);
			data.setValue("Other", (double) other / sum);
		} else if (category.toLowerCase().contains("image")) {
			ArrayList<Entry> imageArray = new ArrayList<Entry>();
	        for (int i=0; i< entryArray.size(); i++) {
	        	if (entryArray.get(i).getType().equals("image")) {
        			imageArray.add(entryArray.get(i));
	        		
	        	}
	        }
			if (quotient == 0) {
				chartTitle = "Images by file size";
	        	
		        int small = 0;		// sum <= 51200 (50kb)
		        int medium = 0;		// sum <= 1024000 (1000kb)
		        int large = 0;		// sum > 1024000
		        
		        for (int i=0; i< imageArray.size(); i++) {
		        	int total = imageArray.get(i).getSize();
		        	if (total <= 51200) {
		        		small++;
		        	}
		        	else if (total <= 1024000) {
		        		medium++;
		        	}
		        	else {
		        		large++;
		        	}
		        }
		        int sum = small + medium + large;
		        data.setValue("Small (<50KB)", (double) small/sum);
		        data.setValue("Medium (<1000KB)", (double) medium/sum);
		        data.setValue("Large", (double) large/sum);
			}
			if (quotient == 1) {
				chartTitle = "Images by resolution";
				ArrayList<Integer> intArray = new ArrayList<Integer>();
	        	ArrayList<Integer> counterArray = new ArrayList<Integer>();
		        
		        for (int i=0; i< imageArray.size(); i++) {
		        	if (!intArray.contains(imageArray.get(i).getRes())) {
	        			intArray.add(imageArray.get(i).getRes());

	        		}
		        }
		        
		        for (int i=0; i< intArray.size(); i++) {
		        	counterArray.add(0);
		        }
		        for (int i=0; i< imageArray.size(); i++) {
		        	for (int j = 0; j < intArray.size(); j++) {
		        		if (imageArray.get(i).getRes() == intArray.get(j)) {
			        		int count = counterArray.get(j);
			        		count++;
			        		counterArray.set(j, count);
			        	}
		        	}
		        }
		        int sum = 0;
		        for(int i = 0; i < counterArray.size(); i++){
		            sum += counterArray.get(i);
		        }
		        for(int i = 0; i < counterArray.size(); i++){
		        	if (intArray.get(i) == 0) {
		        		data.setValue("unknown", (double) counterArray.get(i)/sum);
		        	}
		        	else {
		        		data.setValue(intArray.get(i).toString() + " dpi", (double) counterArray.get(i)/sum);
		        	}
		        }
			}
			if (quotient == 2) {
				chartTitle = "Images by image size";
	        	
		        int icon = 0;		// sum <= 150
		        int medium = 0;		// sum <= 1500
		        int large = 0;		// sum > 1500
		        
		        for (int i=0; i< imageArray.size(); i++) {
		        	int total = imageArray.get(i).getHeight() + imageArray.get(i).getWidth();
		        	if (total <= 150) {
		        		icon++;
		        	}
		        	else if (total <= 1500) {
		        		medium++;
		        	}
		        	else {
		        		large++;
		        	}
		        }
		        int sum = icon + medium + large;
		        data.setValue("Icon", (double) icon/sum);
		        data.setValue("Medium", (double) medium/sum);
		        data.setValue("Large", (double) large/sum);
			}
		}
		else if (category.toLowerCase().contains("audio")) {
			ArrayList<Entry> audioArray = new ArrayList<Entry>();
	        for (int i=0; i< entryArray.size(); i++) {
	        	if (entryArray.get(i).getType().equals("audio")) {
	        		audioArray.add(entryArray.get(i));
	        		
	        	}
	        }
			if (quotient == 0) {
				chartTitle = "Audios by file size";
	        	
		        int small = 0;		// sum <= 51200 (50kb)
		        int medium = 0;		// sum <= 1024000 (1000kb)
		        int large = 0;		// sum > 1024000
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	int total = audioArray.get(i).getSize();
		        	if (total <= 10485760) {
		        		small++;
		        	}
		        	else if (total <= 20971520) {
		        		medium++;
		        	}
		        	else {
		        		large++;
		        	}
		        }
		        int sum = small + medium + large;
		        data.setValue("Small (<10MB)", (double) small/sum);
		        data.setValue("Medium (<20MB)", (double) medium/sum);
		        data.setValue("Large", (double) large/sum);
			}
			else if (quotient == 1) {
				chartTitle = "Audios by release date";
				ArrayList<Integer> yearArray = new ArrayList<Integer>();
	        	ArrayList<Integer> counterArray = new ArrayList<Integer>();
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	if ((audioArray.get(i).getReleaseDate() >= 0) && (audioArray.get(i).getReleaseDate() <= Calendar.getInstance().get(Calendar.YEAR))) {
			        	if (!yearArray.contains(audioArray.get(i).getReleaseDate())) {
			        		yearArray.add(audioArray.get(i).getReleaseDate());
		        		}
		        	}
		        }
		        
		        for (int i=0; i< yearArray.size(); i++) {
		        	counterArray.add(0);
		        }
		        for (int i=0; i< audioArray.size(); i++) {
		        	for (int j = 0; j < yearArray.size(); j++) {
		        		if (audioArray.get(i).getReleaseDate() == yearArray.get(j)) {
			        		int count = counterArray.get(j);
			        		count++;
			        		counterArray.set(j, count);
			        	}
		        	}
		        }
		        int sum = 0;
		        for(int i = 0; i < counterArray.size(); i++){
		            sum += counterArray.get(i);
		        }
		        for(int i = 0; i < counterArray.size(); i++){
		        	if (yearArray.get(i) == 0) {
		        		data.setValue("unknown", (double) counterArray.get(i)/sum);
		        	}
		        	else {
		        		data.setValue(yearArray.get(i).toString(), (double) counterArray.get(i)/sum);
		        	}
		        }
			}
			else if (quotient == 2) {
				chartTitle = "Audios by duration";
	        	
		        int small = 0;		// sum <= 51200 (50kb)
		        int medium = 0;		// sum <= 1024000 (1000kb)
		        int large = 0;		// sum > 1024000
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	int total = (int) audioArray.get(i).getDuration();
		        	if (total <= 180000) {
		        		small++;
		        	}
		        	else if (total <= 300000) {
		        		medium++;
		        	}
		        	else {
		        		large++;
		        	}
		        }
		        int sum = small + medium + large;
		        data.setValue("Short (<3min)", (double) small/sum);
		        data.setValue("Medium (<5min)", (double) medium/sum);
		        data.setValue("Long", (double) large/sum);
			}
			else if (quotient == 3) {
				chartTitle = "Audios by channel type";
	        	
		        int stereo = 0;
		        int mono = 0;
		        int unknown = 0;
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	String type = audioArray.get(i).getChannelType();
		        	if (type.toLowerCase().equals("stereo")) {
		        		stereo++;
		        	}
		        	else if (type.toLowerCase().equals("mono")) {
		        		mono++;
		        	}
		        	else {
		        		unknown++;
		        	}
		        }
		        int sum = stereo + mono + unknown;
		        data.setValue("Stereo", (double) stereo/sum);
		        data.setValue("Mono", (double) mono/sum);
		        if (unknown > 0) {
		        	data.setValue("Unknown", (double) unknown/sum);
		        }
			}
			else if (quotient == 4) {
				chartTitle = "Audios by artist";
				ArrayList<String> artistArray = new ArrayList<String>();
	        	ArrayList<Integer> counterArray = new ArrayList<Integer>();
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	if (!artistArray.contains(audioArray.get(i).getArtist())) {
		        		artistArray.add(audioArray.get(i).getArtist());
	        		}
		        }
		        
		        for (int i=0; i< artistArray.size(); i++) {
		        	counterArray.add(0);
		        }
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	for (int j = 0; j < artistArray.size(); j++) {
		        		if (audioArray.get(i).getArtist() == artistArray.get(j)) {
			        		int count = counterArray.get(j);
			        		count++;
			        		counterArray.set(j, count);
			        	}
		        	}
		        }
		        int sum = 0;
		        for(int i = 0; i < counterArray.size(); i++){
		            sum += counterArray.get(i);
		        }
		        for(int i = 0; i < counterArray.size(); i++){
	        		data.setValue(artistArray.get(i).toString(), (double) counterArray.get(i)/sum);
		        }
			}
			else if (quotient == 5) {
				chartTitle = "Audios by sample rate";
				ArrayList<Integer> rateArray = new ArrayList<Integer>();
	        	ArrayList<Integer> counterArray = new ArrayList<Integer>();
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	if (!rateArray.contains(audioArray.get(i).getSampleRate())) {
		        		rateArray.add(audioArray.get(i).getSampleRate());
	        		}
		        }
		        
		        for (int i=0; i< rateArray.size(); i++) {
		        	counterArray.add(0);
		        }
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	for (int j = 0; j < rateArray.size(); j++) {
		        		if (audioArray.get(i).getSampleRate() == rateArray.get(j)) {
			        		int count = counterArray.get(j);
			        		count++;
			        		counterArray.set(j, count);
			        	}
		        	}
		        }
		        int sum = 0;
		        for(int i = 0; i < counterArray.size(); i++){
		            sum += counterArray.get(i);
		        }
		        for(int i = 0; i < counterArray.size(); i++){
		        	if (rateArray.get(i) == 0) {
		        		data.setValue("unknown", (double) counterArray.get(i)/sum);
		        	}
		        	else {
		        		data.setValue(rateArray.get(i)/1000 + " kHz", (double) counterArray.get(i)/sum);
		        	}
		        }
			}
			else if (quotient == 6) {
				chartTitle = "Audios by genre";
				ArrayList<String> artistArray = new ArrayList<String>();
	        	ArrayList<Integer> counterArray = new ArrayList<Integer>();
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	if (!artistArray.contains(audioArray.get(i).getGenre())) {
		        		artistArray.add(audioArray.get(i).getGenre());
	        		}
		        }
		        
		        for (int i=0; i< artistArray.size(); i++) {
		        	counterArray.add(0);
		        }
		        
		        for (int i=0; i< audioArray.size(); i++) {
		        	for (int j = 0; j < artistArray.size(); j++) {
		        		if (audioArray.get(i).getGenre() == artistArray.get(j)) {
			        		int count = counterArray.get(j);
			        		count++;
			        		counterArray.set(j, count);
			        	}
		        	}
		        }
		        int sum = 0;
		        for(int i = 0; i < counterArray.size(); i++){
		            sum += counterArray.get(i);
		        }
		        for(int i = 0; i < counterArray.size(); i++){
	        		data.setValue(artistArray.get(i).toString(), (double) counterArray.get(i)/sum);
		        }
			}
		}
		else if (category.toLowerCase().contains("video")) {
			ArrayList<Entry> videoArray = new ArrayList<Entry>();
	        for (int i=0; i< entryArray.size(); i++) {
	        	if (entryArray.get(i).getType().equals("video")) {
	        		videoArray.add(entryArray.get(i));
	        	}
	        }
			if (quotient == 0) {
				chartTitle = "Videos by file size";
	        	
		        int small = 0;		// sum <= 52428800 (50mb)
		        int medium = 0;		// sum <= 314572800 (300mb)
		        int large = 0;		// sum > 314572800
		        
		        for (int i=0; i< videoArray.size(); i++) {
		        	int total = videoArray.get(i).getSize();
		        	
		        	if (total <= 52428800) {
		        		small++;
		        	}
		        	else if (total <= 314572800) {
		        		medium++;
		        	}
		        	else {
		        		large++;
		        	}
		        }
		        int sum = small + medium + large;
		        data.setValue("Small (<50MB)", (double) small/sum);
		        data.setValue("Medium (<300MB)", (double) medium/sum);
		        data.setValue("Large", (double) large/sum);
			}
			else if (quotient == 1) {
				chartTitle = "Videos by video size";
	        	
		        int icon = 0;		// sum <= 150
		        int medium = 0;		// sum <= 1500
		        int large = 0;		// sum > 1500
		        
		        for (int i=0; i< videoArray.size(); i++) {
		        	int total = videoArray.get(i).getHeight() + videoArray.get(i).getWidth();
		        	if (total <= 150) {
		        		icon++;
		        	}
		        	else if (total <= 1500) {
		        		medium++;
		        	}
		        	else {
		        		large++;
		        	}
		        }
		        int sum = icon + medium + large;
		        data.setValue("Icon", (double) icon/sum);
		        data.setValue("Medium", (double) medium/sum);
		        data.setValue("Large", (double) large/sum);
			}
			else if (quotient == 2) {
				chartTitle = "Videos by sample rate";
				ArrayList<Integer> rateArray = new ArrayList<Integer>();
	        	ArrayList<Integer> counterArray = new ArrayList<Integer>();
		        
		        for (int i=0; i< videoArray.size(); i++) {
		        	if (!rateArray.contains(videoArray.get(i).getSampleRate())) {
		        		rateArray.add(videoArray.get(i).getSampleRate());
	        		}
		        }
		        
		        for (int i=0; i< rateArray.size(); i++) {
		        	counterArray.add(0);
		        }
		        
		        for (int i=0; i< videoArray.size(); i++) {
		        	for (int j = 0; j < rateArray.size(); j++) {
		        		if (videoArray.get(i).getSampleRate() == rateArray.get(j)) {
			        		int count = counterArray.get(j);
			        		count++;
			        		counterArray.set(j, count);
			        	}
		        	}
		        }
		        int sum = 0;
		        for(int i = 0; i < counterArray.size(); i++){
		            sum += counterArray.get(i);
		        }
		        for(int i = 0; i < counterArray.size(); i++){
		        	if (rateArray.get(i) == 0) {
		        		data.setValue("unknown", (double) counterArray.get(i)/sum);
		        	}
		        	else {
		        		data.setValue(rateArray.get(i)/1000 + " kHz", (double) counterArray.get(i)/sum);
		        	}
		        }
			}
		}

        // create the chart...
        final JFreeChart chart = ChartFactory.createPieChart3D(
            chartTitle,  // chart title
            data,                   // data
            true,                   // include legend
            true,
            false
        );


        TextTitle title = new TextTitle();
        title.setPaint(Color.white);
        title.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        title.setText(chartTitle);
        chart.setTitle(title);
        chart.setBackgroundPaint(Color.BLACK);
        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(270);
        plot.setDirection(Rotation.ANTICLOCKWISE);
        plot.setForegroundAlpha(0.60f);
        plot.setBackgroundPaint(Color.black);
        plot.setBackgroundImageAlpha(0.6f);
        plot.setLabelLinkPaint(Color.white);
        //plot.setInteriorGap(0.33);
        
        return chart;
	}
}