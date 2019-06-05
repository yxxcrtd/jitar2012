package com.huawei.eie.api.sm;

import javax.servlet.http.HttpServletRequest;

public class SMS {
	 static DBSMProxy smproxy = null;
	  public SMS()
	  {
	  }
	  
	  /**
	   * 发送手机短信
	   * @param request
	   * @param smDestAddrs
	   * @param SendMsg
	   */
	  public static final void SendSMS(HttpServletRequest request,String[] CellPhone,String SendMsg){
		  //空信息就不发了
		  if(CellPhone == null){return;}
		  if(CellPhone.length == 0){return;}
		  if(SendMsg == null){return;}
		  if(SendMsg.length() == 0){return;}
		  //创建smproxy
		  if(null == smproxy){
			  smproxy=createProxy(request);
		  }
		  if(null == smproxy){
			  return;
		  }
		  
		  SmSendBean bean = new SmSendBean(); //构造发送信息
		  bean.setSmDestAddrs(CellPhone);
		  bean.setSmMsgContent(SendMsg);
		  try {
			  //发送 
			int[] ret = smproxy.sendSm(bean); //int[] 返回所需要的sm_id，有多少个目的地址，就返回多少个sm_id，并且参照destAddrs的顺序排列，单个地址失败就返回-1;
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
	  
	  private static final DBSMProxy createProxy(HttpServletRequest request)
	     {
    	   DBSMProxy smproxy = new DBSMProxy();
	       //初始化
            try {
            	//request.getRealPath("smApiConf.xml")
            	if(request != null){
	            	String xmlFile = request.getServletContext().getRealPath("smApiConf.xml");
					smproxy.initConn(xmlFile);
            	}
			} catch (Exception e) {
				e.printStackTrace();
			}
            //登录
	        try {
	        	if (smproxy != null && request != null){
	        		if(null != request.getServletContext()){
		        		String SmsUserLoginName = request.getServletContext().getInitParameter("DBSMProxy_SMS_User");
		        		String SmsUserLoginPassword = request.getServletContext().getInitParameter("DBSMProxy_SMS_Password");
		        		if(null != SmsUserLoginName && null != SmsUserLoginPassword ){
		        			smproxy.login(SmsUserLoginName,SmsUserLoginPassword);
		        		}
	        		}
	        	}
	        }catch (Exception ex){
	            ex.printStackTrace();
	        }
	        
	        return smproxy;
	     }  
}
