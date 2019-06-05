<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="expires" content="0" />
        <title>${group.groupTitle!?html}</title>
        <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
        <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
        <!-- 布局模板 -->
        <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_${(page.layoutId!'1')}.css" />
        <!-- ToolTip -->
        <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
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
            <#list widget_list as widget>
              {id: '${widget.id}', page:'${widget.pageId}', column:${widget.columnIndex}, 
                title:'${widget.title?js_string}', module:'${widget.module}', ico:'', 
                data:{ ${widget.data!} } }<#if widget_has_next>, </#if>
            </#list>
          ]
        };
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
    <#-- 调用页面指定的布局模式 -->
    <#include ('../../layout/layout_2.ftl') >
    <div id='placerholder1' title='发起活动' style='display:none'>
    <form method="post">
    <input type='hidden' name='groupId' value='${group.groupId}' />
    <table border='1' width='100%'>
    <tr>
    <td>活动名称：</td><td><input name='actionName' /></td>
    </tr>
    <tr>
    <td>活动类型：</td><td>
                         <input type='radio' name='actionType' value='0' id='actionType0' checked='checked' /><label for='actionType0'>任意参加</label>
                         <input type='radio' name='actionType' value='1' id='actionType1' /><label for='actionType1'>只能组内人员参加</label>
                         <input type='radio' name='actionType' value='2' id='actionType2' /><label for='actionType2'>只能邀请参加</label>
                      </td>
    </tr>
    <tr>
        <td>活动方式：</td><td>
                            <input type='radio' name='actionVisibility' value='0' id='actionVisibility0' checked='checked' /><label for='actionVisibility0'>完全公开</label>
                            <input type='radio' name='actionVisibility' value='1' id='actionVisibility1' /><label for='actionVisibility1'>保密</label>
                         </td>
    </tr>
    <tr>
      <td>活动描述</td><td><textarea bane='actionDescription' style='width:100%;height:60px;'></textarea></td>
    </tr>
    <tr>
        <td>活动人数限制：</td><td><input name='actionUserLimit' /></td>
    </tr>
    <tr>
        <td>活动开始时间：</td><td><input name='actionStartDateTime' /> 时间格式：yyyy-MM-dd HH:mm:ss</td>
    </tr>
    <tr>
        <td>活动结束时间：</td><td><input name='actionFinishDateTime' /> 时间格式：yyyy-MM-dd HH:mm:ss</td>
    </tr>
    <tr>
        <td>报名截止时间：</td><td><input name='actionAttendLimitDateTime' /> 时间格式：yyyy-MM-dd HH:mm:ss</td>
    </tr>
    <tr>
        <td>活动地点：</td><td><textarea name='actionPlace' style='width:100%;height:60px;'></textarea></td>
    </tr>
    <tr>
        <td>活动人数限制：</td><td><input name='actionUserLimit' /></td>
    </tr>
    <tr>
        <td></td><td><input type='submit' value='发起活动' /></td>
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
</body>
</html>
