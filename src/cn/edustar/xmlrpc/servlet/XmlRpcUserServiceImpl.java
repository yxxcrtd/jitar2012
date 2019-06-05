package cn.edustar.xmlrpc.servlet;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.edustar.jitar.context.UserContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Meetings;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.User;

public class XmlRpcUserServiceImpl {
	UserContext userContext = UserContext.getCurrentUserContext();

	/**
	 * 查找用户，并返回用户信息
	 * 
	 * @param loginName
	 * @return Document 要求返回的信息格式： 
	 * <user>
	 * 	<video>0</video> 0 无会议室权限 1=有权限
	 * 	<loginname>登录名</loginname>
	 * 	<username>用户姓名</username>
	 * 	<password>密码</password>
	 * </user>
	 */ 
	public Document getExUser(String loginName) {
		// 查找用户信息,以及是否有视频会议室的权限
		
		
		//System.out.println("getExUser loginName="+loginName);
		//System.out.println("userContext is null ="+userContext);
		//System.out.println("getExUser loginName="+userContext.getUserService());
		
		
		User user = userContext.getUserService().getUserByLoginName(loginName);
		//System.out.println("user:" + user);
		
		if (null != user) {
			Meetings meetings = userContext.getMeetingsService().getMeetingsByObjAndObjId("user", user.getUserId());
			if (null != meetings) {
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("user"); // 创建根节点
				Element root_Video = root.addElement("video");
				root_Video.setText("1");
				Element root_loginname = root.addElement("loginname");
				root_loginname.setText(loginName);
				Element root_username = root.addElement("username");
				root_username.setText(user.getTrueName()); // 暂时用 TrueName
				Element root_password = root.addElement("password");
				root_password.setText(""); // 暂时不提供密码
				return document;
			}
		}
		return null;
	}

	/**
	 * 要求返回的信息格式：
	 * <user>
	 * 	<groupid>11</groupid> // 协作组的ID
	 * 	<groupname>青春</groupname> // 协作组名称
	 * 	<loginname>登录名</loginname> // 需要的信息可以添加
	 * 	<username>用户姓名</username> // 同上
	 *	<password>密码</password> // 同上
	 * </user>
	 */
	public Document getGroupAdministrator(String groupId) {
		// 根据协作组Id得到协作组信息，并得到组管理员的用户信息
		Group group = userContext.getGroupService().getGroup(Integer.valueOf(groupId));
		if (null != group) {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("user"); // 创建根节点
			Element root_groupid = root.addElement("groupid");
			root_groupid.setText(groupId);
			Element root_groupname = root.addElement("groupname");
			root_groupname.setText(group.getGroupName());
			Element root_loginname = root.addElement("loginname");
			int createId = group.getCreateUserId();
			User user = userContext.getUserService().getUserById(createId);
			root_loginname.setText(user.getLoginName());
			Element root_username = root.addElement("username");
			root_username.setText(user.getLoginName());
			Element root_password = root.addElement("password");
			root_password.setText(""); // 暂时不提供密码
			return document;
		}
		return null;
	}

	/**
	 * 要求返回的信息格式：
	 * <user>
	 * 	<jibeiid>11</jibeiid>集体备课id
	 * 	<jibeiname>青春</jibeiname>集体备课名称
	 * 	<loginname>登录名</loginname>
	 * 	<username>用户姓名</username>
	 * 	<password>密码</password>
	 * </user>
	 */
	public Document getJiBeiAdministrator(String jibeiId) {
		// 根据集备id得到集备信息，并得到集备主备人的用户信息
		PrepareCourse prepareCourse = userContext.getPrepareCourseService().getPrepareCourse(Integer.valueOf(jibeiId));
		if (null != prepareCourse) {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("user"); // 创建根节点
			Element root_groupid = root.addElement("jibeiid");
			root_groupid.setText(jibeiId);
			Element root_groupname = root.addElement("jibeiname");
			root_groupname.setText(prepareCourse.getTitle());
			Element root_loginname = root.addElement("loginname");
			int createId = prepareCourse.getCreateUserId();
			User user = userContext.getUserService().getUserById(createId);
			root_loginname.setText(user.getLoginName());
			Element root_username = root.addElement("username");
			root_username.setText(user.getLoginName());
			Element root_password = root.addElement("password");
			root_password.setText(""); // 暂时不提供密码
			return document;
		}
		return null;
	}

}
