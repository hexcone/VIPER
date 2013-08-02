package viper.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import viper.ui.main.StoredPreferences;

public class DBController implements StoredPreferences {
	private static Connection con;
	//private static String userid = "root", password = "root";
	private static String userid = "root", password = "MariahFarah";
	//private static String url = "jdbc:mysql://localhost:3306/ISFCS-db";
	private static String url = "jdbc:mysql://192.168.180.128:3306/isfcs-db";
	private static PreparedStatement pstmt;

	public static Connection getConnection() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");

		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			System.out.println("SSL Enabled: " + PREF.get("SSL", "false"));
			if (PREF.get("SSL", "false").equals("true")) {
				con = DriverManager.getConnection(url
						+ "?verifyServerCertificate=false" + "&useSSL=true"
						+ "&requireSSL=true", userid, password);

				System.setProperty("javax.net.ssl.keyStore",
						"C:/mysql/certs/ca-cert.pem");
				System.setProperty("javax.net.ssl.keyStorePassword",
						"C:/mysql/certs/client-cert.pem");
				System.setProperty("javax.net.ssl.trustStore",
						"C:/mysql/certs/client-key.pem");
			} else {
				con = DriverManager.getConnection(url, userid, password);
			}

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return con;
	}
}
