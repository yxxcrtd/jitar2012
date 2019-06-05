<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 发起活动</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src='${ContextPath}js/datepicker/calendar.js' type="text/javascript"></script>
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
<link rel="stylesheet" type="text/css" href="${ContextPath}js/datepicker/calendar.css" />
<script type="text/javascript">
<!--
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
        if(GetMessageLength("DHtml")==0)
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
  
  function selectUser()
  {
    window.open("selectUser.action")
  }
  
  function getUserList()
  {
      
  }
  
function selectUser()
{
  url = '${ContextPath}selectUser.action?showgroup=1&singleuser=0&inputUser_Id=inviteUserId&inputUserName_Id=inviteUserName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}

$(document).bind('keydown', function (event) {
    var doPrevent = false;
    if (event.keyCode === 8) {
        var d = event.srcElement || event.target;
        if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD' || d.type.toUpperCase() === 'FILE')) 
             || d.tagName.toUpperCase() === 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        }
        else {
            doPrevent = true;
        }
    }

    if (doPrevent) {
        event.preventDefault();
    }
});

//-->
</script>
<style>
input{border:1px solid gray;height:20px}
textarea{border:1px solid gray;}
</style>
</head>
<body>
<#include "/WEB-INF/ftl2/site_head.ftl" />
<!--活动 Start-->
<div class="secMain mt25 clearfix">
<div class="moreList border">
      <h3 class="h3Head textIn"><span class="moreHead">发起活动</span></h3>
        <div class="moreContent">
        
<form id="fAction" method="post" action="createAction.action">
<input type='hidden' name='ownerId' value='${ownerId}' />
<input type='hidden' name='ownerType' value='${ownerType}' />
        
          <table class="moreTable activityTable" cellpadding="0" cellspacing="0">
                <tbody>
                  <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动名称：</strong></div>
                        </td>
                        <td>
                          <div class="activityP"><input name='actionName' style='width:600px' /></div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动类型：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
<input type='radio' name='actionType' value='0' id='actionType0' checked='checked' /><label for='actionType0'>任意参加</label>
<input type='radio' name='actionType' value='1' id='actionType1' /><label for='actionType1'>只能组内人员参加</label>
<input type='radio' name='actionType' value='2' id='actionType2' /><label for='actionType2'>只能邀请参加</label><br/>
<span style='color:#F00'>组内的含义：个人活动指好友；群组活动指组内成员；集备活动指集备成员；学科活动的组指属于该学科的用户（不限学段）。</span>
       
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动方式：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
<input type='radio' name='actionVisibility' value='0' id='actionVisibility0' checked='checked' /><label for='actionVisibility0'>完全公开</label>
<input type='radio' name='actionVisibility' value='1' id='actionVisibility1' /><label for='actionVisibility1'>保密(只能参与活动的人员看到)</label>
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动描述：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
                                <script id="DHtml" name="actionDescription" type="text/plain" style="width:840px;height:500px;">
                                </script>
                                <script type="text/javascript">
                                    var editor = UE.getEditor('DHtml');
                                </script>
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动人数限制：</strong></div>
                        </td>
                        <td>
                          <div class="activityP"><input name='actionUserLimit' value='0' /> (0表示不限制)</div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动开始时间：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
                          <input name="actionStartDateTimeYMD" id="actionStartDateTimeYMD" readonly="readonly" />        
                            <select name="actionStartDateTimeH">
                            <#list 0.. 23 as i>
                            <option value='${i}'>${i}</option>
                            </#list>
                            </select>点 
                            <select name="actionStartDateTimeMM">
                            <#list 0.. 59 as i>
                            <option value='${i}'>${i}</option>
                            </#list>
                            </select>分 
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动结束时间：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
                          <input name="actionFinishDateTimeYMD" id="actionFinishDateTimeYMD" readonly="readonly" />   
                            <select name="actionFinishDateTimeH">
                            <#list 0.. 23 as i>
                            <option value='${i}'>${i}</option>
                            </#list>
                            </select>点
                            <select name="actionFinishDateTimeMM">
                            <#list 0.. 59 as i>
                            <option value='${i}'>${i}</option>
                            </#list>
                            </select>分 
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>报名截止时间：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
<input name="attendLimitDateTimeYMD" id="attendLimitDateTimeYMD" readonly="readonly" />   
        <select name="attendLimitDateTimeH">
        <#list 0.. 23 as i>
        <option value='${i}'>${i}</option>
        </#list>
        </select>点        
        <select name="attendLimitDateTimeMM">
        <#list 0.. 59 as i>
        <option value='${i}'>${i}</option>
        </#list>
        </select>分
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动地点：</strong></div>
                        </td>
                        <td>
                          <div class="activityP"><textarea name='actionPlace' style='width:100%;height:60px;'></textarea></div>
                        </td>
                    </tr>
                   <tr>
        <td class="activityTdBg"><div class="activityP"><strong>邀请用户：</strong></div></td>
        <td> <div class="activityP">
        <input type='hidden' name='inviteUserId' id='inviteUserId' />
        <textarea id='inviteUserName' style='width:100%;height:100px;border:0px' readonly='readonly'></textarea>
        </div>
        </td>
    </tr>
<tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong></strong></div>
                        </td>
                        <td>
                          <div class="activityP">
 <input type='button' value='邀请用户' onclick='selectUser()' />
 <input type='submit' value='发起活动' />
        
</div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
        </form>
    </div>
</div>    
<script type='text/javascript'>
calendar.set("actionStartDateTimeYMD");
calendar.set("actionFinishDateTimeYMD");
calendar.set("attendLimitDateTimeYMD");
</script>
<!--协作组 End-->
<!--公共尾部 Start-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--公共尾部 End-->

<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.coopTag,.comma1,.comma2');
</script>
<![endif]-->
</body>
</html>