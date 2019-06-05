<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="icon" href="${SiteUrl}images/favicon.ico" />
<link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
<title>${channel.title}</title>
<#if cssStyle??>
    <style>
    ${cssStyle}
    </style>
<#else>
    <#if channel.cssStyle?? >
    <style>
    ${channel.cssStyle}
    </style>
    <#else>
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/channel/template1/common.css" />
    </#if>
</#if>
<script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
</head>
<body>
<div class="bodycontent">
<div style="width:1000px;margin:auto;padding-top:10px;padding-right:50px;height:30px">
<font  style="float:right">
<#if loginUser??>
<#if AdminType != "">
<a href="${SiteUrl}manage/channel/channel.action?cmd=manage&channelId=${channel.channelId}">频道管理</a> <strong>|</strong> 
</#if>
<a href="${SiteUrl}go.action?loginName=${loginUser.loginName}">我的工作室</a> <strong>|</strong> 
<a href="${SiteUrl}logout.jsp?ru=${ru!}">退出登录</a>
<#else>
<a href="${SiteUrl}login.jsp?redUrl=${(SiteUrl + 'manage/channel/channel.action?cmd=manage&channelId=' + (channel.channelId)?string)?url}">用户登录</a>
</#if>
</font>
</div>
<div class="content">
#[频道导航]