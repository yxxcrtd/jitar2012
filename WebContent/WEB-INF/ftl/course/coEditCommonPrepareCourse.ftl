<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />  
  <script src='${SiteUrl}js/jitar/core.js'></script>
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
    <script type='text/javascript'>
  //<![CDATA[
  function showHide(divID)
  {
   var d = document.getElementById('div' + divID);
   if(d)
   {
    d.style.display = d.style.display == 'none'?'':'none';
   }
  }
  
  function op(m)
  {
    document.body.setAttribute('onbeforeunload','');
    document.getElementById("ff").optype.value=m;
    document.getElementById("ff").submit();
  }
  
function returnCheck()
{
  return '请不要直接关闭或者刷新浏览器，否则，备课编辑将处于锁定状态，别人将在一段时间内无法进行编辑。\r\n\r\n如果不需要保存编写的内容，请单击“撤销编辑”按钮';
}
//]]>
</script>
</head>
<body onbeforeunload='return returnCheck(event)'>
<#include '/WEB-INF/ftl/site_head.ftl'>
<#if prepareCourse?? >
<div class='course_title'>${prepareCourse.title!} - 共案</div>
<div style='clear:both;height:8px;font-size:0'></div>
<div style='padding:4px 0;color:#f00'>
<span style='float:right'>
<input type='button' value='保存共案' onclick='op(0)' />
<input type='button' value='退出编辑' onclick='op(1)' />
</span>
<span style='float:left'>
请注意：打开此页面将会使本共案处于锁定编辑状态，务必不要直接关闭本页面，如果不保存内容，请单击“退出编辑”按钮。
</span>
</div>
<form method='post' id='ff' action='coEditCommonPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>
<input type='hidden' name='optype' value='1' />

<script id="DHtml" name="commonContent" type="text/plain" style="width:980px;height:800px;">
${prepareCourse.commonContent!}
</script>
<script type="text/javascript">
    var editor = UE.getEditor('DHtml');
</script> 
            
<div style='text-align:right'>
<input type='button' value='保存共案' onclick='op(0)' />
<input type='button' value='退出编辑' onclick='op(1)' />
</div>
</form>
<#else>
 没有加载对象。
</#if>
<br/>
<div class="box">
  <div class="box_head">
    <div class="box_head_right"><span style='cursor:pointer' onclick='showHide(1)'>[显示/隐藏]</span></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;【${prepareCourse.title!}】编辑历史：</div>
  </div>
  <div class="box_content" id='div1'>
    <#if prepareCourseEdit_list?? >
    <table class="pc_table" cellspacing="1">
    <#list prepareCourseEdit_list as e>
    <#assign u = Util.userById(e.editUserId) >
    <#if e.prepareCourseEditId == prepareCourse.prepareCourseEditId>
     <tr class='curbackground'>
    <#else>
     <tr>
    </#if>   
    <td><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></td>
    <td>${e.editDate?string('yyyy-MM-dd HH:mm:ss')}</td>
    <td>
     <a target='_blank' href='showHistoryContent.py?prepareCourseId=${prepareCourse.prepareCourseId}&amp;prepareCourseEditId=${e.prepareCourseEditId}'>查看内容</a>  
    </td>
    </tr>
    </#list>
    </table>
    </#if>
  </div>
</div>

<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
</body>
</html>