package com.jitar2Infowarelab.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("users")
public class CemUsers {
	@XStreamImplicit(itemFieldName="user")
	private List<CemUser> users;

	public List<CemUser> getUsers() {
		return users;
	}

	public void setUsers(List<CemUser> users) {
		this.users = users;
	}
	
}
