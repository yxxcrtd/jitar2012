<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}groups.css" />  
  <link rel="stylesheet" type="text/css" href="css/tooltip/tooltip.css" />
  <script src='js/jitar/core.js' type="text/javascript"></script>
  <script src='js/jitar/tooltip.js' type="text/javascript"></script>
  <script type="text/javascript" src="js/jitar/login.js"></script>
<!-- 配置上载路径 -->
<script type="text/javascript">
    window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
    window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
</script>
<!-- 配置文件 -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>  
  <script type="text/javascript">
  	var JITAR_ROOT = ' ${SiteUrl}';
	var visitor = { id: null, name: null, nickName: null, role: 'guest' };	
  </script>  
 </head>
<body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div style='border:1px solid #b0bec7;margin:auto;background:url(${SiteThemeUrl}g_bg.jpg)'>
	<table border='0' width='100%' cellpadding='4'>
	<tr valign='top'>
	<td style='text-align:right;'>
		<img src='${Util.url(group.groupIcon!"images/group_default.gif")}' width='64' height='64' />
	</td>
	<td>
		<div><strong>协作组名称：</strong><a href='${SiteUrl}g/${group.groupName}'>${group.groupTitle!?html}</a></div>
		<div><strong>协作组描述：</strong>${group.groupIntroduce!}</div>
		<div><strong>协作组标签：</strong>
		<#list Util.tagToList(group.groupTags!) as tag>
      		<a href='${SiteUrl}showTag.action?tagName=${tag?url("UTF-8")}'>${tag}</a>
      		<#if tag_has_next>,</#if>
		</#list>
    	</div>	
    	<div><strong>协作组创建者：</strong>
		<#assign u = Util.userById(group.createUserId)>
			${u.nickName?html}
		</div>
		<div><strong>协作组管理员：</strong>
			<#if manager??>
			 <a href='${SiteUrl}go.action?loginName=${manager.loginName}' target='_blank'>${manager.loginName}</a>
			 <#else>
			   当前没有组长
			 </#if>
		</div>
	</td>
	<td>
		<div><strong>协作组成员数：</strong>${group.userCount}</div>
		<div><strong>协作组访问数：</strong>${group.visitCount}</div>
		<div><strong>协作组文章数：</strong>${group.articleCount}</div>
		<div><strong>协作组主题数：</strong>${group.topicCount}</div>
		<div><strong>协作组资源数数：</strong>${group.resourceCount}</div>			
	</td>
	</tr>
	</table>
</div>
<div class='spacer'></div>

<div style='border:1px solid #b0bec7;background:#fff;padding:1px;'>
	<a name='thread_top'></a>	
	<h1 style='margin:0;'>
	<#if loginUser??>
		<span style='float:right;padding-right:10px;'><a href='#replay'>回复本贴</a> <a href='${SiteUrl}manage/groupBbs.action?cmd=add_topic&groupId=${group.groupId}&redirect=true'>发新帖子</a> </span>
	</#if>
	<img src='${ContextPath}css/index/j.gif' /> ${topic.title?html}
	</h1>
	<#assign u = Util.userById(topic.userId)>
	<table cellpadding="0" cellspacing="0" border='0' class='thread_table'>
	<tr>
		<td class="postauthor" style='padding:2px;'><div class='post_head' style='height:18px'>${u.nickName}</div></td>
		<td>
			<div class='post_head' style='font-weight:normal;height:18px'>
			<span style='float:right;'>
				<a href='' onclick='document.getElementById("con_thread").style.fontSize="16px";return false;'>大</a> 
				<a href='' onclick='document.getElementById("con_thread").style.fontSize="14px";return false;'>中</a> 
				<a href='' onclick='document.getElementById("con_thread").style.fontSize="12px";return false;'>小</a>
			</span>
			<span style='float:left;'>发表于：${topic.createDate}</span>
			<span style='clear:both;'>&nbsp;</span>
			</div>
		</td>
	</tr>
	<tr valign='top'>
		<td class="postauthor">
			<div class='posticon'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='128' height='128' /></div>
			<div>文章数：${u.articleCount}</div>
			<div>资源数：${u.resourceCount}</div>
			<div>图片数：${u.photoCount}</div>
			<div>主题数：${u.topicCount}</div>
			<div>评论数：${u.commentCount}</div>
			<div>注册时间：${u.createDate?string('yyyy-MM-dd HH:mm')}</div>
			<div class='spacer'></div>
			<div style="line-height:200%">
			<a class="linkButton icoBlog" href='${SiteUrl}go.action?loginName=${u.loginName}'>个人主页</a>
			<a class="linkButton icoProfile" href='${SiteUrl}go.action?loginName=${u.loginName}'>查看档案</a>
			<br/>
			<#if loginUser??>
			<a class="linkButton icoFriend"  href='' onclick="DivUtil.addFriend('${u.loginName}');return false;">加为好友</a>
			<a class="linkButton icoMessage" href='' onclick="DivUtil.sendUserMessage('${u.loginName}');return false;">发送消息</a>
			<#else>
			<a class="linkButton icoFriend"  href='login.jsp'>加为好友</a>
			<a class="linkButton icoMessage" href='login.jsp'>发送消息</a>
			</#if>
			</div>
		</td>
		<td>
			<div style='padding:6px 10px'><b>${topic.title?html}</b></div>
			<div  class='thread_content' id='con_thread'>${topic.content}</div>
		</td>
	</tr>
	<tr>
		<td class="postauthor" style='padding:2px;'></td>
		<td style='padding-left:1px;'>
		<div class='thread_foot'>
		<#if loginUser??>
		<a style='display:none' href='#replay' onclick='document.forms[0].content.value="[quote]" + document.getElementById("con_thread").innerHTML + "[/quote]";'>引用</a> 
		<a href='#replay'>回复</a> 
		</#if>
		<a href='#thread_top'>返回顶部</a></div></td>
	</tr>
	</table>
</div>
<#list reply_list as re>
	<#assign u = Util.userById(re.userId)>
	<div class='spacer'></div>
	<div style='border:1px solid #b0bec7;background:#fff;padding:1px;'>
		<table cellpadding="0" cellspacing="0" border='0' class='thread_table'>
		<tr>
			<td class="postauthor" style='padding:2px;'><div class='post_head' style='height:18px'>${u.nickName}</div></td>
			<td>
				<div class='post_head' style='font-weight:normal;height:18px'>
					<div style='float:right'>
					<a href='' onclick='document.getElementById("con_thread${re.replyId}").style.fontSize="16px";return false;'>大</a> 
					<a href='' onclick='document.getElementById("con_thread${re.replyId}").style.fontSize="14px";return false;'>中</a> 
					<a href='' onclick='document.getElementById("con_thread${re.replyId}").style.fontSize="12px";return false;'>小</a>
					</div>
					<div style='float:left'>
					发表于：${re.createDate}
					</div>
				</div>
			</td>
		</tr>
		<tr valign='top'>
			<td class="postauthor">
				<div class='posticon'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='128' height='128' /></div>
				<div>文章数：${u.articleCount}</div>
				<div>资源数：${u.resourceCount}</div>
				<div>图片数：${u.photoCount}</div>
				<div>主题数：${u.topicCount}</div>
				<div>评论数：${u.commentCount}</div>
				<div>注册时间：</strong>${u.createDate?string('yyyy-MM-dd HH:mm')}</div>
				<div class='spacer'></div>
				<div style="line-height:200%">
				<a class="linkButton icoBlog" href='${SiteUrl}go.action?loginName=${u.loginName}'>个人主页</a>
				<a class="linkButton icoProfile" href='${SiteUrl}go.action?loginName=${u.loginName}'>查看档案</a>
				<br/>
				<#if loginUser??>
				<a class="linkButton icoFriend"  href='' onclick="DivUtil.addFriend('${u.loginName}');return false;">加为好友</a>
				<a class="linkButton icoMessage" href='' onclick="DivUtil.sendUserMessage('${u.loginName}');return false;">发送消息</a>
				<#else>
				<a class="linkButton icoFriend"  href='login.jsp'>加为好友</a>
				<a class="linkButton icoMessage" href='login.jsp'>发送消息</a>
				</#if>
				</div>
			</td>
			<td>
				<div style='padding:6px 10px;font-weight:bold;' id='title_thread${re.replyId}'>${re.title?html}</div>
				<div class='thread_content' id='con_thread${re.replyId}'>${re.content}</div>				
			</td>
		</tr>
		<tr>
			<td class="postauthor" style='padding:2px;'></td>
			<td style='padding-left:1px;'>
			<div class='thread_foot'>
			<#if loginUser??>
			<a style='display:none' href='#replay' onclick='document.forms[0].content.value="[quote]" + document.getElementById("con_thread${re.replyId}").innerHTML + "[/quote]";'>引用</a> <a href='#replay' onclick='document.forms[0].elements["title"].value="回复：" + document.getElementById("title_thread${re.replyId}").innerHTML'>回复</a> 
			</#if>
			<a href='#thread_top'>返回顶部</a></div></td>
		</tr>
		</table>
	</div>
</#list>

<div class='spacer'></div>
<div class='pager' style='text-align:right;'>
	<#include ('inc/pager.ftl') >
</div>
<div class='spacer'></div>
<div class='spacer'></div>
<#if loginUser??>
<div style='border:1px solid #b0bec7;background:#fff;'>
	<a name='replay'></a>
	<div style='background:url(${SiteThemeUrl}portalbox_bg.gif);height:20px;padding:4px;font-weight:bold;border-bottom:1px solid #b0bec7'>快速回复</div>
	<div style='padding:10px;'>
	<form name='fastReplyForm' action="${SiteUrl}manage/groupBbs.action" method="post">
	<input type='hidden' name='cmd' value='save_reply' />
	<input type='hidden' name='redirect' value='true' />
	<input type='hidden' name='topicId' value='${topic.topicId}' />
	<input type='hidden' name='groupId' value='${group.groupId}'>
	<table cellspacing='1' width='100%'>   
    <tr>
      <td align="right" width='20%'><b>回复标题:</b></td>
      <td><input type="text" name="title" size="80" value='回复：${topic.title?html}' /> <font color='red'>*</font> 必须填写回复标题</td>
    </tr>                       
    <tr>
      <td valign="top" align="right"><b>回复内容:</b></td>
      <td> 

            <script id="DHtml" name="content" type="text/plain" style="width:740px;height:200px;">
            </script>                          
            <script type="text/javascript">
                var editor = UE.getEditor('DHtml');
            </script>
                                
      </td>
    </tr>
    <tr>
      <td></td>
      <td>
        <input type="submit" class='button' value="快速回复" />&nbsp;&nbsp;
        <input type='reset'  class='button' value=" 重  置 "/>&nbsp;&nbsp;
        <input type='button' value='发表新帖' onclick='window.location.href="${SiteUrl}manage/groupBbs.action?cmd=add_topic&groupId=${group.groupId}&redirect=true"' />
      </td>
    </tr>
  </table>
</form>
</div>
</div>
<div id="shareDiv"></div>
</#if>


<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
<div style='clear:both;'></div>
<#include ('footer.ftl') >
</body>
</html>