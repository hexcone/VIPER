/*
 * Class Name: FileTableModelForStorage
 * Package: viper.entity
 * 
 * Creator: 
 * 		Name: Toh Jian Hao
 * 		Project Group: Malware Analysis, Project Group 2
 * 		Role: Programmer
 * 
 * Created on: 28/5/2013, 14:34
 * 
 * Purpose: Serve as the table model for file storage
 */

package viper.ui.file;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class FileTableModelForStorage extends AbstractTableModel
{
	private String[] columnNames = {"File Name", "File Type"};
	private Object[][] data = {{"File Name", "File Type"}};
	private TableColumn column = null;
	
	public FileTableModelForStorage()
	{
		
	}
	
	public FileTableModelForStorage(Object[][] data)
	{
		super();
		this.data = data;
	}
	
	@Override
	public int getColumnCount()
	{
		return columnNames.length;
	}
	
	public String getColumnName(int col)
	{
        return columnNames[col].toString();
    }


	@Override
	public int getRowCount()
	{
		return data.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return data[arg0][arg1];
	}
	
}