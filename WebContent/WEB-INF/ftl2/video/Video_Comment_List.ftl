<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
        <link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
        <link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css">
        <script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
        <script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
        <script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
        <script src='${SiteUrl}js/jitar/core.js' type="text/javascript"></script>
        <script src='js/jitar/tooltip.js' type="text/javascript"></script>
        <script type="text/javascript" src="js/jitar/login.js"></script>
    </head>

	<body>
        <#if videoCommentList??>
            <#list videoCommentList as commentX>
                  <#if commentX_index % 2 = 1>
                       <table class="liBg" cellspacing="1" cellpadding="3" border="0" style="width:100%">
                  <#else> 
                      <table cellspacing="1" cellpadding="3" border="0" style="width:100%">
                  </#if>  
            
                    <tr>
                        <td width="18%" class="font_bold" align="right" class="comment_title">
                            &nbsp;评论者：
                        </td> 
                        <td style="padding-left: 10px;" class="comment_title">
                        	<#assign comment = commentX>
                            <#assign u = Util.userById(comment.userId)>
                            <img src="${SSOServerUrl +'upload/'+u.userIcon!"images/default.gif"}" width="16" height="16" onerror="this.src='${ContextPath}images/default.gif'"/>
                            <#if loginUser??>
                                <#if (u.trueName??)>
                                    <#assign userName = u.trueName>
                                    <#elseif (u.nickName??)><#assign userName = u.nickName>
                                    <#elseif (u.loginName??)><#assign userName = u.loginName>
                                </#if>
                                <a target="_blank" href="${SiteUrl}go.action?loginName=${u.loginName}" onmouseover="ToolTip.showUserCard(event,'${u.loginName}','${userName}','${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}')">
                                   ${comment.userName}
                                </a>
                            <#else>
                                <a href="${SiteUrl}go.action?loginName=${u.loginName}">
                                    ${comment.userName}
                                </a>
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td class="font_bold" align="right">
                            &nbsp;评论内容：
                        </td>
                        <td style="padding-left: 10px;">
                        	<div class='commentContent'>${comment.content!}</div>
                        	<!--
                            <textarea class="comment_post_table_textinput" rows="5" readonly="true">${comment.content!?html}</textarea>
                            -->
                        </td>
                    </tr>
                    <tr>
                        <td class="font_bold" align="right">
                            &nbsp;评论时间：
                        </td> 
                        <td style="padding-left: 10px;">
                            ${comment.createDate!?string('yyyy-MM-dd HH:mm:ss')}
                        </td>
                    </tr>
                </table>
            </#list>
        </#if>

        <div class="listContTitleBg">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发表评论：
        </div>
        <@s.form action="video">
            <input type="hidden" name="cmd" value="pub_comment" />
            <input type="hidden" name="videoId" value="${video.videoId}" />
            <input type="hidden" name="comment.title" value="" />
            <table class="res_detail" cellSpacing="1" cellPadding="3" border="0" style="width:100%">
                <tr>
                    <td width="18%" class="font_bold" align="right">
                        &nbsp;评论内容：
                    </td>
                    <td style="padding-left: 10px;">
                        <textarea class="comment_post_table_textinput" name="comment.content" style="width:90%;height:80px"></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="submit" class="specialSubmit" value=" 发表评论 " />
                    </td>
                </tr>
            </table>
        </@s.form>
<#include "../pager.ftl">
	</body>
</html>
<script type="text/javascript">
var h;
if(window.document.documentElement)
h = window.document.documentElement.scrollHeight;
else
h = window.document.body.scrollHeight;

window.parent.document.getElementById("videoCommentFrame").style.height=h+ "px";
</script>