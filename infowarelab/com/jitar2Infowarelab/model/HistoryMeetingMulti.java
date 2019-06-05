package com.jitar2Infowarelab.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class HistoryMeetingMulti {
	
	/** 返回的历史会议数量 */
	private int returnNum;
	
	/** 从第几条记录开始返回 */
	private int startFrom;
	
	/** 共有多少个历史会议 */
	private int totalNum;
	
	/** 查询到的会议 */
	private List<HistoryMeeting> historyMeetings;
	
	public int getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(int returnNum) {
		this.returnNum = returnNum;
	}
	public int getStartFrom() {
		return startFrom;
	}
	public void setStartFrom(int startFrom) {
		this.startFrom = startFrom;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public List<HistoryMeeting> getHistoryMeetings() {
		return historyMeetings;
	}
	public void setHistoryMeetings(List<HistoryMeeting> historyMeetings) {
		this.historyMeetings = historyMeetings;
	}
	
}
