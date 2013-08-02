package viper.ui.watermark;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.TextPainter;
import com.gif4j.Watermark;

import viper.db.SecureDatabaseConnector;
import viper.ui.file.FileServerConnector;
import viper.ui.main.StoredPreferences;

public class WatermarkController implements StoredPreferences
{
	private WatermarkTableModel wtm;
	
	//Holds the names of the file that are to be used for upload and download operations
	private Object[][] storageList;
	private String[] idList;
	private final String LOCATION = "C:/Windows/Temp/";
	private SecureDatabaseConnector sdc = new SecureDatabaseConnector();
	private String userID;
	
	public WatermarkController()
	{
		super();
		wtm = new WatermarkTableModel();
		userID = PREF.get(USERID, null);
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
	
	//Return new versions of the table model for general usage
	public WatermarkTableModel getDefaultForStorage()
	{
		return new WatermarkTableModel();
	}
	
	public WatermarkTableModel getCustomForStorage()
	{
		//Returns default if custom is empty.
		if(wtm == null)
		{
			return getDefaultForStorage();
		}
		
		return wtm;
	}
	
	public boolean loadStorageList()
	{
		boolean successful = true;
		String sqlCommand = "SELECT *, COUNT(*) AS num " +
							"FROM filerecord " +
							"WHERE UserID = ? " +
							"AND " +
							"File_Type = ? " +
							"AND " +
							"Watermarked = ?";
		
		String[] values = new String[]{userID, ".gif", "0"};
		
		try
		{
			sdc.connectToDatabase();
			
			sdc.setPreparedStatement(sqlCommand, values);
			
			sdc.performReadAction();
			
			 sqlCommand = "SELECT * " +
				"FROM filerecord " +
				"WHERE UserID = ? " +
				"AND " +
				"File_Type = ? " +
				"AND " +
				"Watermarked = ?";
			
			ResultSet rs = sdc.getResultSet();
			
			int count;
			
			if(rs.next())
			{
				count = rs.getInt("num");
			}
			
			else
			{
				count = 0;
			}
			
			int counter = 0;
			
			if(count != 0)
			{
				sdc.setPreparedStatement(sqlCommand, values);
				
				sdc.performReadAction();
				
			    rs = sdc.getResultSet();
				
				rs.next();
				
				storageList = new String[count][2];
				
				idList = new String[count];
				
				do
				{
					storageList[counter][0] = rs.getString("File_Name");
					idList[counter] = rs.getString("File_ID");
					counter++;
				}
				while(rs.next());
				
				wtm = new WatermarkTableModel(storageList);
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
							  "UserID = ? " +
							  "AND " +
							  "File_Type = ? " +
							  "Watermarked = ?";
		
		String[] values = {fileName, "2", ".gif", "1"};
		
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
		
	public boolean watermarkedImg(String fileID, String fileName, String text){
		
		boolean successful = false;
		
		GifImage gifImage = null;
		GifImage result = null;
		
		File tempFile = null;
		File file = null;
		
		try {
			successful = FileServerConnector.downloadFile(fileID, fileName, LOCATION);
		
			String newName = checkName(fileName);
		
			//input file path
			tempFile = new File(LOCATION + "/" + fileName);
		
			//output file path
			file = new File(LOCATION + "/" + newName);
		
		
			gifImage = GifDecoder.decode(tempFile);
		
		
			//Watermark image file here
			result = addTextWatermarkToGifImage(gifImage, text);
		
			GifEncoder.encode(result, file);
		
			successful = FileServerConnector.uploadFile(file);
			
			if(successful)
			{
				setEntry(newName);
			}
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			file.delete();
		
			tempFile.delete();
		}
		
		return successful;
	}
	
	private void setEntry(String fileName)
	{
		try
		{
			sdc.connectToDatabase();
			
			String sqlStatement = "UPDATE FileRecord " +
								  "SET Watermarked = ? " +
								  "WHERE File_Name = ?";
			
			String values[] = new String[]{"1", fileName};
			
			sdc.setPreparedStatement(sqlStatement, values);
			
			sdc.performWriteAction();
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				sdc.closeConnection();
			}
			
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private String checkName(String fileName)
	{
		String sqlCommand = "SELECT * " +
							"FROM FileRecord " +
							"WHERE File_Name = ? " +
							"AND " +
							"UserID = ? " +
							"ORDER BY File_Name DESC";
		
		String[] values = new String[]{fileName + "_*", "2"};
		int append = 2;
		String temp = null;
		try 
		{
			sdc.connectToDatabase();
			
			sdc.setPreparedStatement(sqlCommand, values);
			
			sdc.performReadAction();
			
			ResultSet rs = sdc.getResultSet();
			
			if(rs.next())
			{
				rs.first();
				temp = rs.getString("File_Name");
			}
		} 
		
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		{
			try {
				sdc.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		String name = null;
		
		
			char check = '.';
		
			for(int i = fileName.length() - 1; i >= 0; i--)
			{
				if(check == fileName.charAt(i))
				{
					name = fileName.substring(0,i);	
				}
			}
			
		if(temp != null)
		{
			for(int i = temp.length() - 1; i >= 0; i--)
			{
				if(check == temp.charAt(i))
				{
					append = Integer.parseInt(temp.substring(i - 1,i));
					
					append++;
				}
			}
		}
		
		name += "_";
		
		name += append;
		
		name += ".gif";
		
		return name;
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
