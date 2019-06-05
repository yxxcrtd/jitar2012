package com.jitar2Infowarelab.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("meetings")
public class Meetings {
	@XStreamImplicit(itemFieldName="meeting")
	private List<Meeting> meetings;

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
}
