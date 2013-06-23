package viper.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBController {
	private static Connection con;
	private static String userid = "root", password = "root";
	private static String url = "jdbc:mysql://localhost:3306/ISFCS-db";
	private static PreparedStatement pstmt;
	
	public static Connection getConnection() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");

		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(url, userid, password);

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return con;
	}
}
