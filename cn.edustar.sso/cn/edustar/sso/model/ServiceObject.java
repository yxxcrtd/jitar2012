package cn.edustar.sso.model;

/**
 * 调用服务的返回信息
 * 
 * @author baimindong
 *
 */
public class ServiceObject {
	//返回成功或失败
	public boolean success = false;
	//返回的JSON信息,然后可以根据需要转换为对应的对象
	public String returnobject = null;
	
}


