package viper.ui.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import viper.ui.main.StoredPreferences;

public class FileServerConnector implements StoredPreferences
{
	private static String setupURL = "http://192.168.180.128:8080/ISPJ/Setup";
	private static String userID = PREF.get(USERID, null);
	
	public static boolean deleteFile(String fileID)
	{
		userID = PREF.get(USERID, null);
		
		boolean successful = false;
		
		try
		{
			String serverURL = "http://192.168.180.128:8080/ISPJ/Delete";
			
			HttpPost httpPost = new HttpPost(setupURL) ;
			
			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
			
			list.add(new BasicNameValuePair("userID", userID));
			
			list.add(new BasicNameValuePair("fileID", fileID));
			
			httpPost.setEntity(new UrlEncodedFormEntity(list));
			
			executeRequest (httpPost);
			
			httpPost = new HttpPost(serverURL);
			
			executeRequest(httpPost);
			
			successful = true;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return successful;
	}
	
	public static boolean downloadFile(String fileID, String fileName, String locationToBePut)
	{
		userID = PREF.get(USERID, null);
		
		boolean successful = false;
		
		try
		{	
			String serverURL = "http://192.168.180.128:8080/ISPJ/Download";
			
			HttpPost httpPost = new HttpPost(setupURL) ;
			
			HttpGet httpGet = new HttpGet(serverURL);
			
			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
			
			list.add(new BasicNameValuePair("userID", userID));
			
			list.add(new BasicNameValuePair("fileID", fileID));
			
			httpPost.setEntity(new UrlEncodedFormEntity(list));
			
			executeRequest (httpPost);
			
			executeRequest (httpGet, locationToBePut + "\\" + fileName);
			
			successful = true;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return successful;
	}
	
	public static boolean uploadFile(File file)
	{
		userID = PREF.get(USERID, null);
		
		boolean successful = false;
		
		try
		{	
			String serverURL = "http://192.168.180.128:8080/ISPJ/Upload";
			
			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
			
			list.add(new BasicNameValuePair("userID", userID));
			
			MultipartEntity params = new MultipartEntity();
			
			file = new File(file.getAbsolutePath());
			
			FileBody fileBody = new FileBody(file, "application/octect-stream");
			
			params.addPart("file", fileBody);
			
			HttpPost httpPost = new HttpPost(setupURL) ;
			
			httpPost.setEntity(new UrlEncodedFormEntity(list));
			
			executeRequest (httpPost);
			
			httpPost = new HttpPost(serverURL) ;
			
			httpPost.setEntity(params) ;
			
			executeRequest (httpPost);
			
			successful = true;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return successful;
	}
	
	private static void executeRequest(HttpRequestBase requestBase) throws IOException, UnsupportedEncodingException{
		userID = PREF.get(USERID, null);
		
		InputStream responseInputStream = null ;
        HttpClient httpClient = new DefaultHttpClient() ;
        
        try
        {   
        	httpClient.execute(requestBase) ;
        } 
        
        catch (UnsupportedEncodingException e)
        {
            throw e;
        } 
        
        finally{
            if (responseInputStream != null){               
                responseInputStream.close() ;          
                httpClient.getConnectionManager().shutdown() ;
            }
        }
        
    }
	
	private static void executeRequest(HttpRequestBase requestBase, String fileToBeDownloaded) throws IOException, UnsupportedEncodingException{

		
		InputStream responseInputStream = null ;
        HttpClient httpClient = new DefaultHttpClient() ;
        HttpResponse response;
        HttpEntity entity;
        
        File file = new File(fileToBeDownloaded);
        
        FileOutputStream fos = new FileOutputStream(file);
        
        try
        {
            response = httpClient.execute(requestBase) ;
            entity = response.getEntity();
            responseInputStream = entity.getContent();
            
            fos = new FileOutputStream(file);
            
            byte[] buffer = new byte[4096];
            int length;
            
            while((length = responseInputStream.read(buffer)) != -1)
            {
            	fos.write(buffer, 0, length);
            }
        } 
        
        catch (UnsupportedEncodingException e)
        {
            throw e;
        } 
        
        finally{
            if (responseInputStream != null)
            {
                responseInputStream.close();
                fos.flush();
                fos.close();
                httpClient.getConnectionManager().shutdown() ;
            }
        }
        
    }
}