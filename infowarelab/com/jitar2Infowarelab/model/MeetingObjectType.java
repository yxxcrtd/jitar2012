package com.jitar2Infowarelab.model;

/**
 * 视频会议的分类常量 , 对应 cn.edustar.jitar.pojos.Meetings.obj
 * 
 * 例如查询  协助组id=3 的视频会议，就是：Meetings.obj = 'group' and Meetings.objId = 3
 * 
 * @author dell
 *
 */
public class MeetingObjectType {
	
	/** 协助组类型的视频会议 */
	public static final String OBJECT_TYPE_GROUP = "group";

	/** 个人视频会议 */
	public static final String OBJECT_TYPE_USER = "user";

	/** 备课组类型的视频会议 */
	public static final String OBJECT_TYPE_PREPARECOURSE = "bkcourse";

	/** 站点教研视频频道视频会议 */
	public static final String OBJECT_TYPE_CHANNEL = "webchannel";
	
}
