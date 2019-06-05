package com.jitar2Infowarelab.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("historyMeetings")
public class HistoryMeetings {
	@XStreamImplicit(itemFieldName="historyMeeting")
	private List<HistoryMeeting> historyMeetings;
	
	public List<HistoryMeeting> getHistoryMeetings() {
		return historyMeetings;
	}
	public void setHistoryMeetings(List<HistoryMeeting> historyMeetings) {
		this.historyMeetings = historyMeetings;
	}	
}
