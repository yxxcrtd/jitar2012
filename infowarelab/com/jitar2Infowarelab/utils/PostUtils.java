package com.jitar2Infowarelab.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

/**
 * 
 * POST到service url   
 * @author dell
 *
 */
public class PostUtils {
	    public static String getResponsePostedXML(String serviceUrl,String postXml){
	    	String responseXml = null;
			try {
				responseXml = retrieveResponseFromServerByPost(new URL(serviceUrl),postXml);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return responseXml;
	    }
	    
	    public static String retrieveResponseFromServerByPost(final URL serviceUrl, final String postXml) {
	        HttpURLConnection conn = null;
	        try {
	            conn = (HttpURLConnection) serviceUrl.openConnection();
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "text/xml"); 
	            conn.setRequestProperty("Content-Length", Integer.toString(postXml.length()));
	            conn.setUseCaches(false);
	            conn.setDoInput(true);
	            conn.setDoOutput(true);
	            
	            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	            out.write(postXml);
	            out.flush();
	            out.close();
	            
	            //final DataOutputStream out = new DataOutputStream(conn.getOutputStream(),"UTF-8");
	            //out.writeBytes(postXml);
	            //out.flush();
	            //out.close();
	            
	            final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
	            final StringBuilder buffer = new StringBuilder(512);

	            String line;
	            while ((line = in.readLine()) != null) {
	                buffer.append(line);
	            }
	            return buffer.toString();
	        } catch (final IOException e) {
	            throw new RuntimeException(e);       
	        } finally {
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }
	    }
}
