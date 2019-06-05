<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>课题公告</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject.js" type="text/javascript"></script>

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
            alert('请输入内容');       
            return false;
        }
        return true;   
    }
  
  
    var JITAR_ROOT = '${SiteUrl}';
    var visitor = { id: null, name: null, nickName: null, role: 'guest' };  
  </script>  
  <script type="text/javascript">
  function isLeap(theYear)
  {
    return (new Date(theYear,1,29).getDate() == 29);
  }
  
  function showMonth(y,m,d)
  {  
    f = document.getElementById("fAction")
    f.elements[m].options.length = 0;  
    for(i = 1; i< 13;i++)
    {
      f.elements[m].options[f.elements[m].options.length] = new Option(i,i)
    } 
     
    f.elements[d].options.length = 0;  
    for(i = 1; i< 32;i++)
    {
      f.elements[d].options[f.elements[d].options.length] = new Option(i,i)
    }     
  }
  
  function showDay(m,y,d)
  {
    monthDays = 0;
    f = document.getElementById("fAction")
    f.elements[d].options.length = 0;
    selectMonth = m.options[m.selectedIndex].value
    if(selectMonth == "1" || selectMonth == "3" || selectMonth == "5" || selectMonth == "7" || selectMonth == "8" ||
     selectMonth == "10" || selectMonth == "12")
    {
        monthDays = 31
    }
    else if( selectMonth == "4" || selectMonth == "6" || selectMonth == "9" || selectMonth == "11")
    {
       monthDays = 30   
    }
    else
    {
       Y = parseInt(f.elements[y].options[f.elements[y].selectedIndex].value,10)
       if(isLeap(Y))
       {
         monthDays = 29        
       }
       else
       {
        monthDays = 28
       }
    }
    for(i = 1; i <= monthDays;i++)
    {
        f.elements[d].options[f.elements[d].options.length] = new Option(i,i)
    }
  }
  
  </script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="secMain mt25 clearfix">
<#if edustar_action ?? >
<#assign a = edustar_action >
<a name='thread_top'></a>
<div class='dist'>
  <div class='dist_head'>
    <div class='dist_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;活动详细信息</div>
  </div>
  <div class='dist_content' style='text-align:left;'>

 <#if !loginUser?? >
 <div>要参与本活动，请先<a href='${SiteUrl}login.jsp'>登录系统</a>。</div>
 </#if>

   <form id="fAction" method="post" action='${SiteUrl}showAction.action?actionId=${a.actionId}'>
    <table border='0' cellpadding='4' cellspacing='1' width='100%' class='table_action'>
    <tr>
    <td class='table_action_td1'>活动名称：</td><td class='table_action_td2'>${a.title?html}</td>
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
        <#elseif a.ownerType == 'subject' >
         只有属于该学科的成员才能参加
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
      <td class='table_action_td1'>留言：<br/>管理员可以看到此留言</td><td class='table_action_td2'><textarea name='userdesc' style='width:100%;font-size:11px;'></textarea></td>
    </tr>    
    <tr>
        <td class='table_action_td2'></td><td class='table_action_td2'>
        <input type='hidden' name="cmd" value='joinAction' />
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
<#if u?? && u != "">
  <div style='border:1px solid #b0bec7;background:#fff;padding:1px;'>
    <table cellpadding="0" cellspacing="0" border='0' class='thread_table'>
    <tr>
      <td class="postauthor" style='padding:2px;'><div class='post_head' style='height:18px'>${u.trueName}</div></td>
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
        <a class="linkButton icoFriend"  href='${SiteUrl}login.jsp?redUrl=${redUrl!?url}'>加为好友</a>
        <a class="linkButton icoMessage" href='${SiteUrl}login.jsp?redUrl=${redUrl!?url}'>发送消息</a>
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
      <a href='${SiteUrl}showAction.action?actionId=${a.actionId}&cmd=deleteReplay&actionReplyId=${ar.actionReplyId}'>删除回复</a>
      </#if>
      </div>
      </td>
    </tr>
    </table>
  </div>
  <#if ar_has_next><div class='spacer'></div></#if>
</#if>
</#list>

<div class='pager' style='text-align:right;'>
  <#include 'pager.ftl' >
</div>
<div id="shareDiv"></div>
</div>
</div>
<div style='clear:both;height:10px;'></div>
</#if>
<#assign caninput = 0>
<#if can_comment?? && can_comment == '1' >
    <#assign caninput = 1>
</#if>

<#if actionUser??>
    <#if actionUser.status == 1>
    <#else>
        <#assign caninput = 0>
    </#if>
</#if>

<#if caninput == 1 >
    <div class='dist'>
      <div class='dist_head'>
        <div class='dist_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;参与讨论</div>
      </div>
      <div class='dist_content' style='text-align:left;'>
      
    <form method='post' action='${SiteUrl}showAction.action?actionId=${a.actionId}' name='re' onsubmit="return checkData(this);">
    <input type='hidden' name='cmd' value='comment' />
    <input type='hidden' name='pageSize' value='${pager.pageSize?default(1)}' />
    <input type='hidden' name='totalRows' value='${pager.totalRows?default(1)}' />
    <table width='100%'>
    <tr>
    <td>
    <a name='replay'></a>
    标题：<input name='title' size="80" value='回复：<#if edustar_action??>${edustar_action.title?html}</#if>' /> <font color='red'>*</font> 必须填写讨论标题</td>
    </tr>
    <tr>
    <td>内容：<font color='red'>*</font> 必须填写讨论内容<br/>
            <script id="DHtml" name="actionComment" type="text/plain" style="width:980px;height:500px;">
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
<div>你目前无权参与讨论。</div>
</#if>
</div>
</div>
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--[if IE 6]>
<script src="${ContextPath}js/new/ie6.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
    DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.secList li,.secVideoPlay');
</script>
<![endif]-->
 </body>
</html>