package viper.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import viper.db.DBController;
import viper.ui.main.StoredPreferences;

public class Logger implements StoredPreferences {
	// The variables used in the Logger class to log actions
	private static Calendar Time_Of_Event;
	private static String entranceID = null;
	private static String accessID = null;
	private static String IDofUser = null;
	private static String time = null;
	private static String date = null;

	// Personal Database Connector for connecting to the database. You may use
	// your own
	// but you will need to changes the code. Tell me if you want to use another
	// database connector and I will do it for you.
	private static Connection con;
	private static PreparedStatement pstmt;

	// private static SecureDatabaseConnector connector = new
	// SecureDatabaseConnector();
	// Event Categories that are number coded for convince. Use these to
	// determine the
	// event category the log event belongs to in the logAction method. Details
	// are
	// about each are stated in the README.txt file.
	public static int EVENT_CATEGORY_OTHER = 0;
	public static int EVENT_CATEGORY_VIEW = 1;
	public static int EVENT_CATEGORY_CREATE = 2;
	public static int EVENT_CATEGORY_DELETE = 3;
	public static int EVENT_CATEGORY_EDIT = 4;
	public static int EVENT_CATEGORY_MOVE = 5;

	// Use to log the user's login attempts and whether or not they were
	// successful.

	// Parameter username is the user name used to when logging in to the
	// system. Links
	// with the User table in order to determine if the username used is
	// legitimate

	// Parameter outcome determines if the login is successful

	// Parameter description is extra details accompanying the results. Such as
	// explaining why the user failed to login.

	public static void logUserLogin(String userID, boolean outcome,
			String Description) {
		try {
			con = DBController.getConnection();

			String booleanPlaceHolderForOutcome = "0";

			generateCurrentTime();

			if (outcome) {
				booleanPlaceHolderForOutcome = "1";
			}

			String sqlCommand = "INSERT INTO EntranceRecord "
					+ "(Time_Of_Attempt, Date_Of_Attempt,Outcome, Description, userWhoLoggedIn) "
					+ "VALUES (?, ?, ?, ?, ?)";

			System.out.println("Time: " + time);
			System.out.println("Date: " + date);
			System.out.println("booleanPlaceHolderForOutcome: "
					+ booleanPlaceHolderForOutcome);
			System.out.println("Description: " + Description);
			System.out.println("userID: " + userID);

			pstmt = con.prepareStatement(sqlCommand);
			pstmt.setString(1, time);
			pstmt.setString(2, date);
			pstmt.setString(3, booleanPlaceHolderForOutcome);
			pstmt.setString(4, Description);
			pstmt.setString(5, userID);
			pstmt.executeUpdate();

			if (outcome) {
				sqlCommand = "SELECT Entrance_ID FROM EntranceRecord WHERE userWhoLoggedIn = ? AND Time_Of_Attempt = ?";

				pstmt = con.prepareStatement(sqlCommand);
				pstmt.setString(1, userID);
				pstmt.setString(2, time);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					entranceID = rs.getString("Entrance_ID");
					PREF.put(ENTRANCEID, entranceID);
				}

				IDofUser = userID;
			}

		}

		catch (SQLException e) {
			System.out.println("Error when opening");

			e.printStackTrace();
		}

		finally {
			try {
				pstmt.close();
				con.close();
			}

			catch (SQLException e) {
				System.out.println("Error when closing");

				e.printStackTrace();
			}
		}
	}

	public static void logInvalidUsernames(String usernameUsed) {
		String sqlCommand = "INSERT INTO InvalidAttempts "
				+ "(UsernameUsed, Time_Of_Attempt, Date_Of_Attempt) "
				+ "VALUES (?, ?, ?)";

		generateCurrentTime();

		try {
			con = DBController.getConnection();

			pstmt = con.prepareStatement(sqlCommand);
			pstmt.setString(1, usernameUsed);
			pstmt.setString(2, time);
			pstmt.setString(3, date);
			pstmt.executeUpdate();
		}

		catch (SQLException e) {
			System.out.println("Error when opening");

			e.printStackTrace();
		}

		finally {
			try {
				con.close();
			}

			catch (SQLException e) {
				System.out.println("Error when closing");

				e.printStackTrace();
			}
		}
	}

	// Use to log the time the user has logged out from the system
	// irregardless of if they did so willingly or not.
	public static void logUserLogout() {
		entranceID = PREF.get(ENTRANCEID, null);
		generateCurrentTime();
		if (entranceID != null) {
			String sqlCommand = "UPDATE EntranceRecord SET Time_Of_Exit = ?, Date_Of_Exit = ? WHERE Entrance_ID = ?";

			try {
				con = DBController.getConnection();

				pstmt = con.prepareStatement(sqlCommand);
				pstmt.setString(1, time);
				pstmt.setString(2, date);
				pstmt.setString(3, entranceID);
				pstmt.executeUpdate();
			}

			catch (SQLException e) {
				System.out.println("Error when opening");

				e.printStackTrace();
			}

			finally {
				try {
					con.close();
				}

				catch (SQLException e) {
					System.out.println("Error when closing");

					e.printStackTrace();
				}
			}
		}
	}

	// Logs where the parts of the system the user has visited. For example,
	// from the
	// home panel to the another panel. The destination is the name of the panel
	// that
	// the user has visited.
	public static void logAccess(String destination, double duration) {

		entranceID = PREF.get(ENTRANCEID, null);
		generateCurrentTime();

		String sqlCommand = "INSERT INTO AccessingRecord "
				+ "(Time_Of_Accessing, Date_Of_Accessing, Destination, Entrance_ID, userWhoAccessed, duration) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try {
			con = DBController.getConnection();

			pstmt = con.prepareStatement(sqlCommand);
			pstmt.setString(1, time);
			pstmt.setString(2, date);
			pstmt.setString(3, destination);
			pstmt.setString(4, entranceID);
			pstmt.setString(5, PREF.get(USERID, null));
			pstmt.setDouble(6, duration);
			pstmt.executeUpdate();

			sqlCommand = "SELECT Accessing_ID " + "FROM AccessingRecord "
					+ "WHERE Time_Of_Accessing = ? " + "AND Entrance_ID = ?";

			pstmt = con.prepareStatement(sqlCommand);
			pstmt.setString(1, time);
			pstmt.setString(2, entranceID);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				accessID = rs.getString("Accessing_ID");
			}
		}

		catch (SQLException e) {
			System.out.println("Error when opening");

			e.printStackTrace();
		}

		finally {
			try {
				con.close();
			}

			catch (SQLException e) {
				System.out.println("Error when closing");

				e.printStackTrace();
			}
		}
	}

	// Determines what action the user has performed in the system.

	// Parameter EventCateogry takes in a number that corresponds to a specific
	// category
	// use the variables stated above.

	// Parameter Event is made up by you based on what is appropriate.

	// Parameter outcome is whether or not the event has succeeded.

	// Description is extra details you may wish to add. Such as explaining why
	// a
	// particular event has failed.
	public static void logAction(int EventCategory, String Event,
			boolean outcome, String Description) {
		// if (isLogin()) {
		generateCurrentTime();
		String booleanPlaceHolder = "0";

		if (outcome) {
			booleanPlaceHolder = "1";
		}

		String sqlCommand = "INSERT INTO LogRecord "
				+ "(Time_Of_Event, Date_Of_Event, Event_Category, Event, Outcome, Description, Accessing_ID, userWhoPerformAction) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			con = DBController.getConnection();
			pstmt = con.prepareStatement(sqlCommand);

			pstmt.setString(1, time);
			pstmt.setString(2, date);
			pstmt.setString(3, pickEventCategory(EventCategory));
			pstmt.setString(4, Event);
			pstmt.setString(5, booleanPlaceHolder);
			pstmt.setString(6, Description);
			pstmt.setString(7, accessID);
			pstmt.setString(8, PREF.get(USERID, null));

			pstmt.executeUpdate();

		}

		catch (SQLException e) {
			System.out.println("Error when opening");

			e.printStackTrace();
		}

		finally {
			try {
				con.close();
			}

			catch (SQLException e) {
				System.out.println("Error when closing");

				e.printStackTrace();
			}
		}
		// }
	}

	private static boolean isLogin() {
		if (entranceID == null) {
			return false;
		}

		return true;
	}

	private static String pickEventCategory(int chosen) {
		switch (chosen) {
		case 0:
			return "Other";

		case 1:
			return "View";

		case 2:
			return "Create";

		case 3:
			return "Delete";

		case 4:
			return "Edit";

		case 5:
			return "Move";
		}

		return "Other";
	}

	private static void generateCurrentTime() {
		Time_Of_Event = Calendar.getInstance();

		date = Time_Of_Event.get(Calendar.YEAR) + "-"
				+ (Time_Of_Event.get(Calendar.MONTH) + 1) + "-"
				+ Time_Of_Event.get(Calendar.DATE);
		time = Time_Of_Event.get(Calendar.HOUR_OF_DAY) + ":"
				+ Time_Of_Event.get(Calendar.MINUTE) + ":"
				+ Time_Of_Event.get(Calendar.SECOND);
	}
}
