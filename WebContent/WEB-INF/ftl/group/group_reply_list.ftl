<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${topic.title!?html} - ${group.groupTitle!?html}</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" type="text/css" href="../${SiteThemeUrl}groups.css" />  
  <link rel="stylesheet" type="text/css" href="../css/tooltip/tooltip.css" />
  <script src='../js/jitar/core.js' type="text/javascript"></script>
  <script src='../js/jitar/tooltip.js' type="text/javascript"></script>
  <script type="text/javascript" src="../js/jitar/login.js"></script>
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
    var JITAR_ROOT = '${SiteUrl}';
    var visitor = { id: null, name: null, nickName: null, role: 'guest' };  
  </script>  
  <script type="text/javascript">
  <!--
   //全部选择和取消全选.
  var blnIsChecked = true;
  function on_AllSelect(oForm) {
  		for (var i = 0; i < oForm.elements.length; i++) {
			if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
			}
			oForm.elements[i].checked = blnIsChecked;
		}
	 	if(oForm.elements["selAll"]) {
	 		if(blnIsChecked) {
	 			oForm.elements["selAll"].value = "取消全选";
	 		} else {
	 			oForm.elements["selAll"].value = "全部选择"; 
	 		}
	 	}
	 	blnIsChecked = !blnIsChecked;
	}
  
 	// 删除一个或多个回复.
	function delSel(list_form) {
		if (has_item_selected(list_form) == false) {
			alert("未选择要删除的回复.");
			return false;
		} else {
			if (confirm("您是否确定要删除选中的回复?") == false) {
				return false;
			}
		}
		
		list_form.cmd.value = 'delete_reply';
		list_form.submit();
 	}
 	
 	// 移除.
 	function unref_reply() {
 		if(has_item_selected() == false) {
			alert('没有选择任何要操作的回复');
			return;
		}
		if(confirm('您是否确定要移除选中的回复? \r\n\r\n回复不会被删除, 只是不显示在协作组中.') == false) return;
   		submit_command('unrefReply');
 	} 	
   	
 	function ref_reply() {
 		if(has_item_selected() == false) {
			alert('没有选择任何要操作的回复');
			return;
		}
		if(confirm('您是否确定要回复选中的回复?') == false) return;
		 submit_command('refReply');
 	}
   	 	
	//设置精华主题状态.
	function submit_command(cmd) {
		if(has_item_selected() == false) {
			alert('没有选择任何要操作的主题');
			return;
		}
		document.forms.list_form.cmd.value = cmd;
		document.forms.list_form.submit();
	}
   	 	
	//检查是否选择了数据.
	function has_item_selected() {
  	var ids = document.getElementsByName('replyId');
  	if (ids == null) return false;
  		for (var i = 0; i < ids.length; ++i) {
    	if (ids[i].checked) return true;
 	 	}
  		return false;
	}
	//-->
	</script>
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
  &gt;&gt; <a href='groupBbs.action?cmd=topic_list&amp;groupId=${group.groupId}'>协作组论坛</a> 
  &gt;&gt; <span>查看主题: ${topic.title!?html}</span>
</div>

<#assign can_manage = (group_member?? && group_member.status == 0 && group_member.groupRole >= 800) >

<form name='addReplyForm' method='get' action='groupBbs.action' >
  <input type='hidden' name='cmd' value='add_reply' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <input type='hidden' name='topicId' value='${topic.topicId}' />
</form>

<form  name="list_form" action="groupBbs.action" method="post" >
  <input type='hidden' name='cmd' value='delreply' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <input type='hidden' name='topicId' value='${topic.topicId}' />


<#-- 主题信息 -->
<div style='border:1px solid #b0bec7;background:#fff;padding:1px;'>
  <a name='thread_top'></a> 
  <h1 style='margin:0;'>
  <#if loginUser??>
    <span style='float:right;padding-right:10px;'><a href='#replay' style='color:#FFF'>回复本贴</a> <a href='${SiteUrl}manage/groupBbs.action?cmd=add_topic&groupId=${group.groupId}&redirect=true' style='color:#FFF'>发新帖子</a> </span>
  </#if>
  <img src='${ContextPath}css/index/j.gif' /> ${topic.title?html}
  </h1>
  <#assign u = Util.userById(topic.userId)>
  <table cellpadding="0" cellspacing="0" border='0' class='thread_table'>
  <tr>
    <td class="postauthor" style='padding:2px;'><div class='post_head' style='height:18px'>${u.nickName!?html}</div></td>
    <td>
      <div class='post_head' style='font-weight:normal;height:18px'>
      <span style='float:right;'>
        <a href='#' onclick='document.getElementById("con_thread").style.fontSize="16px";return false;'>大</a> 
        <a href='#' onclick='document.getElementById("con_thread").style.fontSize="14px";return false;'>中</a> 
        <a href='#' onclick='document.getElementById("con_thread").style.fontSize="12px";return false;'>小</a>
      </span>
      <span style='float:left;'>发表于：${topic.createDate}</span>
      <span style='clear:both;'>&nbsp;</span>
      </div>
    </td>
  </tr>
  <tr valign='top'>
    <td class="postauthor">
      <div class='posticon'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='128' height='128' /></div>
      <#if u??>
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
      </#if>
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

<#-- 回复列表 -->
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
        <div class='posticon'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" onerror="this.src='${SiteUrl}images/default.gif'" width='128' height='128' /></div>
        <div>文章数：${u.articleCount}</div>
        <div>资源数：${u.resourceCount}</div>
        <div>图片数：${u.photoCount}</div>
        <div>主题数：${u.topicCount}</div>
        <div>评论数：${u.commentCount}</div>
        <div>注册时间：</strong>${u.createDate?string('yyyy-MM-dd HH:mm')}</div>
        <div class='spacer'></div>
        <div style="line-height:200%">
        <a class="linkButton icoBlog" href='${SiteUrl}go.action?loginName=${u.loginName}'>个人主页</a>
        <a class="linkButton icoProfile" href='${SiteUrl}go.action?loginName=${u.loginName}/profile'>查看档案</a>
        <br/>
        <#if loginUser??>
        <a class="linkButton icoFriend"  href='' onclick="DivUtil.addFriend('${u.loginName}');return false;">加为好友</a>
        <a class="linkButton icoMessage" href='' onclick="DivUtil.sendUserMessage('${u.loginName}');return false;">发送消息</a>
        <#else>
        <a class="linkButton icoFriend"  href='login.jsp'>加为好友</a>
        <a class="linkButton icoMessage" href='login.jsp'>发送消息</a>
        </#if>
        </div>
    <#if can_manage >
        <div style='clear:both'><input type="checkbox" name="replyId"  value="${re.replyId!}"/> 选择</div>
    </#if>
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
    <#if can_manage >
      <a href="groupBbs.action?cmd=edit_reply&amp;groupId=${group.groupId}&amp;topicId=${topic.topicId}&amp;replyId=${re.replyId}">编辑</a> 
    </#if>
      <a href='#thread_top'>返回顶部</a></div></td>
    </tr>
    </table>
  </div>
</#list>

<div class='spacer'></div>
<div class='pager' style='text-align:right;'>
  <#include ('../inc/pager.ftl') >
</div>
<div class='spacer'></div>
<div class='funcButton'>
<#if can_manage >
  <input type="button" class='button' id="selAll" name="Sel_All" onclick="on_AllSelect(list_form)" value="全部选择" />&nbsp;&nbsp;
</#if>
  <input type="button" class='button' onclick="document.addReplyForm.submit();" value=" 发表回复 "/>&nbsp;&nbsp;
<#if can_manage >
  <input type="button" class='button' onclick="delSel(list_form)" value="删除回复"  />&nbsp;&nbsp;
  <input type='button' class='button' value='设置精华' onclick='submit_command("best_reply")' />&nbsp;&nbsp;
  <input type='button' class='button' value='取消精华' onclick='submit_command("unbest_reply")' />&nbsp;&nbsp;
</#if>
</div>
<div class='spacer'></div>

</form>

<#if loginUser??>
<div id='__fastReply' style='border:1px solid #b0bec7;background:#fff;'>
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
            <script id="content" name="content" type="text/plain" style="width:800px;height:200px;">
            </script>                          
            <script type="text/javascript">
                var editor = UE.getEditor('content');
            </script>         
      </td>
    </tr>
    <tr>
      <td></td>
      <td>
        <input type="submit" class='button' value="快速回复" />
      </td>
    </tr>
  </table>
</form>
</div>
</div>
<div id="shareDiv"></div>
</#if>


</body>
</html>
<#macro topicImage topic>
  <#if topic.isTop><img src='images/ico_top.gif' border='0' /></#if>
  <#if topic.isBest><img src='images/ico_best.gif' border='0' /></#if>
</#macro>
<#macro replyImage reply>
  <#if topic.isBest><img src='images/ico_best.gif' border='0' /></#if>
</#macro>