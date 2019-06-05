<script type='text/javascript'>
var ContainerObject = {'type':'preparecourse','guid':'${prepareCourse.objectGuid}'};
var JITAR_ROOT = '${SiteUrl}';  <#-- 站点根路径 -->
//var USERMGR_ROOT = '{UserMgrClientUrl}'; 此处变量应该可以不用了，不用再改了。;  <#-- 站点根路径 -->
<#if loginUser?? >
var visitor = { id: ${loginUser.userId}, name: '${loginUser.loginName!?js_string}', nickName: '${loginUser.nickName!?js_string}', role: '${visitor_role!"guest"}' };
<#else>
var visitor = { id: null, name: null, nickName: null, role: 'guest' };
</#if>
var user = { };
var preparecourse = {
id: ${prepareCourse.prepareCourseId}, name: '', title: '${prepareCourse.title!?js_string}'
};
var page_ctxt = {
  owner: preparecourse,
  isSystemPage: ${page.isSystemPage?string('true', 'false')},
  pages: [{id: ${page.pageId}, title:'${page.title?js_string}'}], 
  type:'preparecourse',
  widgets: [
   <#if widget_list?? >
    <#list widget_list as widget>
      {id: '${widget.id}', page:${widget.pageId}, column:${widget.columnIndex}, 
        title:'${widget.title?js_string}', module:'${widget.module}', ico:'', 
        data:{ ${widget.data!} } }<#if widget_has_next>, </#if>
    </#list>
   </#if>
  ]
 };
<#if prepareCourseStageId?? >
var  preparecourse_stage_id = '${prepareCourseStageId}';
<#else>
var  preparecourse_stage_id = '0';
</#if>
</script>
<#-- 自定义界面 -->
<#if (customSkin??) >
<style type='text/css'>
<#if ((customSkin.logo.length() > 0) || (customSkin.logoheight.length() > 0) ) >
#header { 
    <#if (customSkin.logo??) && (customSkin.logo.length() > 0)  >
background:url('${customSkin.logo!}') repeat-x top center;
    </#if>
    <#if (customSkin.logoheight??) && (customSkin.logoheight.length() > 0) >
height:${customSkin.logoheight!}px;
    </#if>
}
</#if>
<#if (customSkin.bgcolor??) && (customSkin.bgcolor.length() > 0) >
html,body{ background:${customSkin.bgcolor!} }
</#if>
<#if (customSkin.titleleft??) && (customSkin.titleleft.length() > 0) >
#blog_name { padding-left:${customSkin.titleleft!}px; }
</#if>
<#if (customSkin.titletop??) && (customSkin.titletop.length() > 0) >
#blog_name { padding-top:${customSkin.titletop!}px; }
</#if>
<#if (customSkin.titledisplay??) && (customSkin.titledisplay.length() > 0) >
#blog_name {display:none;}
</#if>
</style>
</#if>