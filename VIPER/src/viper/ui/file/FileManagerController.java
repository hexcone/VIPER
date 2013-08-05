/*
 * Class Name: FileManagerController
 * Package: viper.entity
 * 
 * Creator: 
 * 		Name: Toh Jian Hao
 * 		Project Group: Malware Analysis, Project Group 2
 * 		Role: Programmer
 * 
 * Created on: 28/5/2013, 14:30
 * 
 * Purpose: Serve as the data manager back end for the FileManager UI. Deals with the data and file
 * 			upload and downloads.
 */

package viper.ui.file;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import viper.db.SecureDatabaseConnector;
import viper.entity.Logger;
import viper.ui.main.StoredPreferences;

public class FileManagerController implements StoredPreferences
{
	//Serve as custom versions of the table models
	private FileTableModelForStorage fTMFS = null;
	
	//Holds the names of the file that are to be used for upload and download operations
	private Object[][] storageList;
	private String[] idList;
	
	private String userID ;
	
	private SecureDatabaseConnector sdc = new SecureDatabaseConnector("jdbc:mysql://192.168.180.128:3306/project");
	
	public FileManagerController()
	{
		super();
		fTMFS = new FileTableModelForStorage();
		
		userID = PREF.get(USERID,null);
	}
	
	//Return new versions of the table model for general usage
	public FileTableModelForStorage getDefaultForStorage()
	{
		return new FileTableModelForStorage();
	}
	
	public FileTableModelForStorage getCustomForStorage()
	{
		//Returns default if custom is empty.
		if(fTMFS == null)
		{
			return getDefaultForStorage();
		}
		
		return fTMFS;
	}
	
	public String getFileID(int index)
	{
		if(idList.length != 0)
		{
			return idList[index];
		}
		
		return null;
	}
	
	public String getFileName(int index)
	{
		return (String) storageList[index][0];
	}
	
	public boolean loadStorageList()
	{
		boolean successful = true;
		String sqlCommand = "SELECT *, COUNT(*) AS num " +
							"FROM filerecord " +
							"WHERE UserID = ?";
		
		System.out.println("User ID in loadStorageList: " + userID);
		
		String[] values = new String[]{userID};
		
		try
		{
			sdc.connectToDatabase();
			
			sdc.setPreparedStatement(sqlCommand, values);
			
			sdc.performReadAction();
			
			sqlCommand = "SELECT * " +
			"FROM filerecord " +
			"WHERE UserID = ?";
			
			ResultSet rs = sdc.getResultSet();
			
			rs.next();

			int count = rs.getInt("num");
			
			sdc.setPreparedStatement(sqlCommand, values);
			
			sdc.performReadAction();
			
		    rs = sdc.getResultSet();
			
			rs.next();
			
			int counter = 0;
			
			System.out.println(count);
			
			if(count != 0)
			{
				storageList = new String[count][2];
				
				idList = new String[count];
				
				do
				{
					storageList[counter][0] = rs.getString("File_Name");
					storageList[counter][1] = rs.getString("File_Type");
					idList[counter] = rs.getString("File_ID");
					counter++;
				}
				while(rs.next());
				
				fTMFS = new FileTableModelForStorage(storageList);
			}
			
			else
			{
				fTMFS = new FileTableModelForStorage();
			}
		}
		
		catch(SQLException e)
		{
			successful = false;
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				sdc.closeConnection();
			}
			
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return successful;
	}
	
	public boolean checkFiles(String fileName)
	{
		boolean exist = false;
		
		String sqlCommand = "SELECT * " +
							  "FROM filerecord " +
							  "WHERE File_Name = ? " +
							  "AND " +
							  "UserID = ?";
		
		String[] values = {fileName, PREF.get(USERID, null)};
		
		try
		{
			sdc.connectToDatabase();
			
			sdc.setPreparedStatement(sqlCommand, values);
			
			sdc.performReadAction();
			
			if(sdc.getResultSet().next())
			{
			exist = true;	
			}
		}
		
		catch(SQLException e)
		{
			exist = false;
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				sdc.closeConnection();
			}
			
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return exist;
	}
	
	public boolean uploadFiles(File file)
	{
		boolean successful = FileServerConnector.uploadFile(file);
		
		String message;
		
		if(successful)
		{
			message = "Upload file " + file.getName() + " to the system";
		}
		
		else
		{
			message = "Failed to upload file " + file.getName() + " into the system";
		}
		
		Logger.logAction(Logger.EVENT_CATEGORY_CREATE, "Uploaded file", successful, message);
		
		return successful;
	}
	
	public boolean uploadFiles(File file, String newFileName,String userID)
	{
		boolean successful = FileServerConnector.uploadFile(file);
		
		String message;
		
		if(successful)
		{
			message = "Upload file " + file.getName() + " to the system";
		}
		
		else
		{
			message = "Failed to upload file " + file.getName() + " into the system";
		}
		
		Logger.logAction(Logger.EVENT_CATEGORY_CREATE, "Uploaded file", successful, message);
		
		return successful;
	}
	
	public boolean downloadFile(String fileID, String fileName, String locationToBePut)
	{
		boolean successful = FileServerConnector.downloadFile(fileID, fileName, locationToBePut);
		
		String message;
		
		if(successful)
		{
			message = "Download file " + fileName + " and place it under " + locationToBePut;
		}
		
		else
		{
			message = "Failed to download file " + fileName + " into the system";
		}
		
		Logger.logAction(Logger.EVENT_CATEGORY_MOVE, "Download file", successful, message);
		
		return successful;
	}
	
	public boolean deleteFile(String fileID, String fileName)
	{
		boolean successful = FileServerConnector.deleteFile(fileID);
		
		String message;
		
		if(successful)
		{
			message = "Deleted file " + fileName;
		}
		
		else
		{
			message = "Failed to delete file " + fileName;
		}
		
		Logger.logAction(Logger.EVENT_CATEGORY_DELETE, "Delete file", successful, message);
		
		return successful;
	}
}