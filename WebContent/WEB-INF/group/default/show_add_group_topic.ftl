<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="expires" content="0" />
<title>${group.groupTitle!?html}</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}/${SiteThemeUrl}groups.css" />
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
<link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
<!-- 布局模板 -->
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_${(page.layoutId!'1')}.css" />
<!-- ToolTip -->
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
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
<script language='javascript'>
var JITAR_ROOT = '${SiteUrl}';
//var USERMGR_ROOT = '{UserMgrClientUrl}'; 此处变量应该可以不用了，不用再改了。     
  <#if loginUser?? >
var visitor = { id: ${loginUser.userId}, name: '${loginUser.loginName!?js_string}', nickName: '${loginUser.nickName!?js_string}', role: '${visitor_role!"guest"}' };
  <#else>
var visitor = { id: null, name: null, nickName: null, role: 'guest' };
  </#if>
var user = { };
var group = {
  id: ${group.groupId}, name: '${group.groupName!?js_string}', title: '${group.groupTitle!?js_string}'
};
var page_ctxt = {
  owner: group,
  isSystemPage: ${page.isSystemPage?string('true', 'false')},
  pages: [{id: ${page.pageId}, title:'${page.title}' }],
  widgets: [
            {id: "placerholder1", page:${page.pageId}, column:1, title:'', module:'placeholder', ico:'', data:{} }
           ]
};

function check() {
  var title = document.topicForm.title.value;
  if (title == null || title == '') {
    alert('请填写主题的标题.');
    return false;
  }
  return true;
}  
  </script>
<script src='${SiteUrl}js/jitar/core.js'></script>
<script src='${SiteUrl}js/jitar/lang.js'></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
</head>
<body>
<#include ('func.ftl') >
<div id = 'progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
<div id='header'>
<div id='blog_name'><span>${group.groupTitle!?html}</span></div>
</div>
<div id='placerholder1' title='发布新主题' style='display:none;padding:1px;'>
<form name='topicForm' action="${SiteUrl}manage/groupBbs.action" method="post" onsubmit='return check()'>
  <input type='hidden' name='cmd' value='save_topic' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <input type='hidden' name='redirect' value='${redirect}' />
  <input type='hidden' name='frompage' value='true' />
  <table class="listTable" cellspacing='1' width="100%">
    <tr>
        <td align="right" width="60"><b>标题：</b></td>
        <td><input type="text" name="title" size="60" value='${topic.title!?html}' /> <font color='red'>*</font> 必须填写标题</td>
    </tr>
    <tr>
        <td valign="top" align="right"><b>内容：</b></td>
        <td>
                        <script id="webEditor" name="content" type="text/plain" style="width:840px;height:400px;">
                        ${topic.content!}
                        </script>                          
                        <script type="text/javascript">
                            var editor = UE.getEditor('webEditor');
                        </script>            
    </td>
    </tr>
    <tr>
        <td align="right"><b>标签：</b></td>
        <td><input type="text" name="tags" size='60' value='${topic.tags!?html}'> (以 ',' 逗号隔开多个标签)</td>
    </tr>
    <tr>
        <td></td>
        <td>
          <input type="submit" class='button' value="  发  表  " />&nbsp;&nbsp;
          <input type='button' class='button' value=" 返  回 " onclick="history.back()" />
        </td>
    </tr>
  </table>
</form>

</div>
<#-- 调用页面指定的布局模式 -->
<#include ('../../layout/layout_' + (page.layoutId!'1') + '.ftl') >
<div id='page_footer'></div>
<script>App.start();</script>
<div id="subMenuDiv"></div>
<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<#-- 原来这里是 include loginui.ftl  -->
<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>
