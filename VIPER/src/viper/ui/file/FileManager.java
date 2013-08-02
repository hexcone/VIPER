/*
 * Class Name: FileManager
 * Package: panel
 * 
 * Creator: 
 * 		Name: Toh Jian Hao
 * 		Project Group: Malware Analysis, Project Group 2
 * 		Role: Programmer
 * 
 * Created on: 26/5/2013, 12:01
 * 
 * Purpose: Serve the user interface to enable users to see there files in storage and to upload, download
 * 			and delete files from storage.
 */

package viper.ui.file;

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
import viper.entity.Logger;
import viper.entity.TrackedPanel;
import viper.ui.main.StoredPreferences;

import javax.swing.JButton;
import java.awt.Point;
import java.io.File;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.Rectangle;

public class FileManager extends TrackedPanel
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
	private FileManagerController fmc = null;  //  @jve:decl-index=0:
	
	//File chooser
	private JFileChooser fileChooser = new JFileChooser();
	private JButton upload = null;
	private JTextField selected = null;
	private JButton download = null;
	private JButton Delete = null;
	
	//Set the frame so that the panel can call it
	public FileManager(JFrame frame)
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
		jLabelBackground = new JLabel();
		jLabelBackground.setBounds(0, 0, 1920, 1200);
		jLabelBackground.setIcon(new ImageIcon(getClass().getResource("/viper/image/main/background.png")));
		CurrentView = new JLabel();
		CurrentView.setFont(new Font("Times New Roman", Font.BOLD, 18));
		CurrentView.setText("Files in Storage");
		fmc = new FileManagerController();
		fmc.loadStorageList();
		this.setSize(1000,700);
		this.setLayout(null);
		this.add(getFilesInStorageContainer(), null);
		this.add(getUpload(), null);
		this.add(getDownload(), null);
		this.add(getDelete(), null);
		this.add(CurrentView, null);
		this.add(getSelected(), null);
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
			
			//Sets File Type Column width
			column = FilesInStorageView.getColumnModel().getColumn(1);
			column.setPreferredWidth(150);
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
		
		//Sets File Type Column width
		column = FilesInStorageView.getColumnModel().getColumn(1);
		column.setPreferredWidth(150);
		
		FilesInStorageContainer.setViewportView(FilesInStorageView);
	}

	/**
	 * This method initializes upload	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getUpload() {
		if (upload == null) {
			upload = new JButton();
			upload.setSize(new Dimension(200, 25));
			upload.setText("Upload Files");
			upload.setLocation(new Point(125, 450));
			upload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int returnVal = -1;
					
					boolean success = false;
					
						fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						returnVal = fileChooser.showOpenDialog(FileManager.this);
					
					if(returnVal == JFileChooser.APPROVE_OPTION)
					{
							File file = fileChooser.getSelectedFile();
						
							if(fmc.checkFiles(file.getName()))
							{
								String [] options = new String[]{"Override the duplicate file", "Cancel Operations"};
							
								returnVal = JOptionPane.showOptionDialog(mainFrame,
										"An existing copy of " + fileName + " exists in the database.\n\nPlease choose to...\nOverride the stored copy.\n\nCancel operations",
										"Duplicate File",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE,
										null,
										options,
										options[1]);
							}
							
								if(returnVal != JOptionPane.NO_OPTION)
								{
									success = fmc.uploadFiles(file);
							
									if(success)
									{
										JOptionPane.showMessageDialog(mainFrame,
												"Successfully uploaded file " + file.getName(),
												"Success",
												JOptionPane.INFORMATION_MESSAGE);
									}
						
									else
									{
										JOptionPane.showMessageDialog(mainFrame,
												"Failed to uploaded file " + file.getName(),
												"Failure",
												JOptionPane.ERROR_MESSAGE);
									}
								
									refreshFilesInStorageView();
								}
						
					}		
				}
			});
		}
		return upload;
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
	 * This method initializes download	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDownload() {
		if (download == null) {
			download = new JButton();
			download.setLocation(new Point(125, 500));
			download.setText("Download File");
			download.setSize(new Dimension(200, 25));
			download.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if((fileName == null) || (fileID == null))
					{
						JOptionPane.showMessageDialog(mainFrame,
							    "Please select a file to upload",
							    "Select File",
							    JOptionPane.PLAIN_MESSAGE);
					}
					
					else
					{
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						
						int returnVal = fileChooser.showOpenDialog(FileManager.this);
						
						if(returnVal == JFileChooser.APPROVE_OPTION)
						{
							File file = fileChooser.getSelectedFile();
							
							boolean success = fmc.downloadFile(fileID, fileName, file.getAbsolutePath());
							
							if(success)
							{
								JOptionPane.showMessageDialog(mainFrame,
									    "Successfully downloaded file " + fileName + " into directory " + file.getName(),
									    "Success",
									    JOptionPane.INFORMATION_MESSAGE);
							}
							
							else
							{
								JOptionPane.showMessageDialog(mainFrame,
									    "Failed to uploaded file " + fileName,
									    "Failure",
									    JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			});
		}
		return download;
	}

	/**
	 * This method initializes Delete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDelete() {
		if (Delete == null) {
			Delete = new JButton();
			Delete.setText("Delete File");
			Delete.setSize(new Dimension(200, 25));
			Delete.setLocation(new Point(125, 550));
			Delete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if((fileName == null) || (fileID == null))
					{
						JOptionPane.showMessageDialog(mainFrame,
							    "Please select a file to delete",
							    "Select File",
							    JOptionPane.PLAIN_MESSAGE);
					}
					
					else
					{
						int returnVal = JOptionPane.showConfirmDialog(mainFrame,
							    "Do you wish to delete file " + fileName + "from system storage?\nThis cannot be undone!",
							    "Select File",
							    JOptionPane.YES_NO_OPTION);
						
							if(returnVal ==JOptionPane.YES_OPTION)
							{
								if(fmc.deleteFile(fileID, fileName))
								{
									JOptionPane.showMessageDialog(mainFrame,
											"Successfully deleted file from system storage",
											"Successful",
											JOptionPane.INFORMATION_MESSAGE);
								}
							
								else
								{
									JOptionPane.showMessageDialog(mainFrame,
									    "Failed to uploaded file " + fileName,
									    " Failure",
									    JOptionPane.ERROR_MESSAGE);
								}
							}
							
							refreshFilesInStorageView();
						}
				}
			});
		}
		return Delete;
	}
	
}
