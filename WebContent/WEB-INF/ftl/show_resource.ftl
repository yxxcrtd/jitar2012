<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="icon" href="${SiteUrl}images/favicon.ico" />
    <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
    <title><#include ('webtitle.ftl') ></title>
    <link rel="stylesheet" type="text/css" href="css/common/common.css" />
    <link rel="stylesheet" type="text/css" href="css/tooltip/tooltip.css" />
    <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
    <script src='js/jitar/core.js' type="text/javascript"></script>
    <script src='js/jitar/tooltip.js' type="text/javascript"></script>
    <script type="text/javascript" src="js/jitar/login.js"></script>
</head>
<body> 
    <#include 'site_head.ftl'>
    
    <div style='height:8px;font-size:0;'></div>
    <div class='containter'>
    <#if error??>
    <div style="text-align:center;color:#f00;font-size:24px;padding:20px">${error}</div>
    <#else>
    
        <div class='head_nav'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<#include 'site_subject_category_user_navbar.ftl'>&nbsp;资源属性
        </div>
        <table class='res_detail' cellspacing='1' cellpadding='3' border="0">
            <tr>
                <td style="width:100px" class='font_bold' align='right'>
                    &nbsp;资源标题：
                </td>
                <td style="padding-left: 10px;font-size:24px">
                    <img src='${Util.iconImage(resource.href!)}' align='absmiddle' border='0' />
                        ${resource.title!?html}
                        <#if resource.recommendState>
                            <img src='manage/images/ico_rcmd.gif' border='0' align='absmiddle' />
                        </#if>
                </td>
            </tr>
            <#if resourcedowload=='true'>
            <#if canPreview??>
            <tr>
                <td class='font_bold' align='right' style="vertical-align:top">&nbsp;资源预览：</td>
                <td style="padding-left: 10px;" id="showWindowFrame">
                <iframe src="${SiteUrl}jython/previewResource.py?resource=${resourceId}" style="width:100%;height:510px;border:0" border="0" frameBorder="0"></iframe>
                </td>
            </tr>
            </#if>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;下载资源：
                </td>
                <td style="padding-left: 10px;">
                    <a href='manage/resourceDownload?resourceId=${resource.resourceId}' target='_blank'>
                      <img src='images/download.png' border='0' alt="下载资源" title="下载资源" />
                    </a>
                </td>
            </tr>
            </#if>
            <#assign resource_user = Util.userById(resource.userId)>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;上传者：
                </td>
                <td style="padding-left: 10px;">
                    <a href='${SiteUrl}go.action?loginName=${resource_user.loginName}' target='_blank'>
                        ${resource_user.trueName!?html}
                    </a>
                    (<a href='${SiteUrl}go.action?profile=${resource_user.loginName}' target='_blank'>${resource_user.loginName}</a>)
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;上传时间：
                </td>
                <td style="padding-left: 10px;">
                    ${resource.createDate?string('yyyy-MM-dd HH:mm:ss')}
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;发布方式：
                </td>
                <td style="padding-left: 10px;">
                    <#if resource.shareMode == 1000>完全公开
                        <#elseif resource.shareMode == 500>组内公开
                        <#elseif resource.shareMode == 0>私有
                        <#else>未知
                    </#if>
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;所属学科：
                </td>
                <td style="padding-left: 10px;">
                    ${Util.subjectById(resource.subjectId!).msubjName!?html}
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;所属学段：
                </td>
                <td style="padding-left: 10px;">
                    ${resource.gradeName!?html}
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;资源类型：
                </td>
                <td style="padding-left: 10px;">
                    ${resource.scName!?html}
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;作者：
                </td>
                <td style="padding-left: 10px;">
                    ${resource.author!?html}
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;出版单位：
                </td>
                <td style="padding-left: 10px;">
                    ${resource.publisher!?html}
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;下载次数：
                </td>
                <td style="padding-left: 10px;">
                    ${resource.downloadCount}
                </td>
            </tr>
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;资源描述：
                </td>
                <td style="padding-left: 10px;">
                 ${resource.summary!?html}
                </td>
            </tr> 

            <#if score?? >
            <tr>
                <td class='font_bold' align='right'>
                    &nbsp;资源加分：
                </td>
                <td style="padding-left: 10px;">
                 ${scoreCreateUserName!?html}&nbsp;&nbsp;于:&nbsp;${scoreDate?string('yyyy-MM-dd HH:mm:ss')}&nbsp;&nbsp;对此内容加${score}分，理由：${scoreReason!?html}
                </td>
            </tr> 
            </#if>
            
        </table>
        
        <#if comment_list??>
            <div class="head_nav">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;资源评论：
            </div>
            <#list comment_list as comment>
                <table class="res_detail" cellspacing="1" cellpadding="3" border="0">
                    <tr>
                        <td style="width:100px" class="font_bold" align="right" class="comment_title">
                            &nbsp;评论者：
                        </td> 
                        <td style="padding-left: 10px;">
                            <#assign u = Util.userById(comment.userId)>
                            	<img src="${SSOServerUrl +'upload/'+u.userIcon!SiteUrl+"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif'" width="16" height="16" />
                            <#if loginUser??>
                                <#if (u.trueName??)>
                                    <#assign userName = u.trueName>
                                    <#elseif (u.loginName??)><#assign userName = u.loginName>
                                </#if>
                                <a href="${SiteUrl}go.action?loginName=${u.loginName}" onmouseover="ToolTip.showUserCard(event,'${u.loginName}','${userName}','${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}')">
                                   ${comment.userName}
                                </a>
                            <#else>
                                <a href="${SiteUrl}go.action?loginName=${u.loginName}">
                                    ${comment.userName}
                                </a>
                            </#if>
                            <font style="text-align: right;">@ ${comment.createDate?string('yyyy-MM-dd HH:mm:ss')}</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="font_bold" align="right">
                            &nbsp;评论内容：
                        </td>
                        <td style="padding-left: 10px;">
                        	<pre style="width: 750px; word-break: break-all; word-wrap: break-word;">${comment.content!}</pre>
                        </td>
                    </tr>
                </table>
                <br />
            </#list>
            <div class="pager">
                <#include "inc/pager.ftl">
            </div>
        </#if>

        <#if loginUser??>
            <div class="head_nav">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发表评论：
            </div>
            <form action="manage/resource.action" method="POST">
                <input type="hidden" name="cmd" value="pub_comment" />
                <input type="hidden" name="resourceId" value="${resource.resourceId}" />                
                <input type="hidden" name="title" value="RE:${resource.title!?html}" />
                <table class="res_detail" cellspacing="1" cellpadding="3" border="0">
                    <tr>
                        <td style="width:100px" class='font_bold' align='right'>
                                    评论内容：
                        </td>
                        <td>
                            <textarea class="comment_post_table_textinput" name="content" rows="6"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <input type="submit" value=" 发表评论 " onclick="if(this.form.content.value==''){alert('请输入评论内容。');return false;}" />
                        </td>
                    </tr>
                </table>
            </form>
        </#if>
    </#if>
    </div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>            
    <div style="clear:both"></div>        
    <#include "footer.ftl">
</body>
</html>
