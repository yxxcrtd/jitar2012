package cn.edustar.push;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 孟宪会
 *
 */
public class PushData {
	private static final Logger log = LoggerFactory.getLogger(PushData.class);
	private String errorMessage = "";
	private String returnResult = "";
	
	public boolean Push(String postData, String url) throws Exception {
		log.info("开始推送：数据=" + postData + " 地址：" + url);
		log.info("开始提交数据…… 数据内容：" + postData);		
		this.returnResult = GetResponseData(url, postData);
		return true;
	}

	public String GetResponseData(String url, String postData) {
		log.info("正式开始处理……  ");
		String data = null;
		try {
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Proxy-Connection", "Keep-Alive");
			con.setDoOutput(true);
			con.setDoInput(true);

			OutputStream os = con.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			
			log.info("发送数据……  ");
			dos.write(postData.getBytes());
			dos.flush();
			dos.close();

			InputStream is = con.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			byte d[] = new byte[dis.available()];
			
			log.info("读取结果……  ");
			dis.read(d);
			data = new String(d);
			con.disconnect();
		} catch (Exception ex) {
			log.info("提交数据出现异常……  " + ex.getLocalizedMessage());
			this.setErrorMessage(ex.getLocalizedMessage());
			return "";
		}
		return data;
	}

	public boolean checkServerState(String url) {
		boolean status = false;
		
		try {
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				status = true;
			}

			con.disconnect();
		} catch (Exception ex) {
			this.setErrorMessage(ex.getLocalizedMessage());

		}
		return status;
	}
	
	public String executeGetRequest(String url)
	{
		try {
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String ret;
				InputStream is = con.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				byte d[] = new byte[dis.available()];
				dis.read(d);
				ret = new String(d);				
				con.disconnect();
				return ret;
			}
			else
			{
				con.disconnect();
			}
		} catch (Exception ex) {
			this.setErrorMessage(ex.getLocalizedMessage());
			
		}
		return "";
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}
}
