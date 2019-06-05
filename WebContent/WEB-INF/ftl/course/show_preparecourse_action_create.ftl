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
  
  function selectUser()
  {
    window.open("selectuser.py")
  }
  
  function getUserList()
  {
      
  }
  
function selectUser()
{
  url = '${SiteUrl}selectUser.py?showgroup=1&singleuser=0&inputUser_Id=inviteUserId&inputUserName_Id=inviteUserName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
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
    <div id='placerholder1' title='发起活动' style='display:none;padding:1px;'> 

<form id="fAction" method="post" onsubmit='return checkData(this);'>
    <table border='0' cellpadding='4' cellspacing='1' width='100%' class='table_action' style="table-layout:fixed">
    <tr>
    <td class='table_action_td1' style="width:90px;">活动名称(<span style='color:#F00'>*</span>)：</td><td class='table_action_td2'><input name='actionName' style='width:600px' /></td>
    </tr>
    <tr>
    <td class='table_action_td1'>活动类型(<span style='color:#F00'>*</span>)：</td><td class='table_action_td2'>
	     <input type='radio' name='actionType' value='0' id='actionType0' checked='checked' /><label for='actionType0'>任意参加</label>
	     <input type='radio' name='actionType' value='1' id='actionType1' /><label for='actionType1'>只能组内人员参加</label>
	     <input type='radio' name='actionType' value='2' id='actionType2' /><label for='actionType2'>只能邀请参加</label>
	     <span style='color:#F00'> 【注】组内的含义：个人活动指好友；群组活动指组内成员;备课活动指备课成员。</span>
	  </td>
    </tr>
    <tr>
        <td class='table_action_td1'>活动方式(<span style='color:#F00'>*</span>)：</td><td class='table_action_td2'>
        <input type='radio' name='actionVisibility' value='0' id='actionVisibility0' checked='checked' /><label for='actionVisibility0'>完全公开</label>
        <input type='radio' name='actionVisibility' value='1' id='actionVisibility1' /><label for='actionVisibility1'>保密(只能参与活动的人员看到)</label>
     </td>
    </tr>
    <tr>
      <td class='table_action_td1'>活动描述(<span style='color:#F00'>*</span>)</td><td class='table_action_td2'>

    <script id="DHtml" name="actionDescription" type="text/plain" style="width:820px;height:500px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml');
    </script>  
    	    
      </td>
    </tr>
    <tr>
        <td class='table_action_td1'>活动人数限制：</td><td class='table_action_td2'><input name='actionUserLimit' value='0' /> (0表示不限制)</td>
    </tr>
    <tr>
        <td class='table_action_td1'>活动开始时间：</td>
        <td class='table_action_td2'>
        <select name="actionStartDateTimeY" onchange="showMonth(this,'actionStartDateTimeM','actionStartDateTimeD')">
        <#list 2009.. 2108 as i>
        <option value='${i}' <#if default_year == i>selected</#if>>${i}</option>
        </#list>
        </select>年 
        <select name="actionStartDateTimeM" onchange="showDay(this,'actionStartDateTimeY','actionStartDateTimeD')">
        <#list 1.. 12 as i>
        <option value='${i}' <#if default_month == i>selected</#if>>${i}</option>
        </#list>
        </select>月 
        <select name="actionStartDateTimeD">
        <#list 1.. 31 as i>
        <option value='${i}'  <#if default_day == i>selected</#if>>${i}</option>
        </#list>
        </select>日 
        <select name="actionStartDateTimeH">
        <#list 0.. 23 as i>
        <option value='${i}'>${i}</option>
        </#list>
        </select>点 
        <select name="actionStartDateTimeMM">
        <#list 0.. 59 as i>
        <option value='${i}'>${i}</option>
        </#list>
        </select>分 <span style='color:#F00'>【注】考虑闰年问题，请按年、月、日顺序选择。</span>
    </tr>
    <tr>
        <td class='table_action_td1'>活动结束时间：</td>
        <td class='table_action_td2'>
        <select name="actionFinishDateTimeY" onchange="showMonth(this,'actionFinishDateTimeM','actionFinishDateTimeD')">
        <#list 2009.. 2108 as i>
        <option value='${i}' <#if default_year == i>selected</#if>>${i}</option>
        </#list>
        </select>年
        <select name="actionFinishDateTimeM" onchange="showDay(this,'actionFinishDateTimeY','actionFinishDateTimeD')">
        <#list 1.. 12 as i>
        <option value='${i}' <#if default_month == i>selected</#if>>${i}</option>
        </#list>
        </select>月
        <select name="actionFinishDateTimeD">
        <#list 1.. 31 as i>
        <option value='${i}' <#if default_day == i>selected</#if>>${i}</option>
        </#list>
        </select>日
        <select name="actionFinishDateTimeH">
        <#list 0.. 23 as i>
        <option value='${i}'>${i}</option>
        </#list>
        </select>点
        <select name="actionFinishDateTimeMM">
        <#list 0.. 59 as i>
        <option value='${i}'>${i}</option>
        </#list>
        </select>分 <span style='color:#F00'>【注】考虑闰年问题，请按年、月、日顺序选择。</span>
      </td>
    </tr>
    <tr>
        <td class='table_action_td1'>报名截止时间：</td>
        <td class='table_action_td2'>
        <select name="attendLimitDateTimeY" onchange="showMonth(this,'attendLimitDateTimeM','attendLimitDateTimeD')">
        <#list 2009.. 2108 as i>
        <option value='${i}' <#if default_year == i>selected</#if>>${i}</option>
        </#list>
        </select>年
        <select name="attendLimitDateTimeM" onchange="showDay(this,'attendLimitDateTimeY','attendLimitDateTimeD')">
        <#list 1.. 12 as i>
        <option value='${i}' <#if default_month == i>selected</#if>>${i}</option>
        </#list>
        </select>月
        <select name="attendLimitDateTimeD">
        <#list 1.. 31 as i>
        <option value='${i}' <#if default_day == i>selected</#if>>${i}</option>
        </#list>
        </select>日
        <select name="attendLimitDateTimeH">
        <#list 0.. 23 as i>
        <option value='${i}'>${i}</option>
        </#list>
        </select>点        
        <select name="attendLimitDateTimeMM">
        <#list 0.. 59 as i>
        <option value='${i}'>${i}</option>
        </#list>
        </select>分 <span style='color:#F00'>【注】考虑闰年问题，请按年、月、日顺序选择。</span>
        </td>
    </tr>
    <tr>
      <td class='table_action_td1'>活动地点：</td><td class='table_action_td2'><textarea name='actionPlace' style='width:100%;height:60px;'></textarea></td>
    </tr>
    <tr>
        <td class='table_action_td1'>邀请用户：</td><td class='table_action_td2'>
        <input type='hidden' name='inviteUserId' id='inviteUserId' />
        <textarea id='inviteUserName' style='width:100%;height:100px;border:0px' readonly='readonly'></textarea>
        </td>
    </tr>
    <tr>
        <td class='table_action_td2'></td><td class='table_action_td2'>
        <input type='button' value='邀请用户' onclick='selectUser()' />
        <input type='submit' value='发起活动' />
        </td>
    </tr>
    </table>
    </form>
    
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