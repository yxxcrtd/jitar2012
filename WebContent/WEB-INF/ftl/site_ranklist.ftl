<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>排行榜 <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />  
  <script src='js/jitar/core.js'></script>
  <script type="text/javascript" src="js/stat.js"></script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0'></div>
<div id='main'>
    <div class='tab_outer'>
    
      <div id="article_" class='tab2'>
      <#if !(type??)><#assign type="user" ></#if>
      <div class="${(type == 'user')?string('cur','') }"><a href='ranklist.action?type=user'>个人排行</a></div>
      <div class="${(type == 'ts')?string('cur','') }"><a href='ranklist.action?type=ts'>教研员排行</a></div>
          <div class="${(type == 'group')?string('cur','')} "><a href='ranklist.action?type=group'>协作组排行</a></div>
          <div class="${(type == 'school')?string('cur','') }"><a href='ranklist.action?type=school'>学校排行</a></div>
      </div>
	
		<#assign t1="0">
		<#assign t2="0">
		<#assign t3="0">
		<#assign t4="0">
		<#assign t5="0">
		<#assign t6="0">
		<#assign t7="0">
		<#assign t8="0">
		<#assign t9="0">
		<#assign t10="0">
		<#assign t11="0">
		<#assign t12="0">
		<#assign t13="0">
		<#assign t14="0">
		<#assign t15="0">
		<#assign t16="0">
		<#assign t17="0">
		<#assign t18="0">
		<#assign t19="0">
		<#assign t20="0">
		<#assign t21="0">
		<#assign t22="0">
		<#assign t23="0">
		<#assign t24="0">
		<#assign t25="0">
		<#assign t26="0">
		
		  
        <div class='tab_content' style='padding:5px;overflow:auto'>
        	<div id="article_0"  style="display: block;">
        <#if (type == 'district')>
            <table border="0" cellspacing='0' class='res_table'>
            <thead>
            <tr>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','districtName')=='districtName'>
            <#assign t1="1">
            <td class='td_middle' nowrap='nowrap' style='padding-left:15px'>区县名称&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','unitCount')=='unitCount'>
            <#assign t2="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;学校数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','userCount')=='userCount'>
            <#assign t3="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;工作室数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','articleCount')=='articleCount'>
            <#assign t4="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','recommendArticleCount')=='recommendArticleCount'>
            <#assign t5="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;推荐文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','resourceCount')=='resourceCount'>
            <#assign t6="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','recommendResourceCount')=='recommendResourceCount'>
            <#assign t7="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;推荐资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','photoCount')=='photoCount'>
            <#assign t8="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;图片数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','videoCount')=='videoCount'>
            <#assign t9="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;视频数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','totalScore')=='totalScore'>
            <#assign t10="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;当前积分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'区县排行','userField','aveScore')=='aveScore'>
            <#assign t11="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;人平均分&nbsp;</td>
            </#if>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td colspan='10' style='padding:4px;'></td>
            </tr>   
                    <#list distexlist as distEx>
	                    <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
	                    	<#if t1=="1">
	                        <td style="padding-left: 10px;">${distEx.districtName!}</td>
	                        </#if>
	                        <#if t2=="1">
	                        <td>${distEx.unitCount}</td>
	                        </#if>
	                        <#if t3=="1">
	                        <td>${distEx.userCount}</td>
	                        </#if>
	                        <#if t4=="1">
	                        <td>${distEx.articleCount}</td>
	                        </#if>
	                        <#if t5=="1">
	                        <td>${distEx.recommendArticleCount}</td>
	                        </#if>
	                        <#if t6=="1">
	                        <td>${distEx.resourceCount}</td>
	                        </#if>
	                        <#if t7=="1">
	                        <td>${distEx.recommendResourceCount}</td>
	                        </#if>
	                        <#if t8=="1">
	                        <td>${distEx.photoCount}</td>
	                        </#if>
	                        <#if t9=="1">
	                        <td>${distEx.videoCount}</td>
	                        </#if>
	                        <#if t10=="1">
	                        <td>${distEx.totalScore}</td>
	                        </#if>
	                        <#if t11=="1">
	                        <td>${distEx.aveScore?string(".00")}</td>
	                        </#if>
	                    </tr>
                    </#list>                
            </tbody>
	    </table>
                 
        </#if>
        <#if (type == 'school')>
            <table border="0" cellspacing='0' class='res_table'>
            <thead>
            <tr>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','unitName')=='unitName'>
            <#assign t1="1">
            <td class='td_middle' nowrap='nowrap' style='padding-left:15px'>学校名称&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','userCount')=='userCount'>
            <#assign t2="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;工作室数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','articleCount')=='articleCount'>
            <#assign t3="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','recommendArticleCount')=='recommendArticleCount'>
            <#assign t4="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;推荐文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','resourceCount')=='resourceCount'>
            <#assign t5="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','recommendResourceCount')=='recommendResourceCount'>
            <#assign t6="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;推荐资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','photoCount')=='photoCount'>
            <#assign t7="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;图片数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','videoCount')=='videoCount'>
            <#assign t8="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;视频数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','totalScore')=='totalScore'>
            <#assign t9="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;当前积分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'学校排行','userField','aveScore')=='aveScore'>
            <#assign t10="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;人平均分&nbsp;</td>
            </#if>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td colspan='10' style='padding:4px;'></td>
            </tr>
                    <#list unitList as unit>
	                    <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
	                    	<#if t1=="1">
	                        <td style="padding-left: 10px;">${unit.unitTitle!}</td>
	                        </#if>
	                        <#if t2=="1">
	                        <td>${unit.userCount}</td>
	                        </#if>
	                        <#if t3=="1">
	                        <td>${unit.articleCount}</td>
	                        </#if>
	                        <#if t4=="1">
	                        <td>${unit.recommendArticleCount}</td>
	                        </#if>
	                        <#if t5=="1">
	                        <td>${unit.resourceCount}</td>
	                        </#if>
	                        <#if t6=="1">
	                        <td>${unit.recommendResourceCount}</td>
	                        </#if>
	                        <#if t7=="1">
	                        <td>${unit.photoCount}</td>
	                        </#if>
	                        <#if t8=="1">
	                        <td>${unit.videoCount}</td>
	                        </#if>
	                        <#if t9=="1">
	                        <td>${unit.totalScore}</td>
	                        </#if>
	                        <#if t10=="1">
	                        <td>${unit.aveScore}</td>
	                        </#if>
	                    </tr>
                    </#list>            
            </tbody>
	    </table>
	    <div class='pgr'><#include 'inc/pager.ftl' ></div>
        </#if>    
        <#if (type == 'group')>
            <table border="0" cellspacing='0' class='res_table'>
            <thead>
            <tr>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','groupTitle')=='groupTitle'>
            <#assign t1="1">
            <td class='td_middle' nowrap='nowrap' style='padding-left:15px'>协作组名称&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','trueName')=='trueName'>
            <#assign t2="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;创建者&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','visitCount')=='visitCount'>
            <#assign t3="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;访问量&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','userCount')=='userCount'>
            <#assign t4="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;成员数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','articleCount')=='articleCount'>
            <#assign t5="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','bestArticleCount')=='bestArticleCount'>
            <#assign t6="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;精华文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','resourceCount')=='resourceCount'>
            <#assign t7="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','bestResourceCount')=='bestResourceCount'>
            <#assign t8="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;精华资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','topicCount')=='topicCount'>
            <#assign t9="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;主题数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','discussCount')=='discussCount'>
            <#assign t10="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;讨论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'协作组排行','userField','actionCount')=='actionCount'>
            <#assign t11="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;活动数&nbsp;</td>
            </#if>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td colspan='11' style='padding:4px;'></td>
            </tr>
            
            <#list groupList as group>
                <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
                	<#if t1=="1">
                    <td style="padding-left: 5px;"><a href='${SiteUrl!}g/${group.groupName}' target='_blank' title='${group.groupIntroduce!?html}'>${group.groupTitle!}</a></td>
                    </#if>
                    <#if t2=="1">
                    <td style="padding-left: 5px;"><a href="${SiteUrl}go.action?loginName=${Util.userById(group.createUserId).loginName!?html}" title="点击访问该工作室" target="_blank">${Util.userById(group.createUserId).trueName!?html}</a></td>
                    </#if>
                    <#if t3=="1">
                    <td>${group.visitCount}</td>
                    </#if>
                    <#if t4=="1">
                    <td>${group.userCount}</td>
                    </#if>
                    <#if t5=="1">
                    <td>${group.articleCount}</td>
                    </#if>
                    <#if t6=="1">
                    <td>${group.bestArticleCount}</td>
                    </#if>
                    <#if t7=="1">
                    <td>${group.resourceCount}</td>
                    </#if>
                    <#if t8=="1">
                    <td>${group.bestResourceCount}</td>
                    </#if>
                    <#if t9=="1">
                    <td>${group.topicCount}</td>
                    </#if>
                    <#if t10=="1">
                    <td>${group.discussCount}</td>
                    </#if>
                    <#if t11=="1">
                    <td>${group.actionCount}</td>
                    </#if>
                </tr>
            </#list>                 
            </tbody>
	    </table>
	    <div class='pgr'><#include 'inc/pager.ftl' ></div>
               	
        </#if>
        <#if type == 'ts'>
            <table border="0" cellspacing='0' class='res_table'>
            <thead>
            <tr>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','trueName')=='trueName'>
            <#assign t1="1">
            <td class='td_middle' nowrap='nowrap' style='padding-left:15px'>姓名&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','userScore')=='userScore'>
            <#assign t2="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;积分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','visitCount')=='visitCount'>
            <#assign t3="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;工作室访问&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','visitArticleCount')=='visitArticleCount'>
            <#assign t4="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章访问量&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','visitResourceCount')=='visitResourceCount'>
            <#assign t5="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源访问量&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','myArticleCount')=='myArticleCount'>
            <#assign t6="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;原创文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','otherArticleCount')=='otherArticleCount'>
            <#assign t7="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;转载文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','recommendArticleCount')=='recommendArticleCount'>
            <#assign t8="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;推荐文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','articleCommentCount')=='articleCommentCount'>
            <#assign t9="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章被评论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','articleICommentCount')=='articleICommentCount'>
            <#assign t10="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章评论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','resourceCount')=='resourceCount'>
            <#assign t11="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','recommendResourceCount')=='recommendResourceCount'>
            <#assign t12="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;推荐资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','resourceCommentCount')=='resourceCommentCount'>
            <#assign t13="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源被评论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','resourceICommentCount')=='resourceICommentCount'>
            <#assign t14="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源评论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','resourceDownloadCount')=='resourceDownloadCount'>
            <#assign t15="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源下载数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','prepareCourseCount')=='prepareCourseCount'>
            <#assign t16="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;我的主备数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','createGroupCount')=='createGroupCount'>
            <#assign t17="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;创建协作组数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','jionGroupCount')=='jionGroupCount'>
            <#assign t18="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;加入协作组数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','photoCount')=='photoCount'>
            <#assign t19="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;照片数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','videoCount')=='videoCount'>
            <#assign t20="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;视频数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','articlePunishScore')=='articlePunishScore'>
            <#assign t21="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章罚分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','resourcePunishScore')=='resourcePunishScore'>
            <#assign t22="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源罚分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','commentPunishScore')=='commentPunishScore'>
            <#assign t23="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;评论罚分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','photoPunishScore')=='photoPunishScore'>
            <#assign t24="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;图片罚分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'教研员排行','userField','videoPunishScore')=='videoPunishScore'>
            <#assign t25="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;视频罚分&nbsp;</td>
            </#if>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td colspan='25' style='padding:4px;'></td>
            </tr>
            <#list userList as user>
                    <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
                    	<#if t1="1">
                        <td style="padding-left: 2px;" nowrap='nowrap'>${user.trueName!}</td>
                        </#if>
                        <#if t2="1">
                        <td> ${user.userScore}</td>
                        </#if>
                        <#if t3="1">
                        <td>${user.visitCount}</td>
                        </#if>
                        <#if t4="1">
                        <td>${user.visitArticleCount}</td>
                        </#if>
                        <#if t5="1">
                        <td>${user.visitResourceCount}</td>
                        </#if>
                        <#if t6="1">
                        <td>${user.myArticleCount}</td>
                        </#if>
                        <#if t7="1">
                        <td>${user.otherArticleCount}</td>
                        </#if>
                        <#if t8="1">
                        <td>${user.recommendArticleCount}</td>
                        </#if>
                        <#if t9="1">
                        <td>${user.articleCommentCount}</td>
                        </#if>
                        <#if t10="1">
                        <td>${user.articleICommentCount}</td>
                        </#if>
                        <#if t11="1">
                        <td>${user.resourceCount}</td>
                        </#if>
                        <#if t12="1">
                        <td>${user.recommendResourceCount}</td>
                        </#if>
                        <#if t13="1">
                        <td>${user.resourceCommentCount}</td>
                        </#if>
                        <#if t14="1">
                        <td>${user.resourceICommentCount}</td>
                        </#if>
                        <#if t15="1">
                        <td>${user.resourceDownloadCount}</td>
                        </#if>
                        <#if t16="1">
                        <td>${user.prepareCourseCount}</td>
                        </#if>
                        <#if t17="1">
                        <td>${user.createGroupCount}</td>
                        </#if>
                        <#if t18="1">
                        <td>${user.jionGroupCount}</td>
                        </#if>
                        <#if t19="1">
                        <td>${user.photoCount}</td>
                        </#if>
                        <#if t20="1">
                        <td>${user.videoCount}</td>
                        </#if>
                        <#if t21="1">
                        <td>${user.articlePunishScore}</td>
                        </#if>
                        <#if t22="1">
                        <td>${user.resourcePunishScore}</td>
                        </#if>
                        <#if t23="1">
                        <td>${user.commentPunishScore}</td>
                        </#if>
                        <#if t24="1">
                        <td>${user.photoPunishScore}</td>
                        </#if>
                        <#if t25="1">
                        <td>${user.videoPunishScore}</td>
                        </#if>
                    </tr>
                    </#list>
                    </tbody>
            </table>
            <div class='pgr'><#include 'inc/pager.ftl' ></div>
      </#if> 
      
        <#if type == 'user'>
            <table border="0" cellspacing='0' class='res_table'>
            <thead>
            <tr>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','trueName')=='trueName'>
            <#assign t1="1">
            <td class='td_middle' nowrap='nowrap' style='padding-left:15px'>姓名&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','userScore')=='userScore'>
            <#assign t2="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;积分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','visitCount')=='visitCount'>
            <#assign t3="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;工作室访问&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','visitArticleCount')=='visitArticleCount'>
            <#assign t4="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章访问量&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','visitResourceCount')=='visitResourceCount'>
            <#assign t5="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源访问量&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','myArticleCount')=='myArticleCount'>
            <#assign t6="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;原创文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','otherArticleCount')=='otherArticleCount'>
            <#assign t7="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;转载文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','recommendArticleCount')=='recommendArticleCount'>
            <#assign t8="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;推荐文章数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','articleCommentCount')=='articleCommentCount'>
            <#assign t9="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章被评论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','articleICommentCount')=='articleICommentCount'>
            <#assign t10="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章评论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','resourceCount')=='resourceCount'>
            <#assign t11="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','recommendResourceCount')=='recommendResourceCount'>
            <#assign t12="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;推荐资源数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','resourceCommentCount')=='resourceCommentCount'>
            <#assign t13="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源被评论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','resourceICommentCount')=='resourceICommentCount'>
            <#assign t14="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源评论数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','resourceDownloadCount')=='resourceDownloadCount'>
            <#assign t15="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源下载数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','prepareCourseCount')=='prepareCourseCount'>
            <#assign t16="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;我的主备数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','createGroupCount')=='createGroupCount'>
            <#assign t17="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;创建协作组数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','jionGroupCount')=='jionGroupCount'>
            <#assign t18="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;加入协作组数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','photoCount')=='photoCount'>
            <#assign t19="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;照片数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','videoCount')=='videoCount'>
            <#assign t20="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;视频数&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','articlePunishScore')=='articlePunishScore'>
            <#assign t21="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;文章罚分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','resourcePunishScore')=='resourcePunishScore'>
            <#assign t22="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;资源罚分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','commentPunishScore')=='commentPunishScore'>
            <#assign t23="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;评论罚分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','photoPunishScore')=='photoPunishScore'>
            <#assign t24="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;图片罚分&nbsp;</td>
            </#if>
            <#if Util.getFieldNameInIni(iniFilePath,'个人排行','userField','videoPunishScore')=='videoPunishScore'>
            <#assign t25="1">
            <td class='td_middle' nowrap='nowrap'>&nbsp;视频罚分&nbsp;</td>
            </#if>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td colspan='25' style='padding:4px;'></td>
            </tr>
            <#list userList as user>
                    <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
                    	<#if t1="1">
                        <td style="padding-left: 2px;" nowrap='nowrap'>${user.trueName!}</td>
                        </#if>
                        <#if t2="1">
                        <td> ${user.userScore}</td>
                        </#if>
                        <#if t3="1">
                        <td>${user.visitCount}</td>
                        </#if>
                        <#if t4="1">
                        <td>${user.visitArticleCount}</td>
                        </#if>
                        <#if t5="1">
                        <td>${user.visitResourceCount}</td>
                        </#if>
                        <#if t6="1">
                        <td>${user.myArticleCount}</td>
                        </#if>
                        <#if t7="1">
                        <td>${user.otherArticleCount}</td>
                        </#if>
                        <#if t8="1">
                        <td>${user.recommendArticleCount}</td>
                        </#if>
                        <#if t9="1">
                        <td>${user.articleCommentCount}</td>
                        </#if>
                        <#if t10="1">
                        <td>${user.articleICommentCount}</td>
                        </#if>
                        <#if t11="1">
                        <td>${user.resourceCount}</td>
                        </#if>
                        <#if t12="1">
                        <td>${user.recommendResourceCount}</td>
                        </#if>
                        <#if t13="1">
                        <td>${user.resourceCommentCount}</td>
                        </#if>
                        <#if t14="1">
                        <td>${user.resourceICommentCount}</td>
                        </#if>
                        <#if t15="1">
                        <td>${user.resourceDownloadCount}</td>
                        </#if>
                        <#if t16="1">
                        <td>${user.prepareCourseCount}</td>
                        </#if>
                        <#if t17="1">
                        <td>${user.createGroupCount}</td>
                        </#if>
                        <#if t18="1">
                        <td>${user.jionGroupCount}</td>
                        </#if>
                        <#if t19="1">
                        <td>${user.photoCount}</td>
                        </#if>
                        <#if t20="1">
                        <td>${user.videoCount}</td>
                        </#if>
                        <#if t21="1">
                        <td>${user.articlePunishScore}</td>
                        </#if>
                        <#if t22="1">
                        <td>${user.resourcePunishScore}</td>
                        </#if>
                        <#if t23="1">
                        <td>${user.commentPunishScore}</td>
                        </#if>
                        <#if t24="1">
                        <td>${user.photoPunishScore}</td>
                        </#if>
                        <#if t25="1">
                        <td>${user.videoPunishScore}</td>
                        </#if>
                    </tr>
                    </#list>
                    </tbody>
            </table>
            <div class='pgr'><#include 'inc/pager.ftl' ></div>
      </#if>     
        </div>
      </div>   
</div>

<div style='clear:both;'></div>
<#include ('footer.ftl') >

<script>
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.profile_form.subjectId;
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = 'manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '所有学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}

function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  opt = document.createElement("OPTION");
  opt.value = val;
  opt.text = text;
  sel.options.add(opt);
}
</script>

</body>
</html>