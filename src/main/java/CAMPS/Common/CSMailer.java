package CAMPS.Common;

import CAMPS.Connect.DBConnect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CSMailer {

    public String sendEmail() {
        DBConnect db = new DBConnect();
       
        try {
            db.getConnection();
            ResourceBundle rb = ResourceBundle.getBundle("CAMPS.properties.google");
            return sendRequest(rb.getString("mailer_url"),rb.getString("mailer_clientId"),rb.getString("mailer_client_secert"),"rv.nataraj@gmail.com,senthilprabu005@gmail.com","tsfgd","sjdgsj");
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {

            }
        }
//        return "";
    }
    
    private static String sendRequest(String apiurl,String clientId,String clientSecert,String mailIds,String subject,String message){
        try{
        	boolean redirect = false;

            URL url = new URL(apiurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String payload = "clientSecert="+clientSecert+"&clientId="+clientId+"&mailId="+mailIds+"&subject="+subject +"&message="+message;// This should be your json body i.e. {"Name" : "Mohsin"} 
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(payload);
            writer.flush();

            int status = conn.getResponseCode();
        	if (status != HttpURLConnection.HTTP_OK) {
        		if (status == HttpURLConnection.HTTP_MOVED_TEMP
        			|| status == HttpURLConnection.HTTP_MOVED_PERM
        				|| status == HttpURLConnection.HTTP_SEE_OTHER)
        		redirect = true;
        	}


        	if (redirect) {

        		String newUrl = conn.getHeaderField("Location");
        		String cookies = conn.getHeaderField("Set-Cookie");
        		conn = (HttpURLConnection) new URL(newUrl).openConnection();
        		conn.setRequestProperty("Cookie", cookies);
        		conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
        		conn.addRequestProperty("User-Agent", "Mozilla");
        		conn.addRequestProperty("Referer", "google.com");

        	}

        	BufferedReader in = new BufferedReader(
                                      new InputStreamReader(conn.getInputStream()));
        	String inputLine;
        	StringBuffer html = new StringBuffer();

        	while ((inputLine = in.readLine()) != null) {
        		html.append(inputLine);
        	}
        	in.close();
        	
        	conn.disconnect();
        	return html.toString();
    	 
        }catch (Exception e){
            //System.out.println(e);
            //System.out.println("Failed successfully");
            return e.toString();
        }
    }
}
