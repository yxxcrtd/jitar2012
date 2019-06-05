<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 视频教研</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
  <style type="text/css">
  tr td{background:#fff;height:30px;}
  .txt:hover{outline:solid orange 1px;}
  .div_title { TEXT-ALIGN: center; MARGIN: 10px 0px; FONT-SIZE: 12pt; FONT-WEIGHT: bold }
  .txt { BORDER-BOTTOM: #000000 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 2px; PADDING-LEFT: 2px; PADDING-RIGHT: 2px; BACKGROUND: #fff; COLOR: #000; FONT-SIZE: 12px; BORDER-TOP: 0px; BORDER-RIGHT: 0px; PADDING-TOP: 2px; }
  HR { BORDER-BOTTOM: #000000 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; BACKGROUND: #fff; COLOR: #000; BORDER-TOP: 0px; BORDER-RIGHT: 0px;}
  #v0{margin-left:195px;}
  .alertmsg {font-size:12px;}
  
 </style>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--活动正文 Start-->
<div class="main mt25 clearfix">
<form action='sitevideomeeting.action' method='post' name="theForm">
<#if __referer??>
  <input type='hidden' name='__referer' value='${__referer}' />
</#if>
  <table border="0" cellspacing="1" cellpadding="2" style="background:#B0BEC7;margin:auto;width:800px;">
    <tr>
      <td align="right" width='20%'><strong>视频会议主题：</strong></td>
      <td align="left">
        <input type="text" name="subject" id="subject" size='75' value='${meetings.subject!?html}' class="txt" />
        <span class="alertmsg"><font color='red'>*</font> 必须填写主题.</span>
      </td>
    </tr>
<#assign date1=.now?string("yyyy-MM-dd")>
<#if meetings.startTime??>
    <#assign year= meetings.startTime?string("yyyy")>
    <#assign month= meetings.startTime?string("M")>
    <#assign day= meetings.startTime?string("d")>
    <#assign hour= meetings.startTime?string("h")>
    <#assign minue= meetings.startTime?string("m")>
    <#assign date1=year+'-'+month+'-'+day>
</#if>  
    <tr>
      <td align="right">
          <strong>开始时间：</strong>
        </td>
        <td align="left">
          <div class="activityP">
          
          <input name="meetingStartDateTimeYMD" id="meetingStartDateTimeYMD" class="Wdate" onClick="WdatePicker()" style="width:120px" value="${date1}"/>        
            <select name="meetingStartDateTimeH">
            <#list 0.. 23 as i>
            <option value='${i}' <#if hour??><#if (i?string)==(hour?string)>selected</#if></#if>>${i}</option>
            </#list>
            </select>点 
            <select name="meetingStartDateTimeMM">
            <#list 0.. 59 as i>
            <option value='${i}' <#if minue??><#if (i?string)==(minue?string)>selected</#if></#if>>${i}</option>
            </#list>
            </select>分&nbsp;&nbsp;<span class="alertmsg"><font color='red'>*</font>必须填写开始时间,要晚于当前时间</span>
          </div>
        </td>
    </tr>
<#assign date2=.now?string("yyyy-MM-dd")>
<#if meetings.endTime??>
    <#assign year2 = meetings.endTime?string("yyyy")>
    <#assign month2 = meetings.endTime?string("M")>
    <#assign day2 = meetings.endTime?string("d")>
    <#assign hour2 = meetings.endTime?string("h")>
    <#assign minue2 = meetings.endTime?string("m")>
    <#assign date2 = year2+'-'+month2+'-'+day2>
</#if>     
    <tr>
        <td align="right">
          <strong>结束时间：</strong>
          </td>
        <td align="left">
          <div class="activityP">
          <input name="meetingEndDateTimeYMD" id="meetingEndDateTimeYMD" class="Wdate" onClick="WdatePicker()" style="width:120px"  value="${date2}"/>   
            <select name="meetingEndDateTimeH">
            <#list 0.. 23 as i>
            <option value='${i}' <#if hour2??><#if (i?string)==(hour2?string)>selected</#if></#if>>${i}</option>
            </#list>
            </select>点
            <select name="meetingEndDateTimeMM">
            <#list 0.. 59 as i>
            <option value='${i}' <#if minue2??><#if (i?string)==(minue2?string)>selected</#if></#if>>${i}</option>
            </#list>
            </select>分 &nbsp;&nbsp;<span class="alertmsg"><font color='red'>*</font>必须填写结束时间,要晚于开始时间</span>
          </div>
        </td>
    </tr>
    <tr>
      <td align="right"><strong>提前入会：</strong></td>
      <td align="left">
        <select name="beforehandTime">
            <option value="0" <#if meetings.beforehandTime??><#if meetings.beforehandTime==0>selected</#if></#if>>0分钟</option>
            <option value="5" <#if meetings.beforehandTime??><#if meetings.beforehandTime==5>selected</#if></#if>>5分钟</option>
            <option value="10" <#if meetings.beforehandTime??><#if meetings.beforehandTime==10>selected</#if></#if>>10分钟</option>
            <option value="15" <#if meetings.beforehandTime??><#if meetings.beforehandTime==15>selected</#if></#if>>15分钟</option>
            <option value="20" <#if meetings.beforehandTime??><#if meetings.beforehandTime==20>selected</#if></#if>>20分钟</option>
            <option value="25" <#if meetings.beforehandTime??><#if meetings.beforehandTime==25>selected</#if></#if>>25分钟</option>
            <option value="30" <#if meetings.beforehandTime??><#if meetings.beforehandTime==30>selected</#if></#if>>30分钟</option>
        </select>(<span class="alertmsg">允许提前多少分钟加入会议,服务器需要配置为允许提前加入才有效</span>)
      </td>
    </tr>
    <tr>
      <td align="right"><strong>会议室密码：</strong></td>
      <td align="left">
        <input type="text" name="passwd" value="${meetings.passwd!?html}" class="txt"/> &nbsp;&nbsp;<span class="alertmsg"><font color='red'>*</font>必须填写会议室密码</span>
      </td>
    </tr>    
    <tr>
      <td align="right"><strong>参会人数：</strong></td>
      <td align="left">
        <input type="text" name="attendeeAmount" value="${meetings.attendeeAmount}" class="txt" style="width:40px"/> &nbsp;&nbsp;<span class="alertmsg"><font color='red'>*</font>必须填写参会人数</span>
      </td>
    </tr>       
    <tr>
      <td align="right"><strong>主持人：</strong></td>
      <td align="left">
        <input type="hidden" name="hostId"  id="hostId" value=""/>
        <input type="text" name="hostName" id="hostName" value="${meetings.hostName!?html}" size="40"  readonly='readonly' class="txt"/>
        <a href="javascript:void(0);" class="activityBtn" onclick="selectUser2()">选择主持人</a>&nbsp;&nbsp;<span class="alertmsg"><font color='red'>*</font>必须选择主持人</span>
      </td>
    </tr>
    <tr>
      <td align="right"><strong>会议描述：</strong></td>
      <td align="left">
        <textarea name="agenda" style="width:100%;height:80px">${meetings.agenda!?html}</textarea>
      </td>
    </tr>    
    <tr>
        <td align="right"><strong>参会用户：</strong></td>
        <td align="left">
        <input type='hidden' name='userIds' id='userIds' value="${meetings.userIds!?html}"/>
        <textarea id='inviteUserName' name='userNames' style='width:100%;height:100px;' readonly='readonly'>${meetings.userNames!?html}</textarea>
        <!--<font color='red'>*</font>必须选择参会用户-->
        <br/>
        <a href="javascript:void(0);" class="activityBtn" onclick="selectUser()">选择用户</a>
        <a href="javascript:void(0);" class="activityBtn" onclick="appendUser()">追加用户</a>
        </table>
        </div>
        </td>
    </tr>
  </table>
 <table border="0" cellspacing="0" cellpadding="0" style="margin:auto;width:800px;height:40px;">
     <tr>
     <td style="width:20%>&nbsp;</td>
     <td style="vertical-align:middle;">
        <input type='hidden' name='startTime' value='' />
        <input type='hidden' name='endTime' value='' />
        <input type='hidden' name='cmd' value='save' />
        <input type='hidden' name='Id' value='${meetings.id}' />
         <a href="javascript:void(0);" class="activityBtn" onclick="saveData();">${(meetings.id == 0)?string('增加会议', '修改会议')}</a>
     </td>
     </tr>
 </table>
</form>
<script type="text/javascript">
function saveData(){
    var v=document.getElementById("subject").value
    if(v=="")
    {
        alert("请输入主题");
        return false;
    }
    //组合时间
    if(theForm.meetingStartDateTimeYMD.value=="")
    {
        alert("请输入开始时间");
        return false;
    }
    if(theForm.meetingEndDateTimeYMD.value=="")
    {
        alert("请输入结束时间");
        return false;
    } 
       
      var datePattern = /^(\d{4})-(\d{1,2})-(\d{1,2})$/; 
      
      if (! datePattern.test(theForm.meetingStartDateTimeYMD.value)) { 
        window.alert("请填写正确的开始时间"); 
        return false; 
      }
      if (! datePattern.test(theForm.meetingEndDateTimeYMD.value)) { 
        window.alert("请填写正确的结束时间"); 
        return false; 
      }  
    
    var beginT = theForm.meetingStartDateTimeYMD.value + " " + theForm.meetingStartDateTimeH.value + ":" + theForm.meetingStartDateTimeMM.value + ":00";
    var endT = theForm.meetingEndDateTimeYMD.value + " " + theForm.meetingEndDateTimeH.value + ":" + theForm.meetingEndDateTimeMM.value + ":00";

      var d1 = new Date(beginT.replace(/-/g, "/")); 
      var d2 = new Date(endT.replace(/-/g, "/"));
    
      if (Date.parse(d2) - Date.parse(d1) < 0) { 
        window.alert("开始时间必须早于结束时间"); 
        return false;
      }  
      var today=new Date(); 
      if (today - Date.parse(d1) > 0) { 
        window.alert("开始时间必须晚于当前时间"); 
        return false;
      }  

    theForm.startTime.value = beginT;
    theForm.endTime.value = endT;
    
    if(theForm.userIds.value=="")
    {
        //alert("请输入参会人员");
        //return false;
    }    
    if(theForm.passwd.value=="")
    {
        alert("请输入会议室密码");
        return false;
    }      
     if(theForm.attendeeAmount.value=="" || theForm.attendeeAmount.value=="0")
    {
        alert("请输入参会人数");
        return false;
    }   
     if(theForm.hostId.value=="")
    {
        alert("请选择主持人");
        return false;
    }       
    document.theForm.submit();
    
}
function selectUser()
{
  var url = '${SiteUrl}selectUser.action?showgroup=0&singleuser=0&inputUser_Id=userIds&inputUserName_Id=inviteUserName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}
function appendUser()
{
  var url = '${SiteUrl}selectUser.action?showgroup=0&singleuser=0&inputUser_Id=userIds&inputUserName_Id=inviteUserName&append=1'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}
function selectUser2()
{
  var url = '${SiteUrl}selectUser.action?showgroup=0&singleuser=1&inputUser_Id=hostId&inputUserName_Id=hostName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}
</script>  
  
</div>
<!--活动 End-->
<#include '/WEB-INF/ftl2/footer.ftl'>
</body>
</html>
