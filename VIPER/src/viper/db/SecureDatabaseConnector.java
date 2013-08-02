package viper.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SecureDatabaseConnector
{
	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	
	public SecureDatabaseConnector()
	{
		
	}
	
	public SecureDatabaseConnector(String databaseUrl)
	{
	}
	
	public ResultSet getResultSet() throws SQLException
	{
		return resultSet;
	}
	
	public void setPreparedStatement(String sqlCommand, String[] values) throws SQLException
	{
		preparedStatement = connection.prepareStatement(sqlCommand);
		
		if(values != null)
		{
			for(int i = 0; i < values.length; i++)
			{
				preparedStatement.setString((i + 1), values[i]);
			}
		}
	}
	
	public void performReadAction() throws SQLException
	{
		resultSet = preparedStatement.executeQuery();
	}
	
	public void performWriteAction() throws SQLException
	{	
		preparedStatement.executeUpdate();
	}
	
	public void connectToDatabase() throws SQLException
	{
		connection = DBController.getConnection();
	}
	
	public void closeConnection() throws SQLException
	{
		if(resultSet != null)
		{
			resultSet.close();
		}
		
		if(preparedStatement != null)
		{
			preparedStatement.close();
		}
		
		if(connection != null)
		{
			connection.close();
		}
	}
}
