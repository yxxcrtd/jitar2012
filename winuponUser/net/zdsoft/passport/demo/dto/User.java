/* 
 * @(#)User.java    Created on 2008-10-28
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: User.java 28 2008-10-28 12:14:56Z huangwj $
 */
package net.zdsoft.passport.demo.dto;

import java.io.Serializable;

/**
 * 用户信息值对象, 表示本系统的用户信息.
 * 
 * @author huangwj
 * @version $Revision: 28 $, $Date: 2008-10-28 20:14:56 +0800 (星期二, 28 十月 2008) $
 */
public class User implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5838321428881848248L;

    public static final String KEY = "net.zdsoft.passport.demo.dto.User";

    private String id;
    private String accountId;
    private String username;
    private String nickname;
    private int score;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
