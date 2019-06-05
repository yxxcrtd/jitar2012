<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${prepareCourse.title?html}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_8.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}groups.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}action.css" />
  <#include ('common_script.ftl') >
  <script src='${SiteUrl}js/jitar/core.js'></script>
  <script src='${SiteUrl}js/jitar/lang.js'></script>
  <script src='${SiteUrl}js/datepicker/calendar.js' type="text/javascript"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
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
//取fck内容的长度
function GetMessageLength(str)
{
    return editor.getContentLength(); 
}  
//取fck内容
function GetMessageContent(str)
{
     return editor.getContent();
}  
     function checkData(frm)
    {
        if(frm.title.value=="")
        {
            alert("请输入标题");
            return false;
        }
        if(GetMessageLength("DHtml")=='0')
        {
            alert('请输入讨论内容');       
            return false;
        }        
        return true;   
    }
</script>    
</head> 
<body>
    <#include ('func.ftl') >
    <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
    <div id='header'>
      <div id='blog_name'><span>${prepareCourse.title!?html}</span></div>
    </div>
    <div id='nav'><#include ('navbar.ftl') ></div>
    <#include ('/WEB-INF/layout/layout_8.ftl') >
    <div id='placerholder1' title='教研活动' style='display:none;padding:1px;'> 

<#if action ?? >
<#assign a = action >
<a name='thread_top'></a>
<div class='dist'>
  <div class='dist_head'>
  	<div style='float:right;padding-right:10px'><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_action_edit.py?actionId=${a.actionId}'>修改活动</a></div>
    <div class='dist_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;活动详细信息</div>    
  </div>
  <div class='dist_content' style='text-align:left;'>

 <#if !loginUser?? >
 <div>要参与本活动，请先<a href='${SiteUrl}login.jsp'>登录系统</a>。</div>
 </#if>

   <form id="fAction" method="post" action='${SiteUrl}showAction.action?actionId=${a.actionId}'>
    <table border='0' cellpadding='4' cellspacing='1' width='100%' class='table_action'>
    <tr>
    <td class='table_action_td1'>活动名称：</td><td class='table_action_td2'>${a.title!?html}</td>
    </tr>
    <tr>
    <td class='table_action_td1'>活动类型：</td><td class='table_action_td2'>
    <#if a.actionType == 0 >
        任何人都可以参加。
    <#elseif a.actionType == 1 >
        <#if a.ownerType == 'user' >
          只有创建者的好友才能参加
        <#elseif a.ownerType == 'group' >
          只有该群组内成员才能参加
        <#elseif a.ownerType == 'course' >
         只有该备课组内成员才能参加
        <#else>
         未知 
        </#if>
    <#elseif a.actionType == 2 >
        只能邀请参加
    <#else>
        未定
    </#if>
       </td>
    </tr>
    <tr>
        <td class='table_action_td1'>活动方式：</td><td class='table_action_td2'>
        <#if a.visibility == 0 >
            完全公开
        <#elseif  a.visibility == 1 >
            保密
        <#else>
            未定
        </#if>
      </td>
    </tr>
     <tr>
        <td class='table_action_td1'>状态：</td><td class='table_action_td2'>
        <#if a.status == 0 >
            正常
        <#elseif a.status == 1 >
            待审核
        <#elseif a.status == 2 >
            已关闭
        <#elseif a.status == 3 >
            锁定
         <#elseif a.status == -1 >
            待删除（回收站）
        <#else>
            未定
        </#if>
      </td>
    </tr>
    <tr>
      <td class='table_action_td1'>活动描述</td><td class='table_action_td2'>
      ${a.description}
      </td>
    </tr>
    <tr>
        <td class='table_action_td1'>活动人数限制：</td><td class='table_action_td2'>${a.userLimit}</td>
    </tr>
    <tr>
        <td class='table_action_td1'>已经报名人数：</td><td class='table_action_td2'>${usercount!}</td>
    </tr
    
    <tr>
        <td class='table_action_td1'>活动开始时间：</td>
        <td class='table_action_td2'>
        ${a.startDateTime?string('yyyy年MM月dd日 HH点mm分')}       
    </tr>
    <tr>
        <td class='table_action_td1'>活动结束时间：</td>
        <td class='table_action_td2'>
        ${a.finishDateTime?string('yyyy年MM月dd日 HH点mm分')}
      </td>
    </tr>
    <tr>
        <td class='table_action_td1'>报名截止时间：</td>
        <td class='table_action_td2'>
       ${a.attendLimitDateTime?string('yyyy年MM月dd日 HH点mm分')}（当前时间：<span style='color:#F00'>${Util.today()?string('yyyy年MM月dd日 HH点mm分')}</span>）
       <#if isDeadLimit?? >
       <span style='color:red'>该活动的报名时间已经截止。</span>
       </#if>
        </td>
    </tr>
    <tr>
      <td class='table_action_td1'>活动地点：</td><td class='table_action_td2'>${a.place}</td>
    </tr>
    <#if canAttend == '1'>
    <tr style='display:none'>
      <td class='table_action_td1>输入参加人数：</td><td class='table_action_td2'><input name='usernum' value='1' /></td>
    </tr>
    <tr>
      <td class='table_action_td1'>留言：<br/>（管理员可以看到此留言）</td><td class='table_action_td2'><textarea name='userdesc' style='width:100%;font-size:11px;'></textarea></td>
    </tr>    
    <tr>
        <td class='table_action_td2'></td><td class='table_action_td2'>
        <input type='submit' value='参加活动' />
        </td>
    </tr>
    </#if>
    </table>
    </form>
<#else>
没有找到该活动。
</#if>
</div>
</div>

<div style='clear:both;height:10px;'></div>
<#if action_reply_list??>
<div class='dist'>
  <div class='dist_head'>
    <div class='dist_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;本活动的讨论</div>
  </div>
  <div class='dist_content' style='text-align:left;'>
  
<#list action_reply_list as ar >
<#assign u = Util.userById(ar.userId)>
  <div style='border:1px solid #b0bec7;background:#fff;padding:1px;'>
    <table cellpadding="0" cellspacing="0" border='0' class='thread_table'>
    <tr>
      <td class="postauthor" style='padding:2px;'><div class='post_head' style='height:18px'>${u.nickName}</div></td>
      <td>
        <div class='post_head' style='font-weight:normal;height:18px'>
          <div style='float:right'>
          <a href='' onclick='document.getElementById("con_thread${ar.actionReplyId}").style.fontSize="16px";return false;'>大</a> 
          <a href='' onclick='document.getElementById("con_thread${ar.actionReplyId}").style.fontSize="14px";return false;'>中</a> 
          <a href='' onclick='document.getElementById("con_thread${ar.actionReplyId}").style.fontSize="12px";return false;'>小</a>
          </div>
          <div style='float:left'>
          发表于：${ar.createDate}
          </div>
        </div>
      </td>
    </tr>
    <tr valign='top'>
      <td class="postauthor">
        <div class='posticon'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='128' height='128' /></div>
        <div>文章数：${u.articleCount!}</div>
        <div>资源数：${u.resourceCount}</div>
        <div>图片数：${u.photoCount}</div>
        <div>主题数：${u.topicCount}</div>
        <div>评论数：${u.commentCount}</div>
        <div>注册时间：</strong>${u.createDate?string('yyyy-MM-dd HH:mm')}</div>
        <div class='spacer'></div>
        <div style="line-height:200%">
        <a class="linkButton icoBlog" href='${SiteUrl}go.action?loginName=${u.loginName}'>个人主页</a>
        <a class="linkButton icoProfile" href='${SiteUrl}go.action?profile=${u.loginName}'>查看档案</a>
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
        <div style='padding:6px 10px;font-weight:bold;' id='title_thread${ar.actionReplyId}'>${ar.topic?html}</div>
        <div class='thread_content' id='con_thread${ar.actionReplyId}'>${ar.content}</div>        
      </td>
    </tr>
    <tr>
      <td class="postauthor" style='padding:2px;'></td>
      <td style='padding-left:1px;'>
      <div class='thread_foot'>
    <#if loginUser??>
      <a style='display:none' href='#replay' onclick='document.forms["re"].elements["title"].value="[quote]" + document.getElementById("con_thread${ar.actionReplyId}").innerHTML + "[/quote]";'>引用</a> <a href='#replay' onclick='document.forms["re"].elements["title"].value="回复：" + document.getElementById("title_thread${ar.actionReplyId}").innerHTML'>回复</a> 
    </#if>
      <a href='#thread_top'>返回顶部</a> 
      <#if can_manage == '1'>
      <a href='show_preparecourse_action_delete.py?actionId=${action.actionId}&actionReplyId=${ar.actionReplyId}'>删除回复</a>
      </#if>
      </div>
      </td>
    </tr>
    </table>
  </div>
  <#if ar_has_next><div class='spacer'></div></#if>
</#list>

<div class='pager' style='text-align:right;'>
  <#include ('pager.ftl') >
</div>
<div id="shareDiv"></div>
</div>
</div>
<div style='clear:both;height:10px;'></div>
</#if>

<#if can_comment?? && can_comment == '1' >
<div class='dist'>
  <div class='dist_head'>
    <div class='dist_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;参与讨论</div>
  </div>
  <div class='dist_content' style='text-align:left;'>
  
<form method='post' name='re' onsubmit='return checkData(this);'>
<input type='hidden' name='cmd' value='comment' />
<input type='hidden' name='pageSize' value='${pager.pageSize?default(1)}' />
<input type='hidden' name='totalRows' value='${pager.totalRows?default(1)}' />
<table width='100%'>
<tr>
<td>
<a name='replay'></a>
标题：<input name='title' size="80" value='回复：${action.title?html}' /> <font color='red'>*</font> 必须填写讨论标题</td>
</tr>
<tr>
<td>内容：<font color='red'>*</font> 必须填写讨论内容<br />
    <script id="DHtml" name="actionComment" type="text/plain" style="width:880px;height:500px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml');
    </script>         
</td>
</tr>
<tr>
<td><input type='submit' value='发表讨论' /></td>
</tr>
</table>
</form>

<#else>
<div>请无权参加讨论。</div>
</#if>
</div>
</div>

    
    </div>
    <div id='page_footer'></div>
    <script>App.start();</script>
    <div id="subMenuDiv"></div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
    <#-- 原来这里是 include loginui.ftl  -->
    <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
    <script type="text/javascript">
		calendar.set("start-date");
		calendar.set("end-date");
		calendar.set("deadline-date");
	</script>
</body>
</html>