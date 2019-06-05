package cn.edustar.usermgr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.jitar.util.CommonUtil;

import com.alibaba.fastjson.JSON;

/**
 * CAS统一用户接口
 */
public class Client {
	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	private BaseUser baseUser = new BaseUser();
	private String userMgrUrl;

	public Client() {
		// 
	}
	
	// 获取用户总数
	public int getUserCounts() {
		int userCounts = 0;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 请求超时60秒
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/getUserCounts");
			//System.out.println("（获取用户总数）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = "0";
			try {
				// 读取超时60秒
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（获取用户总数）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			if(!CommonUtil.isInteger(responseBody)) responseBody = "0";
			userCounts = Integer.valueOf(responseBody);
			//System.out.println("（获取用户总数）返回的数据：" + userCounts);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return userCounts;
	}

	// 根据用户名修改用户密码
	public String updatePasswordByUsername(String username, String password, String newPassword) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/updatePasswordByUsername?username=" + username + "&password=" + password + "&newPassword=" + newPassword);
			//System.out.println("（根据用户名修改用户密码）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（根据用户名修改用户密码）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			//System.out.println("（根据用户名修改用户密码）返回的数据：" + responseBody);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	// 根据用户名重置用户密码
	public String resetPasswordByUsername(String username, String password) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/resetPasswordByUsername?username=" + username + "&password=" + password);
			logger.info("（根据用户名重置用户密码）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（根据用户名重置用户密码）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			logger.info("（根据用户名重置用户密码）返回的数据：" + responseBody);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	// 配置表中根据key修改value
	public String updateValueByKey(String key, String value) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/updateValueByKey?key=" + key + "&value=" + value);
			logger.info("（配置表中根据key修改value）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（配置表中根据key修改value）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			logger.info("（配置表中根据key修改value）返回的数据：" + responseBody);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	// 根据用户名得到用户对象
	public BaseUser getUserByUsername(String username) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/getUserByUsername?username=" + username);
			logger.info("（根据用户名得到用户对象）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（根据用户名得到用户对象）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			logger.info("（根据用户名得到用户对象）返回的数据：" + responseBody);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		//BaseUser baseUser = new BaseUser();
		baseUser.setId((Integer) JSON.parseObject(responseBody).getJSONObject("user").get("id"));
		baseUser.setGuid((String) JSON.parseObject(responseBody).getJSONObject("user").get("guid"));
		baseUser.setUsername(username);
		baseUser.setTrueName((String) JSON.parseObject(responseBody).getJSONObject("user").get("trueName"));
		baseUser.setPassword((String) JSON.parseObject(responseBody).getJSONObject("user").get("password"));
		baseUser.setEmail((String) JSON.parseObject(responseBody).getJSONObject("user").get("email"));
		baseUser.setCreateDate(new Date((Long) JSON.parseObject(responseBody).getJSONObject("user").get("createDate")));
		baseUser.setStatus((Integer) JSON.parseObject(responseBody).getJSONObject("user").get("status"));
		baseUser.setLastLoginIp((String) JSON.parseObject(responseBody).getJSONObject("user").get("lastLoginIp"));
		baseUser.setLastLoginTime(new Date((Long) JSON.parseObject(responseBody).getJSONObject("user").get("lastLoginTime")));
		baseUser.setCurrentLoginIp((String) JSON.parseObject(responseBody).getJSONObject("user").get("currentLoginIp"));
		baseUser.setCurrentLoginTime(new Date((Long) JSON.parseObject(responseBody).getJSONObject("user").get("currentLoginTime")));
		baseUser.setLoginTimes((Integer) JSON.parseObject(responseBody).getJSONObject("user").get("loginTimes"));
		baseUser.setQuestion((String) JSON.parseObject(responseBody).getJSONObject("user").get("question"));
		baseUser.setAnswer((String) JSON.parseObject(responseBody).getJSONObject("user").get("answer"));
		baseUser.setUsn((Integer) JSON.parseObject(responseBody).getJSONObject("user").get("usn"));
		baseUser.setRole((Integer) JSON.parseObject(responseBody).getJSONObject("user").get("role"));
		baseUser.setRoleName((String) JSON.parseObject(responseBody).getJSONObject("user").get("roleName"));
		return baseUser;
	}
	
	// 根据用户名重置用户问题和答案
	public String resetQuestionAndAnswerByUsername(String username, String question, String answer) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/resetQuestionAndAnswerByUsername?username=" + username + "&question=" + URLEncoder.encode(URLEncoder.encode(question, "UTF-8"), "UTF-8") + "&answer=" + URLEncoder.encode(URLEncoder.encode(answer, "UTF-8"), "UTF-8"));
			logger.info("（根据用户名重置用户问题和答案）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（根据用户名重置用户问题和答案）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			logger.info("（根据用户名重置用户问题和答案）返回的数据：" + responseBody);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	// 保存新用户
	public String saveNewBaseUser(BaseUser baseUser) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			logger.info("（保存新用户）请求的数据：" + JSON.toJSONString(baseUser));			
			HttpGet httpGet = new HttpGet(userMgrUrl + "/saveNewBaseUser?json=" + URLEncoder.encode(JSON.toJSONString(baseUser), "UTF-8"));
			//HttpGet httpGet = new HttpGet(userMgrUrl + "/saveNewBaseUser?json=" + URLEncoder.encode(URLEncoder.encode(JSON.toJSONString(baseUser), "UTF-8"), "UTF-8"));
			logger.info("（保存新用户）请求的地址：" + userMgrUrl + "/saveNewBaseUser?json=" + URLEncoder.encode(JSON.toJSONString(baseUser), "UTF-8"));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（保存新用户）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			logger.info("（保存新用户）返回的数据：" + responseBody);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	// 根据用户名删除用户
	public String deleteUser(String username) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/deleteUser?username=" + username);
			logger.info("（根据用户名删除用户）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（根据用户名删除用户）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			logger.info("（根据用户名删除用户）返回的数据：" + responseBody);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	// 根据用户名修改用户状态
	public String updateStatusByUsername(String username, int status) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/updateStatusByUsername?username=" + username + "&status=" + status);
			logger.info("（根据用户名修改用户状态）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（根据用户名修改用户状态）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			logger.info("（根据用户名修改用户状态）返回的数据：" + responseBody);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	// 根据用户名修改用户真实姓名、邮件地址和用户角色
	public String updateUserInfoByUsername(String username, String trueName, String email, int role) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		String responseBody = "";
		try {
			HttpGet httpGet = new HttpGet(userMgrUrl + "/updateUserInfoByUsername?username=" + URLEncoder.encode(URLEncoder.encode(username, "UTF-8"), "UTF-8") + "&trueName=" + URLEncoder.encode(URLEncoder.encode(trueName, "UTF-8"), "UTF-8") + "&email=" + URLEncoder.encode(URLEncoder.encode(email, "UTF-8"), "UTF-8") + "&role=" + role);
			logger.info("（根据用户名修改用户真实姓名、邮件地址和用户角色）请求的地址：" + httpGet);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				// httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
				responseBody = httpClient.execute(httpGet, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("（根据用户名修改用户真实姓名、邮件地址和用户角色）Socket 读超时！请检查CAS连接是否正常！");
				e.printStackTrace();
			}
			logger.info("（根据用户名修改用户真实姓名、邮件地址和用户角色）返回的数据：" + responseBody);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	public BaseUser getBaseUser() {
		return this.baseUser;
	}

	public void setUserMgrUrl(String userMgrUrl) {
		this.userMgrUrl = userMgrUrl;
	}

	public String getUsername() {
		return baseUser.getUsername();
	}
	
}
