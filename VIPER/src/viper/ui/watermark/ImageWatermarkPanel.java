
package viper.ui.watermark;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import javax.swing.JButton;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import viper.entity.TrackedPanel;
import viper.ui.main.StoredPreferences;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.TextPainter;
import com.gif4j.Watermark;

public class ImageWatermarkPanel extends TrackedPanel implements StoredPreferences
{

	private static final long serialVersionUID = 1L;
	private JFrame mainFrame = null;
	private JScrollPane FilesInStorageContainer = null;
	private JTable FilesInStorageView = null;
	private JLabel jLabelBackground;
	private JLabel CurrentView = null;
	private TableColumn column = null;
	private String userID = null;  //  @jve:decl-index=0:
	private String fileName;
	private String fileID;
	
	//Controller to manage back end logic
	private WatermarkController fmc = null;  
	
	//File chooser
	private JFileChooser fileChooser = new JFileChooser();
	private JTextField selected = null;
	private JLabel jLabelTxtWatermark = null;
	private JCheckBox jCheckBoxInsertTxt = null;
	private JLabel jLabelInsertTxt = null;
	private JLabel jLabelWatermarkTxt = null;
	private JTextField jTextFieldWatermarkTxt = null;
	private JButton jButtonWatermark = null;
	private JButton jButtonCompare = null;
	
	//Set the frame so that the panel can call it
	public ImageWatermarkPanel(JFrame frame)
	{
		super();
		mainFrame = frame;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize()
	{
		super.initialize();
		jLabelWatermarkTxt = new JLabel();
		jLabelWatermarkTxt.setText("Add text here: ");
		jLabelWatermarkTxt.setLocation(new Point(200, 520));
		jLabelWatermarkTxt.setForeground(Color.lightGray);
		jLabelWatermarkTxt.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelWatermarkTxt.setSize(new Dimension(120, 25));
		jLabelWatermarkTxt.setVisible(false);
		jLabelInsertTxt = new JLabel();
		jLabelInsertTxt.setText("Insert Text on image ");
		jLabelInsertTxt.setSize(new Dimension(200, 25));
		jLabelInsertTxt.setForeground(Color.lightGray);
		jLabelInsertTxt.setHorizontalTextPosition(SwingConstants.LEADING);
		jLabelInsertTxt.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelInsertTxt.setLocation(new Point(230, 480));
		jLabelTxtWatermark = new JLabel();
		jLabelTxtWatermark.setText("Text Watermark");
		jLabelTxtWatermark.setSize(new Dimension(120, 25));
		jLabelTxtWatermark.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabelTxtWatermark.setForeground(Color.lightGray);
		jLabelTxtWatermark.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTxtWatermark.setLocation(new Point(200, 447));
		userID = "2";
		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource("/viper/image/main/background.png")));
		CurrentView = new JLabel();
		CurrentView.setFont(new Font("Times New Roman", Font.BOLD, 18));
		CurrentView.setText("Files in Storage");
		fmc = new WatermarkController();
		this.setSize(1000,700);
		this.setLayout(null);
		this.add(getFilesInStorageContainer(), null);
		this.add(CurrentView, null);
		this.add(getSelected(), null);
		this.add(jLabelTxtWatermark, null);
		this.add(getJCheckBoxInsertTxt(), null);
		this.add(jLabelInsertTxt, null);
		this.add(jLabelWatermarkTxt, null);
		this.add(getJTextFieldWatermarkTxt(), null);
		this.add(getJButtonWatermark(), null);
		this.add(getJButtonCompare(), null);
		this.add(jLabelBackground, null);
		
	}

	/**
	 * This method initializes FilesInStorageContainer	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getFilesInStorageContainer() 
	{
		if (FilesInStorageContainer == null) 
		{
			FilesInStorageContainer = new JScrollPane();
			FilesInStorageContainer.setSize(new Dimension(700, 250));
			FilesInStorageContainer.setLocation(new Point(150, 115));
			FilesInStorageContainer.setViewportView(getFilesInStorageView());
		}
		return FilesInStorageContainer;
	}

	/**
	 * This method initializes FilesInStorageView	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getFilesInStorageView() 
	{
		if (FilesInStorageView == null)
		{	
			fmc.loadStorageList();
			
			FilesInStorageView = new JTable(fmc.getCustomForStorage());
			FilesInStorageView.setRowSelectionAllowed(true);
			FilesInStorageView.setCellSelectionEnabled(true);
			
			FilesInStorageView.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					int selectedRow = FilesInStorageView.getSelectedRow();
					
					fileID = fmc.getFileID(selectedRow);
					fileName = fmc.getFileName(selectedRow);
					
					String text = (String)fmc.getCustomForStorage().getValueAt(selectedRow, 0);
					
					selected.setText(text);
				}
			});
			
			//Sets File Name Column width
			column = FilesInStorageView.getColumnModel().getColumn(0);
			column.setPreferredWidth(500);
		}
		return FilesInStorageView;
	}

	/**
	 * This method initializes FilesToUploadViewer	
	 * 	
	 * @return javax.swing.JTable	
	 */
	
	/**
	 * This method initializes viewStoredFiles	
	 * 	
	 * @return javax.swing.JButton	
	 */
	
	private void refreshFilesInStorageView()
	{
		fmc.loadStorageList();
		
		FilesInStorageView = new JTable(fmc.getCustomForStorage());
		FilesInStorageView.setRowSelectionAllowed(true);
		
		FilesInStorageView.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int selectedRow = FilesInStorageView.getSelectedRow();
				
				fileID = fmc.getFileID(selectedRow);
				fileName = fmc.getFileName(selectedRow);
				
				String text = (String)fmc.getCustomForStorage().getValueAt(selectedRow, 0);
				
				selected.setText(text);
			}
		});
		
		//Sets File Name Column width
		column = FilesInStorageView.getColumnModel().getColumn(0);
		column.setPreferredWidth(400);
		
		FilesInStorageContainer.setViewportView(FilesInStorageView);
	}

	/**
	 * This method initializes selected	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSelected() {
		if (selected == null) {
			selected = new JTextField();
			selected.setLocation(new Point(125, 390));
			selected.setText("No file Selected");
			selected.setHorizontalAlignment(JTextField.CENTER);
			selected.setEditable(false);
			selected.setSize(new Dimension(500, 35));
		}
		return selected;
	}

	/**
	 * This method initializes jCheckBoxInsertTxt	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxInsertTxt() {
		if (jCheckBoxInsertTxt == null) {
			jCheckBoxInsertTxt = new JCheckBox();
			jCheckBoxInsertTxt.setSize(new Dimension(21, 21));
			jCheckBoxInsertTxt.setLocation(new Point(200, 480));
			jCheckBoxInsertTxt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jCheckBoxInsertTxt.isSelected() == true) {
					jLabelWatermarkTxt.setVisible(true);
					jLabelWatermarkTxt.setEnabled(true);
					jTextFieldWatermarkTxt.setVisible(true);
					jTextFieldWatermarkTxt.setEnabled(true);
					jButtonWatermark.setVisible(true);
					jButtonWatermark.setEnabled(true);
					jButtonCompare.setVisible(true);
					jButtonCompare.setEnabled(true);
					}
				}
			});
		}
		return jCheckBoxInsertTxt;
	}

	/**
	 * This method initializes jTextFieldWatermarkTxt	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldWatermarkTxt() {
		if (jTextFieldWatermarkTxt == null) {
			jTextFieldWatermarkTxt = new JTextField();
			jTextFieldWatermarkTxt.setLocation(new Point(330, 520));
			jTextFieldWatermarkTxt.setSize(new Dimension(180, 30));
			jTextFieldWatermarkTxt.setVisible(false);
		}
		return jTextFieldWatermarkTxt;
	}

	/**
	 * This method initializes jButtonWatermark	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonWatermark() {
		if (jButtonWatermark == null) {
			jButtonWatermark = new JButton();
			jButtonWatermark.setLocation(new Point(350, 580));
			jButtonWatermark.setText("Watermark");
			jButtonWatermark.setSize(new Dimension(200, 25));
			jButtonWatermark.setVisible(false);
			jButtonWatermark.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					String text = jTextFieldWatermarkTxt.getText();
					boolean success = fmc.watermarkedImg(fileID, fileName, text);
				
				if(success)
				{
					JOptionPane.showMessageDialog(mainFrame,
						    "File has been successfully watermarked " + fileName,
						    "Success",
						    JOptionPane.INFORMATION_MESSAGE);
				}
				
				else
				{
					JOptionPane.showMessageDialog(mainFrame,
						    "Failed to watermark file " + fileName,
						    "Failure",
						    JOptionPane.ERROR_MESSAGE);
				}
				
				refreshFilesInStorageView();
				}
			});
		}
		return jButtonWatermark;
	}

	/**
	 * This method initializes jButtonCompare	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCompare() {
		if (jButtonCompare == null) {
			jButtonCompare = new JButton();
			jButtonCompare.setLocation(new Point(350, 620));
			jButtonCompare.setText("Compare");
			jButtonCompare.setSize(new Dimension(200, 25));
			jButtonCompare.setVisible(false);
			jButtonCompare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame frame = new ComparingImg();
					frame.setSize(1000, 700);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					
					JPanel panel = new CompareImgW();
					
					frame.getContentPane().add(panel);
				}
			});
		}
		return jButtonCompare;
	}
	private GifImage addTextWatermarkToGifImage(GifImage gifImage, String text) {
		//create new TextPainter
		TextPainter tp = new TextPainter(new Font("Arial", Font.BOLD,15));
		tp.setOutlinePaint(Color.LIGHT_GRAY);
		
		//render the specified text outlined
		BufferedImage renderedWatermarkText = tp.renderString(text, true);
		
		//create new Watermark
		Watermark watermark = new Watermark(renderedWatermarkText, Watermark.LAYOUT_MIDDLE_CENTER, 0.1f);
		
		//apply watermark to the specified gif image and return the result
		
		return watermark.apply(gifImage, true);
		
	}
}
