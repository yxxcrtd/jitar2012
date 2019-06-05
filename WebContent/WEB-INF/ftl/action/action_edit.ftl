<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <#if SiteConfig ??>
  <meta name="keywords" content="${SiteConfig.site.keyword!}" /> 
  </#if>
  <title><#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}action.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script src='${SiteUrl}js/datepicker/calendar.js' type="text/javascript"></script>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />
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
    if(frm.actionName.value=="")
    {
        alert("请输入活动名称");
        return false;
    }
    if(GetMessageLength("DHtml")=='0')
    {
        alert('请输入活动描述');       
        return false;
    }        
    return true;   
}
  
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
  
  function getUserList()
  {
      
  }
  
  function postAction(cmd)
  {
   var f = document.getElementById("fAction")
       if("edit" == cmd){
            if(!checkData(f)){
                return false;
            }
       }
       if("inviteuser" == cmd){
            if(document.getElementById("inviteUserId").value == ""){
                alert("请先邀请用户，再点添加");
                return false;   
            }
       }   
   f.cmd.value = cmd
   f.submit();
  }
  
  function selAll(o)
  {
   var cs = document.getElementsByName("guid")
   for(i = 0;i<cs.length;i++)
   {
    cs[i].checked = o.checked;
   }
  }
  
function selectUser()
{
  url = '${SiteUrl}selectUser.action?showgroup=1&singleuser=0&inputUser_Id=inviteUserId&inputUserName_Id=inviteUserName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}


  </script>
 </head> 
<body>
<#include '/WEB-INF/ftl/site_head.ftl' >
<div style='clear:both;height:10px;'></div>
 <#if edustarAction?? >
 <div class='dist'>
  <div class='dist_head'>
    <div class='dist_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;修改活动</div>
  </div>
  <div class='dist_content' style='text-align:left;'>  

   <form id="fAction" method="post" action="actionEdit.action?actionId=${edustarAction.actionId}">
   <input name='cmd' type='hidden' value='' />
    <table border='0' cellpadding='4' cellspacing='1' width='100%' class='table_action'>
    <tr>
    <td class='table_action_td1'>活动名称 <span style='color:#F00'>*</span>：</td><td class='table_action_td2'><input name='actionName' value='${edustarAction.title!?html}' style='width:600px;' /></td>
    </tr>
    <tr>
    <td class='table_action_td1'>活动类型 <span style='color:#F00'>*</span>：</td>
    <td class='table_action_td2'>
     <input type='radio' name='actionType' value='0' id='actionType0' <#if edustarAction.actionType == 0 >checked='checked' </#if>/><label for='actionType0'>任意参加</label>
     <input type='radio' name='actionType' value='1' id='actionType1' <#if edustarAction.actionType == 1 >checked='checked' </#if>/><label for='actionType1'>只能组内人员参加</label>
     <input type='radio' name='actionType' value='2' id='actionType2' <#if edustarAction.actionType == 2 >checked='checked' </#if>/><label for='actionType2'>只能邀请参加</label>
    
    </td>
    </tr>
    <tr>
      <td class='table_action_td1'>活动方式 <span style='color:#F00'>*</span>：</td>
      <td class='table_action_td2'>
        <input type='radio' name='actionVisibility' value='0' id='actionVisibility0' <#if edustarAction.visibility == 0 >checked='checked' </#if>/><label for='actionVisibility0'>完全公开</label>
        <input type='radio' name='actionVisibility' value='1' id='actionVisibility1' <#if edustarAction.visibility == 1 >checked='checked' </#if>/><label for='actionVisibility1'>保密</label>
     </td>
    </tr>
    <tr>
      <td class='table_action_td1'>活动描述 <span style='color:#F00'>*</span>：</td><td class='table_action_td2'>
            <script id="DHtml" name="actionDescription" type="text/plain" style="width:980px;height:500px;">
            ${edustarAction.description!}
            </script>
            <script type="text/javascript">
                var editor = UE.getEditor('DHtml');
            </script>  
                  
      </td>
    </tr>
    <tr>
     <td class='table_action_td1'>活动人数限制 <span style='color:#F00'>*</span>：</td><td class='table_action_td2'><input name='actionUserLimit' value='${action.userLimit!}' /></td>
    </tr>
    <tr>
     <td class='table_action_td1'>活动报名人数：</td><td class='table_action_td2'>${edustarAction.attendCount!}</td>
    </tr>
    <tr>
        <td class='table_action_td1'>活动开始时间：</td>
        <td class='table_action_td2'>
        <input name="actionStartDateTimeYMD" id="actionStartDateTimeYMD" value="${edustarAction.startDateTime?string("yyyy-MM-dd")}" readonly="readonly" />
             
        <select name="actionStartDateTimeH">
        <#list 0.. 23 as i>
        <#if startDateTimeH == i>
        <#assign sH = " selected='selected'" >
        <#else>
        <#assign sH = "" >
        </#if>
        <option value='${i}'${sH}>${i}</option>
        </#list>
        </select>点 
        <select name="actionStartDateTimeMM">
        <#list 0.. 59 as i>
        <#if startDateTimeMM == i>
        <#assign sMM = " selected='selected'" >
        <#else>
        <#assign sMM = "" >
        </#if>
        <option value='${i}'${sMM}>${i}</option>
        </#list>
        </select>分
    </tr>
    <tr>
        <td class='table_action_td1'>活动结束时间：</td>
        <td class='table_action_td2'>
        <input name="actionFinishDateTimeYMD" id="actionFinishDateTimeYMD" value="${edustarAction.finishDateTime?string("yyyy-MM-dd")}" readonly="readonly" />
       
        <select name="actionFinishDateTimeH">
        <#list 0.. 23 as i>
        <#if finishDateTimeH == i>
        <#assign fH = " selected='selected'" >
        <#else>
        <#assign fH = "" >
        </#if>
        <option value='${i}'${fH}>${i}</option>
        </#list>
        </select>点
        <select name="actionFinishDateTimeMM">
        <#list 0.. 59 as i>
        <#if finishDateTimeMM == i>
        <#assign fMM = " selected='selected'" >
        <#else>
        <#assign fMM = "" >
        </#if>
        <option value='${i}'${fMM}>${i}</option>
        </#list>
        </select>分
      </td>
    </tr>
    <tr>
        <td class='table_action_td1'>报名截止时间：</td>
        <td class='table_action_td2'>
        <input name="attendLimitDateTimeYMD" id="attendLimitDateTimeYMD" value="${edustarAction.attendLimitDateTime?string("yyyy-MM-dd")}" readonly="readonly" />
       
        <select name="attendLimitDateTimeH">
        <#list 0.. 23 as i>
        <#if attendLimitDateTimeH == i>
        <#assign aH = " selected='selected'" >
        <#else>
        <#assign aH = "" >
        </#if>
        <option value='${i}'${aH}>${i}</option>
        </#list>
        </select>点        
        <select name="attendLimitDateTimeMM">
        <#list 0.. 59 as i>
        <#if attendLimitDateTimeMM == i>
        <#assign aMM = " selected='selected'" >
        <#else>
        <#assign aMM = "" >
        </#if>
        <option value='${i}'${aMM}>${i}</option>
        </#list>
        </select>分        
        </td>
    </tr>
    <tr>
      <td class='table_action_td1'>活动地点：</td><td class='table_action_td2'><textarea name='actionPlace' style='width:100%;height:60px;'>${edustarAction.place!?html}</textarea></td>
    </tr>
     <tr>
        <td class='table_action_td2'></td><td class='table_action_td2'>
        <input type='button' value='修改活动' onclick='postAction("edit")' />
        </td>
    </tr>
    <tr>
    <td class='table_action_td1'>本活动参与人员：</td>
    <td class='table_action_td2'>
      <#if action_user_list?? >
        <table border='0' cellpadding='4' cellspacing='1' style='background:#ccc'>
        <tr style='background:#EEE'>
        <th>序号</th>
        <th><input type='checkbox' onclick='selAll(this)' /></th>
        <th>登录名</th>
        <th>真实姓名</th>
        <th>加入时间</th>
        <th>状态</th>
        <th>机构</th>
        <th>用户留言</th>
        </tr>
	    <#list action_user_list as user>
	     <tr style='background:#FFF'>
	     <td>${user_index + 1}</td>
         <td><#if user.userId != edustarAction.createUserId><input type='checkbox' name='guid' value='${user.actionUserId}' /></#if></td>
	     <td><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.loginName}</a></td>
	     <td><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></td>
	     <td>${user.actionUserCreateDate!?string('yyyy-MM-dd HH:mm')}</td>
	     <td>
	     <#if user.actionUserStatus == 0 >
		<span style="color:#f00">未回复</span>
		<#elseif user.actionUserStatus ==1 >
		已参加
		<#elseif user.actionUserStatus ==2 >
		已退出
		<#elseif user.actionUserStatus ==3 >
		已请假
		<#else>
		未定义
		</#if>
	     </td>
	     <td>${user.unitTitle!}</td>
	     <td>${user.actionUserDescription!?html}</td>
	    </tr>
	    </#list>
        </table>
        <input type='button' value='删除活动用户' onclick='postAction("deluser")' />
        <input type='button' value='导出用户报名表' onclick='postAction("printuser")' />      
        （序号只起统计人数的作用）
      </#if>
    </td>
    </tr>
    <tr>
    <td class='table_action_td1'>邀请用户：</td>
    <td class='table_action_td2'>
    <input type='hidden' name='inviteUserId' id='inviteUserId' />
    <textarea id='inviteUserName' style='width:100%;height:100px;border:0px' readonly='readonly'></textarea>
    <br/>
    <input type='button' value='邀请用户' onclick='selectUser()' />
    <input type='button' value='添加用户' onclick='postAction("inviteuser")' />     
    （管理员增加用户暂时不受人数限制）
    </td>
    </tr>   
    </table>
    </form>
 </div> 
 </div>
</#if>
<div style="clear: both;"></div>   
<#include '/WEB-INF/ftl/footer.ftl'>
<script type='text/javascript'>
calendar.set("actionStartDateTimeYMD");
calendar.set("actionFinishDateTimeYMD");
calendar.set("attendLimitDateTimeYMD");
</script>
 </body>
</html>