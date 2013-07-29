package viper.entity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import viper.db.DBController;
import viper.ui.main.StoredPreferences;

public class User implements StoredPreferences {
	private User user;

	private String userId;
	private String userName;
	private String userPassword;
	private String userRealName;
	private String userBio;
	private String userImagePath;
	private String userFaceRecPath;
	private String userMetadataPath;
	private String userEmail;
	private int userContactNo;
	private String userCompany;
	private String userCountry;
	private String userAddress;
	private String userRole; // 0: Admin 1: User
	private boolean userSSLSetting; // true: ON false: OFF
	private boolean userFaceRegSetting; // true: ON false: OFF
	private boolean userProfileSetting; // true: private false: public
	private boolean userSuspended;

	private static Connection con;
	private static PreparedStatement pstmt;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserBio() {
		return userBio;
	}

	public void setUserBio(String userBio) {
		this.userBio = userBio;
	}

	public String getUserImagePath() {
		return userImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
	}
	
	public String getUserFaceRecPath() {
		return userFaceRecPath;
	}

	public void setUserFaceRecPath(String userFaceRecPath) {
		this.userFaceRecPath = userFaceRecPath;
	}
	
	public String getUserMetadataPath() {
		return userMetadataPath;
	}

	public void setUserMetadataPath(String userMetadataPath) {
		this.userMetadataPath = userMetadataPath;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getUserContactNo() {
		return userContactNo;
	}

	public void setUserContactNo(int userContactNo) {
		this.userContactNo = userContactNo;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isUserSSLSetting() {
		return userSSLSetting;
	}

	public void setUserSSLSetting(boolean userSSLSetting) {
		this.userSSLSetting = userSSLSetting;
	}

	public boolean isUserFaceRegSetting() {
		return userFaceRegSetting;
	}

	public void setUserFaceRegSetting(boolean userFaceRegSetting) {
		this.userFaceRegSetting = userFaceRegSetting;
	}

	public boolean isUserProfileSetting() {
		return userProfileSetting;
	}

	public void setUserProfileSetting(boolean userProfileSetting) {
		this.userProfileSetting = userProfileSetting;
	}

	public boolean isUserSuspended() {
		return userSuspended;
	}

	public void setUserSuspended(boolean userSuspended) {
		this.userSuspended = userSuspended;
	}

	public User() {

	}

	public User(String userName, String userEmail, String userPassword) {
		this.userId = User.generateUUID();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userImagePath = "/viper/image/user/profile/image.jpg";
		this.userEmail = userEmail;
		this.userRole = "1";
		this.userSSLSetting = false;
		this.userFaceRegSetting = false;
		this.userProfileSetting = false;
		this.userSuspended = false;
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static String hashPassword(char[] plaintextArray) {
		String plaintext = new String(plaintextArray);
		String cipher;
		MessageDigest m = null;

		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.reset();
		m.update(plaintext.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		cipher = bigInt.toString(16);

		return cipher;
	}

	public boolean validateUserName(String userName) {
		ResultSet rs = null;
		String sql;
		boolean available = true;

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT * FROM user WHERE `userName` = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userName);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					available = false;
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return available;
	}

	public void createUser() {
		String sql;

		try {
			con = DBController.getConnection();

			try {
				sql = "INSERT INTO user(`userId`, `userName`, `userPassword`, `userImagePath`, `userEmail`, " +
						"`userRole`, `userSSLSetting`, `userFaceRegSetting`, `userProfileSetting`, `userSuspended`) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, userId);
				pstmt.setString(2, userName);
				pstmt.setString(3, userPassword);
				pstmt.setString(4, userImagePath);
				pstmt.setString(5, userEmail);
				pstmt.setString(6, userRole);
				pstmt.setInt(7, userSSLSetting ? 1 : 0);
				pstmt.setInt(8, userFaceRegSetting ? 1 : 0);
				pstmt.setInt(9, userProfileSetting ? 1 : 0);
				pstmt.setInt(10, userSuspended ? 1 : 0);

				pstmt.executeUpdate();
				pstmt.close();
				con.close();
			} catch (SQLException s) {
				s.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean verifyUsername(String userName) {
		ResultSet rs = null;
		String sql;

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT * FROM user WHERE `userName` = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userName);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					return false;		// username is taken
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;					// username is available
	}

	public static User authenticateUser(String userName, String userPassword) {
		ResultSet rs = null;
		String sql;
		User user = null;
		
		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT * FROM user WHERE `userName` = ? AND `userPassword` = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userName);
				pstmt.setString(2, userPassword);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					user = new User();
					user.setUserId(rs.getString("userId"));
					user.setUserName(rs.getString("userName"));
					user.setUserPassword(rs.getString("userPassword"));
					user.setUserRealName(rs.getString("userRealName"));
					user.setUserBio(rs.getString("userBio"));
					user.setUserImagePath(rs.getString("userImagePath"));
					user.setUserFaceRecPath(rs.getString("userFaceRecPath"));
					user.setUserEmail(rs.getString("userEmail"));
					user.setUserContactNo(rs.getInt("userContactNo"));
					user.setUserCompany(rs.getString("userCompany"));
					user.setUserCountry(rs.getString("userCountry"));
					user.setUserAddress(rs.getString("userAddress"));
					user.setUserRole(rs.getString("userRole"));
					user.setUserSSLSetting(rs.getBoolean("userSSlSetting"));
					user.setUserFaceRegSetting(rs
							.getBoolean("userFaceRegSetting"));
					user.setUserProfileSetting(rs
							.getBoolean("userProfileSetting"));
					user.setUserSuspended(rs.getBoolean("userSuspended"));
					
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static User retrieveUser(String userId) {
		ResultSet rs = null;
		String sql;
		User user = null;
		
		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT * FROM user WHERE `userId` = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userId);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					user = new User();
					user.setUserId(rs.getString("userId"));
					user.setUserName(rs.getString("userName"));
					user.setUserPassword(rs.getString("userPassword"));
					user.setUserRealName(rs.getString("userRealName"));
					user.setUserBio(rs.getString("userBio"));
					user.setUserImagePath(rs.getString("userImagePath"));
					user.setUserFaceRecPath(rs.getString("userFaceRecPath"));
					user.setUserMetadataPath(rs.getString("userMetadataPath"));
					user.setUserEmail(rs.getString("userEmail"));
					user.setUserContactNo(rs.getInt("userContactNo"));
					user.setUserCompany(rs.getString("userCompany"));
					user.setUserCountry(rs.getString("userCountry"));
					user.setUserAddress(rs.getString("userAddress"));
					user.setUserRole(rs.getString("userRole"));
					user.setUserSSLSetting(rs.getBoolean("userSSlSetting"));
					user.setUserFaceRegSetting(rs
							.getBoolean("userFaceRegSetting"));
					user.setUserProfileSetting(rs
							.getBoolean("userProfileSetting"));
					user.setUserSuspended(rs.getBoolean("userSuspended"));
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void updateUser() {
		String sql;

		try {
			con = DBController.getConnection();

			try {
				sql = "UPDATE user " +
						"SET `userEmail`=?, `userRealName`=?, `userBio`=?, `userContactNo`=?, " +
						"`userCompany`=?, `userCountry`=?, `userAddress`=?, `userImagePath`=?, " +
						"`userFaceRecPath`=?, `userSSLSetting`=?, `userFaceRegSetting`=?, " +
						"`userProfileSetting`=? WHERE `userId`=?;";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, userEmail);
				pstmt.setString(2, userRealName);
				pstmt.setString(3, userBio);
				pstmt.setInt(4, userContactNo);
				pstmt.setString(5, userCompany);
				pstmt.setString(6, userCountry);
				pstmt.setString(7, userAddress);
				pstmt.setString(8, userImagePath);
				pstmt.setString(9, userFaceRecPath);
				pstmt.setInt(10, userSSLSetting ? 1 : 0);
				pstmt.setInt(11, userFaceRegSetting ? 1 : 0);
				pstmt.setInt(12, userProfileSetting ? 1 : 0);
				pstmt.setString(13, userId);

				pstmt.executeUpdate();
				pstmt.close();
				con.close();
			} catch (SQLException s) {
				s.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateUser(String userMetadataPath) {
		String sql;

		try {
			con = DBController.getConnection();

			try {
				sql = "UPDATE user SET " +
						"`userMetadataPath`=? WHERE `userId`=?;";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, userMetadataPath);
				pstmt.setString(2, PREF.get(USERID, null));

				pstmt.executeUpdate();
				pstmt.close();
				con.close();
			} catch (SQLException s) {
				s.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
