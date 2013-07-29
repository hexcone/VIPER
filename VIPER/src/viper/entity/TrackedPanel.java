package viper.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import javax.swing.JPanel;


public class TrackedPanel extends JPanel {
	// login
	/*private static int success = 0;
	private static int regFail = 0;
	private static int faceRecFail = 0;*/
	
	// dwell time (page access)
	private static String page = "";
	private static double dwellDuration = 0;
	private static Date dwellStart = null;
	private static Date dwellEnd = null;
	
	// typing speed
	
	public static String getPage() {
		return page;
	}

	public static void setPage(String page) {
		TrackedPanel.page = page;
	}
	
	public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	@Override
	public void removeAll() {
		dwellEnd = new Date();
		dwellDuration = dwellEnd.getTime() - dwellStart.getTime();
		Logger.logAccess(page, dwellDuration);
		
		// reset values
		page = "";
		dwellDuration = 0;
		dwellStart = null;
		dwellEnd = null;
		
		super.removeAll();
	}
	
	public void initialize() {
		setPage(this.getClass().getSimpleName());
		dwellStart = new Date();
	}
	
	
	
}


